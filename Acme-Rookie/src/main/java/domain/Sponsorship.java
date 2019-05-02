
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import forms.SponsorshipForm;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private CreditCard				creditCard;
	private String					bannerURL;
	private String					targetPageURL;
	private Double					flatRateApplied;

	private Provider				provider;
	private Collection<Position>	positions;


	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	@NotBlank
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@URL
	@NotBlank
	public String getTargetPageURL() {
		return this.targetPageURL;
	}

	public void setTargetPageURL(final String targetPageURL) {
		this.targetPageURL = targetPageURL;
	}

	@Min(0)
	@NotNull
	public Double getFlatRateApplied() {
		return this.flatRateApplied;
	}

	public void setFlatRateApplied(final Double flatRateApplied) {
		this.flatRateApplied = flatRateApplied;
	}

	@Valid
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@Valid
	@ManyToMany
	@NotNull
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	public SponsorshipForm castToForm() {
		final SponsorshipForm sponsorshipForm = new SponsorshipForm();

		sponsorshipForm.setId(this.getId());
		sponsorshipForm.setBannerURL(this.getBannerURL());
		sponsorshipForm.setPositions(this.getPositions());
		sponsorshipForm.setTargetPageURL(this.getTargetPageURL());
		sponsorshipForm.setCreditCard(this.getCreditCard());

		return sponsorshipForm;

	}
}
