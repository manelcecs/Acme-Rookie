
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	@Autowired
	AuditService		auditService;

	@Autowired
	PositionService		positionService;

	SimpleDateFormat	format	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	/*
	 * Este test prueba la creación de una nueva auditoría
	 * 
	 * 1 positive,
	 * 1 negative
	 */
	@Test
	public void createAuditDriver() throws ParseException {

		final Object testingData[][] = {
			{
				/*
				 * a) Un auditor puede crear una auditoria
				 * b) Positivo
				 * c) 100%
				 * d)
				 */
				"auditor0", null
			}, {
				/*
				 * a) Un auditor puede crear una auditoria
				 * b) Un actor que no sea un auditor no puede crear una auditoría
				 * c) 100%
				 * d)
				 */
				"company5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAuditTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createAuditTemplate(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			this.auditService.create();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Este test prueba el guardado de una nueva auditoría
	 * 
	 * 1 positive,
	 * 6 negative
	 */
	@Test
	public void saveNewAuditDriver() throws ParseException {

		final Object testingData[][] = {
			{
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) Positivo
				 * c) 100%
				 * d) 9/9
				 */
				"auditor0", true, 7, this.format.parse("2019/01/01 11:00:00"), "Texto valido", "position32", null
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) Los atributos tienen restricciones: La puntuación está dentro de un rango y no puede ser nula, la fecha tiene un formato y no puede ser nula ni futuro, etc.
				 * c) 100%
				 * d) 3/8
				 */
				"auditor0", true, null, null, null, "position32", ConstraintViolationException.class
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) El modo borrador no puede ser nulo
				 * c) 100%
				 * d) 9/9
				 */
				"auditor0", null, 7, this.format.parse("2019/01/01 11:00:00"), "Texto valido", "position32", NullPointerException.class
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) No puede guardarse codigo malicioso HTML en la base de datos
				 * c) 100%
				 * d) 9/9
				 */
				"auditor0", true, 7, this.format.parse("2019/01/01 11:00:00"), "<script>This is not safeHTML!!</script>", "position32", ConstraintViolationException.class
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) La fecha tiene que ser pasado
				 * c) 100%
				 * d) 9/9
				 */
				"auditor0", true, 7, this.format.parse("2100/01/01 11:00:00"), "Texto válido", "position32", ConstraintViolationException.class
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) Una aditoria solo puede ser guardado por un auditor
				 * c) 100%
				 * d) 9/9
				 */
				"company0", true, 7, this.format.parse("2019/01/01 11:00:00"), "Texto válido", "position32", IllegalArgumentException.class
			}, {
				/*
				 * a) Un auditor puede guardar una auditoria
				 * b) La posicion debe ser una que tenaga asignada el auditor logueado
				 * c) 100%
				 * d) 9/9
				 */
				"auditor0", true, 7, this.format.parse("2019/01/01 11:00:00"), "Texto válido", "position33", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveNewAuditTemplate((String) testingData[i][0], (Boolean) testingData[i][1], (Integer) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void saveNewAuditTemplate(final String user, final Boolean draft, final Integer score, final Date moment, final String text, final String position, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Audit audit = new Audit();
			audit.setDraft(draft);
			audit.setScore(score);
			audit.setMoment(moment);
			audit.setText(text);
			audit.setPosition(this.positionService.findOne(this.getEntityId(position)));
			this.auditService.save(audit);
			this.auditService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}
}
