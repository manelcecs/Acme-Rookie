
package forms;

import java.util.Collection;

import domain.CreditCard;
import domain.Position;

public class SponsorshipForm {

	private int						id;
	private CreditCard				creditCard;
	private String					bannerURL;
	private String					targetPageURL;

	private Collection<Position>	positions;


	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	public String getTargetPageURL() {
		return this.targetPageURL;
	}

	public void setTargetPageURL(final String targetPageURL) {
		this.targetPageURL = targetPageURL;
	}

	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}
