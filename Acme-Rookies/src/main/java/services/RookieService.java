
package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.ValidateCreditCard;
import utiles.ValidatorCollection;
import domain.Rookie;
import forms.RookieForm;

@Service
@Transactional
public class RookieService {

	@Autowired
	private UserAccountRepository	accountRepository;

	@Autowired
	private RookieRepository		rookieRepository;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private Validator				validator;


	public Rookie create() throws ParseException {
		final Rookie res = new Rookie();
		res.setSpammer(false);
		res.setBanned(false);
		res.setFinder(this.finderService.generateNewFinder());
		res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());
		return res;
	}
	public Rookie save(final Rookie rookie) {
		Assert.isTrue(rookie != null);

		if (rookie.getId() == 0) {
			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = rookie.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			rookie.setUserAccount(finalAccount);
		} else
			Assert.isTrue(!rookie.getBanned());

		final Rookie res = this.rookieRepository.save(rookie);

		return res;

	}

	public void flush() {
		this.rookieRepository.flush();
	}

	public Rookie findOne(final int rookieId) {
		return this.rookieRepository.findOne(rookieId);
	}

	public Rookie findByPrincipal(final UserAccount principal) {
		return this.rookieRepository.findByPrincipal(principal.getId());
	}

	public Rookie reconstruct(final RookieForm rookieForm, final BindingResult binding) throws ParseException {

		if (!this.validateEmail(rookieForm.getEmail()))
			binding.rejectValue("email", "rookie.edit.email.error");
		if (!rookieForm.getUserAccount().getPassword().equals(rookieForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "rookie.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(rookieForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "rookie.edit.userAccount.username.error");
		if (!rookieForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "rookie.edit.termsAndConditions.error");
		if (rookieForm.getSurnames().isEmpty())
			binding.rejectValue("surnames", "rookie.edit.surnames.error");

		rookieForm.setCreditCard(ValidateCreditCard.checkNumeroAnno(rookieForm.getCreditCard()));
		ValidateCreditCard.checkFecha(rookieForm.getCreditCard(), binding);

		final Rookie result;
		result = this.create();

		final UserAccount account = rookieForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.ROOKIE);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(rookieForm.getAddress());
		result.setEmail(rookieForm.getEmail());
		result.setName(rookieForm.getName());
		result.setVatNumber(rookieForm.getVatNumber());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), rookieForm.getPhoneNumber()));
		result.setPhoto(rookieForm.getPhoto());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(rookieForm.getSurnames()));

		result.setCreditCard(rookieForm.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	//DASHBOARD---------------------------------------------------------

	public Collection<Rookie> getRookiesWithMoreApplications() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.rookieRepository.getRookiesWithMoreApplications();
	}

	public Rookie reconstruct(final Rookie rookie, final BindingResult binding) {

		if (!this.validateEmail(rookie.getEmail()))
			binding.rejectValue("email", "rookie.edit.email.error");
		if (rookie.getSurnames().isEmpty())
			binding.rejectValue("surnames", "rookie.edit.surnames.error");

		rookie.setCreditCard(ValidateCreditCard.checkNumeroAnno(rookie.getCreditCard()));
		ValidateCreditCard.checkFecha(rookie.getCreditCard(), binding);

		final Rookie result;
		result = this.findByPrincipal(LoginService.getPrincipal());
		result.setAddress(rookie.getAddress());
		result.setEmail(rookie.getEmail());
		result.setName(rookie.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), rookie.getPhoneNumber()));
		result.setPhoto(rookie.getPhoto());
		result.setVatNumber(rookie.getVatNumber());
		result.setSurnames(ValidatorCollection.deleteStringsBlanksInCollection(rookie.getSurnames()));

		result.setCreditCard(rookie.getCreditCard());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Boolean validateEmail(final String email) {

		Boolean valid = false;

		final Pattern emailPattern = Pattern.compile("^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,}[>]{1}|[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,})$");

		final Matcher mEmail = emailPattern.matcher(email.toLowerCase());
		if (mEmail.matches())
			valid = true;
		return valid;
	}

	public Collection<Rookie> findAll() {
		return this.rookieRepository.findAll();
	}

}
