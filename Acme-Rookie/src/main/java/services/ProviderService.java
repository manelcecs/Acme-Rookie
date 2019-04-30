
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

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.EmailValidator;
import utiles.ValidateCreditCard;
import utiles.ValidatorCollection;
import domain.Provider;
import forms.ProviderForm;

@Service
@Transactional
public class ProviderService {

	@Autowired
	private UserAccountRepository	accountRepository;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ProviderRepository		providerRepository;


	public Provider create() {
		final Provider res = new Provider();

		res.setSpammer(false);
		res.setBanned(false);
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());

		return res;
	}

	public Provider save(final Provider provider) {
		Assert.isTrue(provider != null);

		if (provider.getId() == 0) {
			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = provider.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			provider.setUserAccount(finalAccount);
		} else
			Assert.isTrue(!provider.getBanned());

		final Provider res = this.providerRepository.save(provider);

		return res;
	}

	public void flush() {
		this.providerRepository.flush();
	}

	public Provider findOne(final int providerId) {
		return this.providerRepository.findOne(providerId);
	}

	public Provider findByPrincipal(final UserAccount principal) {
		return this.providerRepository.findByPrincipal(principal.getId());
	}

	public Collection<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	public Provider reconstruct(final ProviderForm providerForm, final BindingResult binding) {

		if (!EmailValidator.validateEmail(providerForm.getEmail(), Authority.PROVIDER))
			binding.rejectValue("email", "provider.edit.email.error");
		if (!providerForm.getUserAccount().getPassword().equals(providerForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "provider.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(providerForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "provider.edit.userAccount.username.error");
		if (!providerForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "provider.edit.termsAndConditions.error");
		if (providerForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "provider.edit.surnames.error");

		providerForm.setCreditCard(ValidateCreditCard.checkNumeroAnno(providerForm.getCreditCard()));
		ValidateCreditCard.checkFecha(providerForm.getCreditCard(), binding);

		final Provider result;
		result = this.create();

		final UserAccount account = providerForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.PROVIDER);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(providerForm.getAddress());
		result.setEmail(providerForm.getEmail());
		result.setName(providerForm.getName());
		result.setVatNumber(providerForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), providerForm.getPhoneNumber()));
		result.setPhoto(providerForm.getPhoto());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(providerForm.getSurnames()));

		result.setCreditCard(providerForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Provider reconstruct(final Provider provider, final BindingResult binding) {

		if (!EmailValidator.validateEmail(provider.getEmail(), Authority.PROVIDER))
			binding.rejectValue("email", "provider.edit.email.error");
		if (provider.getSurnames().isEmpty())
			binding.rejectValue("surnames", "provider.edit.surnames.error");

		provider.setCreditCard(ValidateCreditCard.checkNumeroAnno(provider.getCreditCard()));
		ValidateCreditCard.checkFecha(provider.getCreditCard(), binding);

		final Provider result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setAddress(provider.getAddress());
		result.setEmail(provider.getEmail());
		result.setName(provider.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), provider.getPhoneNumber()));
		result.setPhoto(provider.getPhoto());
		result.setVatNumber(provider.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(provider.getSurnames()));

		result.setCreditCard(provider.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
}
