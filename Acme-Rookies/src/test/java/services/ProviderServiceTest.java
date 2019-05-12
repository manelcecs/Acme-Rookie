
package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Provider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService	providerService;


	@Override
	@Before
	public void setUp() {

		this.unauthenticate();

	}
	/**
	 * Este test hace referencia al registro de un nuevo Provider.
	 * Corresponde con el requisito funcional 7.1.
	 * Debe presentar datos válidos.
	 * 1 test positivo
	 * 4 test negativos
	 */
	@Test
	public void ProviderRegisterDriver() throws ParseException {
		final Object testingData[][] = {
			{
				/*
				 * usuario no logeado, con datos válidos.
				 * a)7.1
				 * b)Positivo
				 * c)74%
				 * d)2/4
				 */
				null, true, null
			}, {
				/*
				 * usuario logeado como admin
				 * a)7.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"admin", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como Provider
				 * a)7.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"provider0", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como company
				 * a)7.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"company0", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario no logeado, datos invalidos(email y photoURL)
				 * a)7.1
				 * b)Negativo
				 * c)74%
				 * d)2/4
				 */
				null, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ProviderRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void ProviderRegisterTemplate(final String username, final boolean validData, final Class<?> expected) throws ParseException {
		Class<?> caught;

		caught = null;
		Provider admin = this.providerService.create();
		try {
			this.authenticate(username);

			if (!validData) {
				admin.setEmail("");
				admin.setPhoto("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				admin.setEmail("dummyMail" + uniqueId + "@mailto.com");
				admin.setPhoto("https://tiny.url/dummyPhoto");
			}
			admin = ProviderServiceTest.fillData(admin);
			this.providerService.save(admin);
			this.providerService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	@Override
	@After
	public void tearDown() {

		this.unauthenticate();

	}

	private static Provider fillData(final Provider admin) {
		final Provider res = admin;

		res.setAddress("Dirección de prueba");
		res.setBanned(false);

		res.setName("DummyBoss");
		res.setPhoneNumber("+34 555-2342");
		res.setSpammer(false);
		res.setProviderMake("DummyProviderMAKE");

		final String dummySurname = "Dummy Wane Dan";
		final String surnames[] = dummySurname.split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		res.setSurnames(surNames);

		final UserAccount a = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.PROVIDER);
		a.addAuthority(auth);
		a.setUsername("DummyTest" + res.getEmail().hashCode());
		a.setPassword("DummyPass");

		res.setUserAccount(a);

		final CreditCard cc = new CreditCard();
		cc.setCvv(231);
		cc.setExpirationMonth(04);
		cc.setExpirationYear(22);
		cc.setHolder(res.getName());
		cc.setMake("VISA");
		cc.setNumber("4308543581357191");

		res.setCreditCard(cc);

		res.setVatNumber("888848545-R");

		return res;
	}

}
