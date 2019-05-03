
package controllers.provider;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import controllers.AbstractController;
import domain.Provider;
import forms.ProviderForm;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final ProviderForm providerForm = new ProviderForm();

		res = this.createEditModelAndView(providerForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final ProviderForm providerForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Provider providerRect = this.providerService.reconstruct(providerForm, binding);
			this.providerService.save(providerRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(providerForm);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(providerForm, "provider.edit.commit.error");

		}

		return res;

	}

	@RequestMapping(value = "/provider/save", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveAdmin(final Provider provider, final BindingResult binding) {
		ModelAndView res;

		try {
			final Provider providerRect = this.providerService.reconstruct(provider, binding);
			this.providerService.save(providerRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(provider);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(provider);

		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final ProviderForm providerForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("provider/edit");
		result.addObject("providerForm", providerForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Provider provider, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("provider/edit");
		result.addObject("provider", provider);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
