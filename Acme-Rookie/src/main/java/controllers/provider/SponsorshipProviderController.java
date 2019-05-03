
package controllers.provider;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdminConfigService;
import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.AdminConfig;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/provider")
public class SponsorshipProviderController extends AbstractController {

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private AdminConfigService	adminConfigService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSponsorship) {
		final ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);
		Assert.notNull(sponsorship);

		if (sponsorship.getProvider().getUserAccount().equals(LoginService.getPrincipal())) {
			result = new ModelAndView("sponsorship/display");
			result.addObject("sponsorship", sponsorship);
			result.addObject("flatRateAppliedWithVAT", this.sponsorshipService.calculateFlateRateVAT(idSponsorship));
		} else
			result = this.listModelAndView("security.error.accessDenied");

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		result = this.createEditModelAndView(new SponsorshipForm());

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idSponsorship) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);

		try {
			Assert.notNull(sponsorship);
			this.sponsorshipService.delete(sponsorship);
			result = this.listModelAndView();
		} catch (final Exception e) {
			result = this.listModelAndView("sponsorship.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idSponsorship) {
		final ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);
		Assert.notNull(sponsorship);
		final SponsorshipForm sponsorshipForm = sponsorship.castToForm();

		if (sponsorship.getProvider().getUserAccount().equals(LoginService.getPrincipal()))
			result = this.createEditModelAndView(sponsorshipForm);
		else
			result = this.listModelAndView("security.error.accessDenied");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		ModelAndView result;

		//FIXME providers con id 0 null pointer exception aunque falten cosas por enviar 
		try {
			final Sponsorship sponsorshipRect = this.sponsorshipService.reconstruct(sponsorshipForm, binding);
			this.sponsorshipService.save(sponsorshipRect);
			result = this.listModelAndView();
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(sponsorshipForm);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorshipForm, "sponsorship.commit.error");
		}

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String messageCode) {
		final ModelAndView result = new ModelAndView("sponsorship/list");

		final Provider provider = this.providerService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllByProvider(provider.getId());

		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "/sponsorship/provider/list.do");
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm) {
		return this.createEditModelAndView(sponsorshipForm, null);
	}

	protected ModelAndView createEditModelAndView(final SponsorshipForm sponsorshipForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("sponsorship/edit");

		final Collection<Position> posiblePositions = this.positionService.getAllPositionsFiltered();

		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();

		final Double flatRate;
		if (sponsorshipForm.getId() == 0)
			flatRate = adminConfig.getFlatRate();
		else
			flatRate = this.sponsorshipService.findOne(sponsorshipForm.getId()).getFlatRateApplied();

		result.addObject("sponsorshipForm", sponsorshipForm);
		result.addObject("posiblePositions", posiblePositions);
		result.addObject("flatRate", flatRate);
		result.addObject("flatRateWithVAT", this.sponsorshipService.calculateFlateRateVAT(sponsorshipForm.getId()));
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

}
