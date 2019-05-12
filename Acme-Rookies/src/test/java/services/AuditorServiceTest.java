
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Auditor;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AuditorService	auditorService;


	@Test
	public void auditorRegisterDriver() {
		final Object testingData[][] = {
			{
				/*
				 * usuario no logeado
				 * a)11.1.
				 * b)Negativo
				 * c)13%
				 * d)2/4
				 */
				null, true, IllegalArgumentException.class
			//			}, {
			//				/*
			//				 * usuario logeado como administrador, datos invalidos.(email y creditCard)
			//				 * a)11.1
			//				 * b)Negativo
			//				 * c)100%
			//				 * d)2/4
			//				 */
			//				"admin", false, ConstraintViolationException.class
			}, {
				/*
				 * usuario logeado como administratdor
				 * a)11.1
				 * b)Positivo
				 * c)100%
				 * d)2/4
				 */
				"admin", true, null
			}, {
				/*
				 * usuario logeado como rookie
				 * a)11.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"rookie0", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como company
				 * a)11.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"company0", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.auditorRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void auditorRegisterTemplate(final String username, final Boolean validData, final Class<?> expected) {

		Class<?> caught = null;

		this.authenticate(username);

		try {

			Auditor auditor = this.auditorService.create();

			auditor = AuditorServiceTest.fillActor(auditor);

			if (!validData) {
				auditor.setEmail("unvalidEmail");
				auditor.setCreditCard(AuditorServiceTest.unvalidCreditCard(auditor));
			}

			this.auditorService.save(auditor);
			this.auditorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			if (caught.equals(IllegalArgumentException.class))
				this.auditorService.flush();
		}

		this.unauthenticate();
		this.checkExceptions(expected, caught);

	}

	private static Auditor fillActor(final Auditor auditor) {
		final Auditor res = auditor;

		res.setAddress("Dirección de prueba");
		res.setBanned(false);

		res.setName("DummyBoss");
		res.setPhoneNumber("+34 555-2342");
		res.setSpammer(false);
		res.setEmail("dummyEmail@mail.com");

		final String dummySurname = "Dummy Wane Dan";
		final String surnames[] = dummySurname.split(" ");
		final List<String> surNames = new ArrayList<>();
		for (int i = 0; i < surnames.length; i++)
			surNames.add(surnames[i]);
		res.setSurnames(surNames);

		final UserAccount a = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		a.addAuthority(auth);
		a.setUsername("DummyTest" + res.getEmail());
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
	private static CreditCard unvalidCreditCard(final Actor res) {

		final CreditCard cc = new CreditCard();
		cc.setCvv(231);
		cc.setExpirationMonth(04);
		cc.setExpirationYear(01);
		cc.setHolder(res.getName());
		cc.setMake("VISA");
		cc.setNumber("6954239812549809");

		return cc;
	}

}
