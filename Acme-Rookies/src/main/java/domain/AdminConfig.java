
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import forms.AdminConfigForm;

@Entity
@Access(AccessType.PROPERTY)
public class AdminConfig extends DomainEntity {

	private Integer				cacheFinder;
	private Integer				resultsFinder;
	private Collection<String>	spamWords;
	private String				systemName;
	private String				welcomeMessageEN;
	private String				welcomeMessageES;
	private String				countryCode;
	private String				bannerURL;
	private Double				VAT;
	private boolean				nameChanged;
	private Double				flatRate;


	@Range(min = 1, max = 24)
	@NotNull
	public Integer getCacheFinder() {
		return this.cacheFinder;
	}

	public void setCacheFinder(final Integer cacheFinder) {
		this.cacheFinder = cacheFinder;
	}

	@Range(min = 1, max = 100)
	@NotNull
	public Integer getResultsFinder() {
		return this.resultsFinder;
	}

	public void setResultsFinder(final Integer resultsFinder) {
		this.resultsFinder = resultsFinder;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@SafeHtml
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}

	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^(\\+[1-9]|\\+[1-9][0-9]|\\+[1-9][0-9][0-9])$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	//@URL
	@SafeHtml
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@Range(min = 0, max = 100)
	@NotNull
	public Double getVAT() {
		return this.VAT;
	}

	public void setVAT(final Double VAT) {
		this.VAT = VAT;
	}

	public boolean getNameChanged() {
		return this.nameChanged;
	}

	public void setNameChanged(final boolean nameChanged) {
		this.nameChanged = nameChanged;
	}

	@Min(0)
	@NotNull
	public Double getFlatRate() {
		return this.flatRate;
	}

	public void setFlatRate(final Double flatRate) {
		this.flatRate = flatRate;
	}

	public AdminConfigForm castToForm() {
		final AdminConfigForm adminConfigForm = new AdminConfigForm();
		adminConfigForm.setBannerURL(this.getBannerURL());
		adminConfigForm.setCountryCode(this.getCountryCode());
		adminConfigForm.setCacheFinder(this.getCacheFinder());
		adminConfigForm.setResultsFinder(this.getResultsFinder());
		adminConfigForm.setSystemName(this.getSystemName());
		adminConfigForm.setWelcomeMessageEN(this.getWelcomeMessageEN());
		adminConfigForm.setWelcomeMessageES(this.getWelcomeMessageES());
		adminConfigForm.setFlatRate(this.getFlatRate());
		adminConfigForm.setVAT(this.getVAT());

		return adminConfigForm;

	}
}
