
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

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private CreditCard				creditCard;
	private String					bannerURL;
	private String					targetPageURL;
	private Double					flatRateApplied;

	private Provider				provider;
	private Collection<Position>	position;


	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@URL
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@URL
	public String getTargetPageURL() {
		return this.targetPageURL;
	}

	public void setTargetPageURL(final String targetPageURL) {
		this.targetPageURL = targetPageURL;
	}

	@Min(0)
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
	public Collection<Position> getPosition() {
		return this.position;
	}

	public void setPosition(final Collection<Position> position) {
		this.position = position;
	}

}
