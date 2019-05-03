
package services;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import utiles.ValidateCreditCard;
import domain.AdminConfig;
import domain.Position;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private Validator				validator;


	public Sponsorship create() {
		return new Sponsorship();
	}

	public void save(final Sponsorship sponsorship) {
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getProvider().getUserAccount()));
		final Double flateRate = this.adminConfigService.getAdminConfig().getFlatRate();
		if (sponsorship.getId() == 0)
			Assert.isTrue(sponsorship.getFlatRateApplied().equals(flateRate));
		for (final Position position : sponsorship.getPositions())
			Assert.isTrue(!position.getDraft());

		this.sponsorshipRepository.save(sponsorship);
	}
	public Collection<Sponsorship> findAllByProvider(final int idProvider) {
		Assert.isTrue(this.providerService.findOne(idProvider).getUserAccount().equals(LoginService.getPrincipal()));
		return this.sponsorshipRepository.findAllByProvider(idProvider);
	}

	public Sponsorship findOne(final int idSponsorship) {
		final Sponsorship sponsorship = this.sponsorshipRepository.findOne(idSponsorship);
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getProvider().getUserAccount()));
		return sponsorship;
	}

	public Sponsorship reconstruct(final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		Sponsorship result;

		if (sponsorshipForm.getId() == 0) {
			result = this.create();
			result.setProvider(this.providerService.findByPrincipal(LoginService.getPrincipal()));
			result.setFlatRateApplied(this.adminConfigService.getAdminConfig().getFlatRate());
		} else
			result = this.findOne(sponsorshipForm.getId());

		result.setBannerURL(sponsorshipForm.getBannerURL());
		result.setCreditCard(ValidateCreditCard.checkNumeroAnno(sponsorshipForm.getCreditCard()));
		result.setPositions(sponsorshipForm.getPositions());
		result.setTargetPageURL(sponsorshipForm.getTargetPageURL());

		ValidateCreditCard.checkFecha(result.getCreditCard(), binding);

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Sponsorship getRandomOfAPosition(final int idPosition) {
		Sponsorship res = null;
		final List<Sponsorship> sponsorships = this.findAllByPosition(idPosition);

		if (sponsorships.size() != 0) {
			final int index = ThreadLocalRandom.current().nextInt(sponsorships.size());
			res = sponsorships.get(index);
		}

		return res;
	}

	public List<Sponsorship> findAllByPosition(final int idPosition) {
		return this.sponsorshipRepository.findAllByPosition(idPosition);
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getProvider().getUserAccount()));
		this.sponsorshipRepository.delete(sponsorship);
	}

	public Double calculateFlateRateVAT(final int idSponsorship) {
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();

		final Double flatRate;
		if (idSponsorship == 0)
			flatRate = adminConfig.getFlatRate();
		else
			flatRate = this.findOne(idSponsorship).getFlatRateApplied();

		final Double flatRateWithVAT = flatRate + flatRate * (adminConfig.getVAT() / 100);

		return flatRateWithVAT;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}
}
