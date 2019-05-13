
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.EmailValidator;
import utiles.ValidateCreditCard;
import utiles.ValidatorCollection;
import domain.Auditor;
import forms.AuditorForm;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private UserAccountRepository	accountRepository;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private Validator				validator;

	@Autowired
	private AuditorRepository		auditorRepository;


	public Auditor create() {
		final Auditor res = new Auditor();

		res.setSpammer(false);
		res.setBanned(false);
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());

		return res;
	}

	public Auditor save(final Auditor auditor) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(auditor != null);

		if (auditor.getId() == 0) {
			Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.ADMINISTRATOR));
			Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = auditor.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			auditor.setUserAccount(finalAccount);
		} else {
			Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.AUDITOR));
			Assert.isTrue(!auditor.getBanned());
		}

		final Auditor res = this.auditorRepository.save(auditor);

		return res;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

	public Auditor findOne(final int auditorId) {
		return this.auditorRepository.findOne(auditorId);
	}

	public Auditor findByPrincipal(final UserAccount principal) {
		return this.auditorRepository.findByPrincipal(principal.getId());
	}

	public Collection<Auditor> findAll() {
		return this.auditorRepository.findAll();
	}

	public Auditor reconstruct(final AuditorForm auditorForm, final BindingResult binding) {

		if (!EmailValidator.validateEmail(auditorForm.getEmail(), Authority.AUDITOR))
			binding.rejectValue("email", "auditor.edit.email.error");
		if (!auditorForm.getUserAccount().getPassword().equals(auditorForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "auditor.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(auditorForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "auditor.edit.userAccount.username.error");
		if (!auditorForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "auditor.edit.termsAndConditions.error");
		if (auditorForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "auditor.edit.surnames.error");

		auditorForm.setCreditCard(ValidateCreditCard.checkNumeroAnno(auditorForm.getCreditCard()));
		ValidateCreditCard.checkFecha(auditorForm.getCreditCard(), binding);

		final Auditor result;
		result = this.create();

		final UserAccount account = auditorForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.AUDITOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(auditorForm.getAddress());
		result.setEmail(auditorForm.getEmail());
		result.setName(auditorForm.getName());
		result.setVatNumber(auditorForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), auditorForm.getPhoneNumber()));
		result.setPhoto(auditorForm.getPhoto());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(auditorForm.getSurnames()));

		result.setCreditCard(auditorForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {

		if (!EmailValidator.validateEmail(auditor.getEmail(), Authority.AUDITOR))
			binding.rejectValue("email", "auditor.edit.email.error");
		if (auditor.getSurnames().isEmpty())
			binding.rejectValue("surnames", "auditor.edit.surnames.error");

		auditor.setCreditCard(ValidateCreditCard.checkNumeroAnno(auditor.getCreditCard()));
		ValidateCreditCard.checkFecha(auditor.getCreditCard(), binding);

		final Auditor result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setAddress(auditor.getAddress());
		result.setEmail(auditor.getEmail());
		result.setName(auditor.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), auditor.getPhoneNumber()));
		result.setPhoto(auditor.getPhoto());
		result.setVatNumber(auditor.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(auditor.getSurnames()));

		result.setCreditCard(auditor.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
