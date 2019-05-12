
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Position;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	SponsorshipService	sponsorshipService;

	@Autowired
	ProviderService		providerService;

	@Autowired
	PositionService		positionService;


	/*
	 * This test reefer to requeriment 13.1
	 * An actor who is authenticated as a provider must be able to:
	 * Manage his or her sponsorships, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 1 positive
	 * 1 negative
	 */
	@Test
	public void listingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)A provider lists his or her sponsorships
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: -
				 */
				"provider0", "provider0", null
			}, {
				/*
				 * a) One provider lists the sponsorships of another provider
				 * b) Negative
				 * c)Sequence coverage: 50%
				 * d)Data coverage: -
				 */
				"provider0", "provider1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listingTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void listingTemplate(final String user, final String owner, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final int ownerID = super.getEntityId(owner);
			this.sponsorshipService.findAllByProvider(ownerID);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 13.1
	 * An actor who is authenticated as a provider must be able to:
	 * Manage his or her sponsorships, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 1 positive
	 * 1 negative
	 */
	@Test
	public void showingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)A provider display his or her sponsorship
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: -
				 */
				"provider1", super.getEntityId("sponsorship01"), null
			}, {
				/*
				 * a) One provider display the sponsorship of another provider
				 * b) Negative
				 * c)Sequence coverage: 66%
				 * d)Data coverage: -
				 */
				"provider0", super.getEntityId("sponsorship01"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.showingTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void showingTemplate(final String user, final int idSponsorship, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.sponsorshipService.findOne(idSponsorship);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 13.1
	 * An actor who is authenticated as a provider must be able to:
	 * Manage his or her sponsorships, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 2 positive
	 * 14 negative
	 */
	@Test
	public void creatingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)Provider create a valid sponsorship (with lower limit data)
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", null
			}, {
				/*
				 * a)Provider create a valid sponsorship (with upper limit data)
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", null
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with banner not URL)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "Non URL", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with banner blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with target not URL)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "Non URL", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with target not blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with cvv upper limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 1000, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with cvv lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 99, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with expMonth lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 0, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with expMonth upper limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 13, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with expYear lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, -1, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with holder blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with make blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with invalid creditCardNumber)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "make", "1111222233563563334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with a different rate than the current one of the configuration)
				 * b)Negative
				 * c)Sequence coverage: 50%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "make", "1111222233334444", 666.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Provider create a invalid sponsorship (with invalid position)
				 * b)Negative
				 * c)Sequence coverage: 75%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position01", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.creatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Double) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void creatingTemplate(final String user, final String bannerURL, final String targetPageURL, final Integer cvv, final Integer expirationMonth, final Integer expirationYear, final String holder, final String make, final String number,
		final Double flatRateApplied, final String positionName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setProvider(this.providerService.findOne(this.getEntityId(user)));

			sponsorship = this.fillData(sponsorship, bannerURL, targetPageURL, cvv, expirationMonth, expirationYear, holder, make, number, flatRateApplied, positionName);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 13.1
	 * An actor who is authenticated as a provider must be able to:
	 * Manage his or her sponsorships, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 2 positive
	 * 20 negative
	 */
	@Test
	public void updatingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)Provider edit a valid sponsorship (with lower limit data)
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", null
			}, {
				/*
				 * a)Provider edit a valid sponsorship (with upper limit data)
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", null
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with banner not URL)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "Non URL", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with banner blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "", "http://url.com", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with target not URL)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "Non URL", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with target not blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "", 999, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class

			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with cvv upper limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 1000, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with cvv lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 99, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with expMonth lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 0, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with expMonth upper limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 13, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with expYear lower limit data)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, -1, "holder", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with holder blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "", "make", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with make blank)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "", "1111222233334444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with invalid creditCardNumber)
				 * b)Negative
				 * c)Sequence coverage: 100%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "make", "434444", 3000.0, "position20", ConstraintViolationException.class
			}, {
				/*
				 * a)Provider edit a invalid sponsorship (with invalid position)
				 * b)Negative
				 * c)Sequence coverage: 75%
				 * d)Data coverage: 100%
				 */
				"provider1", "http://url.com", "http://url.com", 100, 12, 2019, "holder", "make", "1111222233334444", 3000.0, "position01", IllegalArgumentException.class
			}, {
				/*
				 * a)Provider edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				"provider0", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Admin edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				"admin", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Company edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				"company0", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Rookie edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				"rookie0", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Auditor edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				"auditor0", "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}, {
				/*
				 * a)Anonymous actor edits the sponsorship of another provider
				 * b)Negative
				 * c)Sequence coverage: 12'5%
				 * d)Data coverage: 100%
				 */
				null, "http://url.com", "http://url.com", 100, 1, 2019, "holder", "make", "1111222233334444", 3000.0, "position20", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.updatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Double) testingData[i][9], (String) testingData[i][10], (Class<?>) testingData[i][11]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void updatingTemplate(final String user, final String bannerURL, final String targetPageURL, final Integer cvv, final Integer expirationMonth, final Integer expirationYear, final String holder, final String make, final String number,
		final Double flatRateApplied, final String positionName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId("sponsorship01"));

			sponsorship = this.fillData(sponsorship, bannerURL, targetPageURL, cvv, expirationMonth, expirationYear, holder, make, number, flatRateApplied, positionName);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 13.1
	 * An actor who is authenticated as a provider must be able to:
	 * Manage his or her sponsorships, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 1 positive
	 * 1 negative
	 */
	@Test
	public void deletingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)A provider delete his or her sponsorship
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: -
				 */
				"provider1", "sponsorship01", null
			}, {
				/*
				 * a) One provider delete the sponsorship of another provider
				 * b) Negative
				 * c)Sequence coverage: 50%
				 * d)Data coverage: -
				 */
				"provider1", "sponsorship02", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletingTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void deletingTemplate(final String user, final String idSponsorship, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(idSponsorship));

			this.sponsorshipService.delete(sponsorship);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	private Sponsorship fillData(final Sponsorship sponsorship, final String bannerURL, final String targetPageURL, final Integer cvv, final Integer expirationMonth, final Integer expirationYear, final String holder, final String make,
		final String number, final Double flatRateApplied, final String positionName) {
		sponsorship.setBannerURL(bannerURL);
		sponsorship.setTargetPageURL(targetPageURL);
		final CreditCard creditCard = new CreditCard();
		creditCard.setCvv(cvv);
		creditCard.setExpirationMonth(expirationMonth);
		creditCard.setExpirationYear(expirationYear);
		creditCard.setHolder(holder);
		creditCard.setMake(make);
		creditCard.setNumber(number);
		sponsorship.setCreditCard(creditCard);
		sponsorship.setFlatRateApplied(flatRateApplied);
		final Collection<Position> positions = new ArrayList<>();
		final Position position = this.positionService.findOne(this.getEntityId(positionName));
		positions.add(position);
		sponsorship.setPositions(positions);
		return sponsorship;
	}
}
