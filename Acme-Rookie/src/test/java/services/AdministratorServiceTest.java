
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.AdminConfig;
import domain.Administrator;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private CompanyService			companyService;


	/**
	 * Este test hace referencia al registro de un nuevo administrador.
	 * Corresponde con el requisito funcional 11.1.
	 * Además de datos válidos, es requisito indispensable que para registrar
	 * un administrador nuevo, lo haga un adminsitrador ya existente.
	 * 1 test positivo
	 * 4 test negativos
	 */
	@Test
	public void AdminRegisterDriver() {
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
				"rookie", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como company
				 * a)11.1
				 * b)Negativo
				 * c)39%
				 * d)2/4
				 */
				"company", true, IllegalArgumentException.class
			}, {
				/*
				 * usuario logeado como administrador, datos invalidos.(email y photoURL)
				 * a)11.1
				 * b)Negativo
				 * c)100%
				 * d)2/4
				 */
				"admin", false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AdminRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void AdminRegisterTemplate(final String username, final boolean validData, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		Administrator admin = this.administratorService.create();
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
				admin.setEmail("<admin" + uniqueId + "@>");
				admin.setPhoto("https://tiny.url/dummyPhoto");
			}
			admin = AdministratorServiceTest.fillData(admin);
			this.administratorService.save(admin);
			this.administratorService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private static Administrator fillData(final Administrator admin) {
		final Administrator res = admin;

		res.setAddress("Dirección de prueba");
		res.setBanned(false);

		res.setName("DummyBoss");
		res.setPhoneNumber("+34 555-2342");
		res.setSpammer(false);

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

	/**
	 * #1 Test for Case use: Administrator can banned/unbanned an spammers from the system
	 * 2 positive
	 * 7 negative
	 */
	@Test
	public void bannedDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a)#1 | admin ban/unban a rookie spammer
				 * b) Positive
				 * c) Sequence coverage: 37/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "rookie0", true, null
			}, {
				/**
				 * a)#1 | admin ban/unban a company spammer
				 * b) Positive
				 * c) Sequence coverage: 37/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "company0", true, null
			}, {
				/**
				 * a)#1 | admin ban/unban a no rookie spammer
				 * b) Positive
				 * c) Sequence coverage: 4/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "rookie0", false, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | admin ban/unban a no company spammer
				 * b) Negative
				 * c) Sequence coverage: 4/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "company0", false, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | admin spammer ban/unban himself
				 * b) Negative
				 * c) Sequence coverage: 3/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "admin", true, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | admin no spammer ban/unban himself
				 * b) Negative
				 * c) Sequence coverage: 3/37
				 * d) Data coverage: -
				 * 
				 */
				"admin", "admin", false, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | rookie try ban/unban
				 * b) Negative
				 * c) Sequence coverage: 2/37
				 * d) Data coverage: -
				 * 
				 */
				"rookie0", "admin", true, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | company try ban/unban
				 * b) Negative
				 * c) Sequence coverage: 2/37
				 * d) Data coverage: -
				 * 
				 */
				"company0", "admin", true, IllegalArgumentException.class
			}, {
				/**
				 * a)#1 | unlogged try ban/unban
				 * b) Negative
				 * c) Sequence coverage: 2/37
				 * d) Data coverage: -
				 * 
				 */
				null, "admin", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.bannedTemplate((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void bannedTemplate(final String user, final String actorBanned, final Boolean spammer, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);

			final int idActor = this.getEntityId(actorBanned);
			final Actor actor = this.actorService.getActor(idActor);

			actor.setSpammer(spammer);

			this.actorService.ban(actor);

			this.actorService.flush();

			Assert.isTrue(actor.getBanned());

			this.actorService.unban(actor);

			this.actorService.flush();

			Assert.isTrue(!actor.getBanned());

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 4.1
	 * An actor who is authenticated as an administrator must be able to:
	 * Run a procedure to notify the existing users of the rebranding. The system must
	 * guarantee that the process is run only once
	 * 1 positive
	 * 6 negative
	 */
	@Test
	public void notifyRebrandingDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)A admin notify rebranding
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: -
				 */
				"admin", true, null
			}, {
				/*
				 * a)A admin notify rebranding (but no exists rebranding)
				 * b)Negative
				 * c)Sequence coverage: 12%
				 * d)Data coverage: -
				 */
				"admin", false, IllegalArgumentException.class
			}, {
				/*
				 * a)A rookie notify rebranding
				 * b)Negative
				 * c)Sequence coverage: 4%
				 * d)Data coverage: -
				 */
				"rookie0", true, IllegalArgumentException.class
			}, {
				/*
				 * a)A company notify rebranding
				 * b)Negative
				 * c)Sequence coverage: 4%
				 * d)Data coverage: -
				 */
				"company0", true, IllegalArgumentException.class
			}, {
				/*
				 * a)A auditor notify rebranding
				 * b)Negative
				 * c)Sequence coverage: 4%
				 * d)Data coverage: -
				 */
				"auditor0", true, IllegalArgumentException.class
			}, {
				/*
				 * a)A provider notify rebranding
				 * b)Negative
				 * c)Sequence coverage: 4%
				 * d)Data coverage: -
				 */
				"provider0", true, IllegalArgumentException.class
			}, {
				/*
				 * a)A anonymous actor notify rebranding
				 * b)Negative
				 * c)Sequence coverage: 4%
				 * d)Data coverage: -
				 */
				null, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.notifyRebrandingTemplate((String) testingData[i][0], (Boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void notifyRebrandingTemplate(final String user, final Boolean nameChanged, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);

			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
			adminConfig.setNameChanged(nameChanged);
			this.adminConfigService.save(adminConfig);
			this.adminConfigService.flush();

			this.messageService.notificationRebranding();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * This test reefer to requeriment 4.3
	 * Launch a process to compute an audit score for every company.
	 * The audit score is computed as the average of the audit scores
	 * that the positions offered by a company has got, but normalised
	 * to range 0.00 up to +1.00 using a linear homothetic transformation.
	 * Note that the audit score of a company that hasn't got any audits is not 0.00, but nil.
	 * 1 positive
	 * 6 negative
	 */
	@Test
	public void computeScoreDriver() {
		final Object testingData[][] = {
			{
				/*
				 * a)A admin compute score
				 * b)Positive
				 * c)Sequence coverage: 100%
				 * d)Data coverage: -
				 */
				"admin", null
			}, {
				/*
				 * a)A rookie compute score
				 * b)Negative
				 * c)Sequence coverage: 8'3%
				 * d)Data coverage: -
				 */
				"rookie0", IllegalArgumentException.class
			}, {
				/*
				 * a)A company actor compute score
				 * b)Negative
				 * c)Sequence coverage: 8'3%
				 * d)Data coverage: -
				 */
				"company0", IllegalArgumentException.class
			}, {
				/*
				 * a)A provider actor compute score
				 * b)Negative
				 * c)Sequence coverage: 8'3%
				 * d)Data coverage: -
				 */
				"provider0", IllegalArgumentException.class
			}, {
				/*
				 * a)A auditor actor compute score
				 * b)Negative
				 * c)Sequence coverage: 8'3%
				 * d)Data coverage: -
				 */
				"auditor0", IllegalArgumentException.class
			}, {
				/*
				 * a)A anonymous actor compute score
				 * b)Negative
				 * c)Sequence coverage: 8'3%
				 * d)Data coverage: -
				 */
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.computeScoreTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void computeScoreTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(user);

			this.companyService.computeScore();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
