
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.AdminConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminConfigServiceTest extends AbstractTest {

	@Autowired
	private AdminConfigService	adminConfigService;


	/**
	 * #1 Test for Case use: Administrator can edit the system config
	 * 1 positive
	 * 14 negative
	 */
	@Test
	public void editAdminConfigDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a)#1 | admin edit with legacy data
				 * b) Positive
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 100.0, 0.0, null
			}, {
				/**
				 * a)#1 | company edit with legacy data
				 * b) Negative
				 * c) Sequence coverage: 75%
				 * d) Data coverage: 100%
				 * 
				 */
				"company1", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | Rookie edit with legacy data
				 * b) Negative
				 * c) Sequence coverage: 75%
				 * d) Data coverage: 100%
				 * 
				 */
				"rookie1", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | admin edit with unvalid url
				 * b) Negative
				 * c) Sequence coverage: 75%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "es no es una url", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with unvalid cache time finder
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", -1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with unvalid cache time finder
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 25, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with unlegal finder number results
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 0, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with unlegal finder number results
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 101, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with unlegal country code
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+1000", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with blank system name
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "", "Hi, you are welcome", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with blank welcome message spanish
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "", "Hola, bienvenido", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with blank welcome message english
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "", 21.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with invalid VAT (lower limit data)
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", -0.1, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with invalid VAT (upper limit data)
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 101.0, 3000.0, ConstraintViolationException.class
			}, {
				/**
				 * a)#1 | admin edit with invalid Fare
				 * b) Negative
				 * c) Sequence coverage: 100%
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "http://url.com", 1, "+34", 10, "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", 100.0, -0.1, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editAdminConfigTemplate((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Double) testingData[i][8], (Double) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	protected void editAdminConfigTemplate(final String beanName, final String bannerURL, final Integer cacheFinder, final String countryCode, final Integer resultsFinder, final String systemName, final String welcomeMessageEN,
		final String welcomeMessageES, final Double VAT, final Double flatRate, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
			adminConfig.setBannerURL(bannerURL);
			adminConfig.setCacheFinder(cacheFinder);
			adminConfig.setCountryCode(countryCode);
			adminConfig.setResultsFinder(resultsFinder);
			adminConfig.setSystemName(systemName);
			adminConfig.setWelcomeMessageEN(welcomeMessageEN);
			adminConfig.setWelcomeMessageES(welcomeMessageES);
			adminConfig.setVAT(VAT);
			adminConfig.setFlatRate(flatRate);
			this.adminConfigService.save(adminConfig);
			this.adminConfigService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * #2 Test for Case use: Administrator can manage the list of spam words
	 * 2 positive
	 * 7 negative
	 */
	@Test
	public void editSpamWordTemplate() {
		final Object testingData[][] = {
			{
				/**
				 * a)#2 | admin add spamword
				 * b) Positive
				 * c) Sequence coverage: 8/8
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "nueva palabra", "add", null
			}, {
				/**
				 * a)#2 | admin delete a spam word
				 * b) Positive
				 * c) Sequence coverage: 15/15
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "sex", "delete", null
			}, {
				/**
				 * a)#2 | admin delete nonexistent spam word
				 * b) Negative
				 * c) Sequence coverage: 4/15
				 * d) Data coverage: 100%
				 * 
				 */
				"admin", "dvdsdvsdv", "delete", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | rookie delete spam word
				 * b) Negative
				 * c) Sequence coverage: 1/15
				 * d) Data coverage: 100%
				 * 
				 */
				"rookie0", "sex", "delete", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | rookie add spam word
				 * b) Negative
				 * c) Sequence coverage: 6/8
				 * d) Data coverage: 100%
				 * 
				 */
				"rookie0", "nueva palabra", "add", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | company delete spam word
				 * b) Negative
				 * c) Sequence coverage: 1/15
				 * d) Data coverage: 100%
				 * 
				 */
				"company0", "sex", "delete", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | company add spam word
				 * b) Negative
				 * c) Sequence coverage: 6/8
				 * d) Data coverage: 100%
				 * 
				 */
				"company0", "palabra", "add", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | unlogged add spam word
				 * b) Negative
				 * c) Sequence coverage: 1/15
				 * d) Data coverage: 100%
				 * 
				 */
				null, "sex", "delete", IllegalArgumentException.class
			}, {
				/**
				 * a)#2 | unlogged add spam word
				 * b) Negative
				 * c) Sequence coverage: 6/8
				 * d) Data coverage: 100%
				 * 
				 */
				null, "palabra", "add", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editSpamWordTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void editSpamWordTemplate(final String user, final String spamWord, final String action, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);
			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();

			final Collection<String> spamWords = adminConfig.getSpamWords();

			if (action == "add") {
				spamWords.add(spamWord);
				adminConfig.setSpamWords(spamWords);
				this.adminConfigService.save(adminConfig);
			} else if (action == "delete")
				this.adminConfigService.deleteSpamWord(spamWord);

			this.adminConfigService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
