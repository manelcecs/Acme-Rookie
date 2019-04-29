
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Company;
import domain.Position;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private PositionService			positionService;
	@Autowired
	private CompanyService			companyService;
	@Autowired
	private RookieService			rookieService;


	@Override
	@Before
	public void setUp() {
		this.unauthenticate();
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to positions for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardPositionDriver() {

		final Collection<Company> companies = new ArrayList<Company>();
		companies.add(this.companyService.findOne(this.getEntityId("company0")));
		companies.add(this.companyService.findOne(this.getEntityId("company1")));
		companies.add(this.companyService.findOne(this.getEntityId("company2")));
		companies.add(this.companyService.findOne(this.getEntityId("company3")));
		companies.add(this.companyService.findOne(this.getEntityId("company4")));
		companies.add(this.companyService.findOne(this.getEntityId("company5")));

		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 1.8571, 2, 1, 0.3499, companies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 1.8571, 2, 1, 0.3499, companies, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardPositionTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Company>) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void dashboardPositionTemplate(final String beanName, final Double avgOfPositionsPerCompanyExpected, final Integer maximumOfPositionsPerCompanyExpected, final Integer minimumOfPositionsPerCompanyTestExpected,
		final Double sDOfPositionsPerCompanyExpected, final Collection<Company> companiesWithMoreOffersOfPositionsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfPositionsPerCompany = this.administratorService.getAvgOfPositionsPerCompany();
			final Integer maximumOfPositionsPerCompany = this.administratorService.getMaximumOfPositionsPerCompany();
			final Integer minimumOfPositionsPerCompany = this.administratorService.getMinimumOfPositionsPerCompany();
			final Double sDOfPositionsPerCompany = this.administratorService.getSDOfPositionsPerCompany();
			final Collection<Company> companiesWithMoreOffersOfPositions = this.companyService.getCompaniesWithMoreOffersOfPositions();

			Assert.isTrue(companiesWithMoreOffersOfPositionsExpected.containsAll(companiesWithMoreOffersOfPositions) && companiesWithMoreOffersOfPositionsExpected.size() == companiesWithMoreOffersOfPositions.size());
			Assert.isTrue(avgOfPositionsPerCompany.equals(avgOfPositionsPerCompanyExpected) && maximumOfPositionsPerCompany.equals(maximumOfPositionsPerCompanyExpected) && minimumOfPositionsPerCompany.equals(minimumOfPositionsPerCompanyTestExpected)
				&& sDOfPositionsPerCompany.equals(sDOfPositionsPerCompanyExpected));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to applications for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardApplicationDriver() {

		final Collection<Rookie> rookies = new ArrayList<Rookie>();
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie0")));
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie1")));
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie2")));
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie3")));
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie4")));
		rookies.add(this.rookieService.findOne(this.getEntityId("rookie5")));
		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, rookies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, rookies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.8571, 1, 0, 0.3499, rookies, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, rookies, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardApplicationTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Rookie>) testingData[i][5],
				(Class<?>) testingData[i][6]);
	}
	protected void dashboardApplicationTemplate(final String beanName, final Double avgOfApplicationsPerRookieExpected, final Integer maximumOfApplicationsPerRookieExpected, final Integer minimumOfApplicationsPerRookieExpected,
		final Double sDOfApplicationsPerRookieExpected, final Collection<Rookie> rookiesWithMoreApplicationsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Double avgOfApplicationsPerRookie = this.administratorService.getAvgOfApplicationsPerRookie();
			final Integer maximumOfApplicationsPerRookie = this.administratorService.getMaximumOfApplicationsPerRookie();
			final Integer minimumOfApplicationsPerRookie = this.administratorService.getMinimumOfApplicationsPerRookie();
			final Double sDOfApplicationsPerRookie = this.administratorService.getSDOfApplicationsPerRookie();
			final Collection<Rookie> rookiesWithMoreApplications = this.rookieService.getRookiesWithMoreApplications();

			Assert.isTrue(rookiesWithMoreApplicationsExpected.containsAll(rookiesWithMoreApplications) && rookiesWithMoreApplicationsExpected.size() == rookiesWithMoreApplications.size());
			Assert.isTrue(avgOfApplicationsPerRookie.equals(avgOfApplicationsPerRookieExpected) && maximumOfApplicationsPerRookie.equals(maximumOfApplicationsPerRookieExpected)
				&& minimumOfApplicationsPerRookie.equals(minimumOfApplicationsPerRookieExpected) && sDOfApplicationsPerRookie.equals(sDOfApplicationsPerRookieExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 11.2
	 * here we're going to test the dashboard metrics related to applications for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardSalaryDriver() {

		final Collection<Position> bestPositions = new ArrayList<Position>();
		bestPositions.add(this.positionService.findOne(this.getEntityId("position20")));
		bestPositions.add(this.positionService.findOne(this.getEntityId("position22")));
		bestPositions.add(this.positionService.findOne(this.getEntityId("position23")));
		bestPositions.add(this.positionService.findOne(this.getEntityId("position24")));
		bestPositions.add(this.positionService.findOne(this.getEntityId("position25")));
		bestPositions.add(this.positionService.findOne(this.getEntityId("position26")));

		final Collection<Position> worstPositions = new ArrayList<Position>();
		worstPositions.add(this.positionService.findOne(this.getEntityId("position10")));
		worstPositions.add(this.positionService.findOne(this.getEntityId("position27")));
		worstPositions.add(this.positionService.findOne(this.getEntityId("position28")));
		worstPositions.add(this.positionService.findOne(this.getEntityId("position29")));
		worstPositions.add(this.positionService.findOne(this.getEntityId("position30")));
		worstPositions.add(this.positionService.findOne(this.getEntityId("position31")));

		final Object testingData[][] = {
			{
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardSalaryTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Position>) testingData[i][5],
				(Collection<Position>) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void dashboardSalaryTemplate(final String beanName, final Double avgOfSalariesOfferedExpected, final Integer maximumOfSalariesOfferedExpected, final Integer minimumOfSalariesOfferedExpected, final Double sDOfSalariesOfferedExpected,
		final Collection<Position> positionsWithTheBestSalaryExpected, final Collection<Position> positionsWithTheWorstSalaryExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfSalariesOffered = this.administratorService.getAvgOfSalariesOffered();
			final Integer maximumOfSalariesOffered = this.administratorService.getMaximumOfSalariesOffered();
			final Integer minimumOfSalariesOffered = this.administratorService.getMinimumOfSalariesOffered();
			final Double sDOfSalariesOffered = this.administratorService.getSDOfSalariesOffered();

			final Collection<Position> positionsWithTheBestSalary = this.positionService.getPositionsWithTheBestSalary();
			final Collection<Position> positionsWithTheWorstSalary = this.positionService.getPositionsWithTheWorstSalary();

			Assert.isTrue(positionsWithTheWorstSalary.containsAll(positionsWithTheWorstSalaryExpected) && positionsWithTheWorstSalaryExpected.size() == positionsWithTheWorstSalary.size());
			Assert.isTrue(positionsWithTheBestSalary.containsAll(positionsWithTheBestSalaryExpected) && positionsWithTheBestSalaryExpected.size() == positionsWithTheBestSalary.size());
			Assert.isTrue(avgOfSalariesOffered.equals(avgOfSalariesOfferedExpected) && maximumOfSalariesOffered.equals(maximumOfSalariesOfferedExpected) && minimumOfSalariesOffered.equals(minimumOfSalariesOfferedExpected)
				&& sDOfSalariesOffered.equals(sDOfSalariesOfferedExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 18.1
	 * here we're going to test the dashboard metrics related to curricula for the administrator
	 * One positive
	 * Three negatives
	 */
	@Test
	public void dashboardCurriculaDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.8571, 1, 0, 0.3499, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.8571, 1, 0, 0.3499, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardCurriculaTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardCurriculaTemplate(final String beanName, final Double avgOfCurriculaPerRookieExpected, final Integer maximumOfCurriculaPerRookieExpected, final Integer minimumOfCurriculaPerRookieExpected,
		final Double sDOfCurriculaPerRookieExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Double avgOfCurriculaPerRookie = this.administratorService.getAvgOfCurriculaPerRookie();
			final Integer maximumOfCurriculaPerRookie = this.administratorService.getMaximumOfCurriculaPerRookie();
			final Integer minimumOfCurriculaPerRookie = this.administratorService.getMinimumOfCurriculaPerRookie();
			final Double sDOfCurriculaPerRookie = this.administratorService.getSDOfCurriculaPerRookie();

			Assert.isTrue(avgOfCurriculaPerRookie.equals(avgOfCurriculaPerRookieExpected) && maximumOfCurriculaPerRookie.equals(maximumOfCurriculaPerRookieExpected) && minimumOfCurriculaPerRookie.equals(minimumOfCurriculaPerRookieExpected)
				&& sDOfCurriculaPerRookie.equals(sDOfCurriculaPerRookieExpected));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 18.1
	 * here we're going to test the dashboard metrics related to finders for the administrator
	 * One positive
	 * Three negatives
	 */
	@Test
	public void dashboardFinderDriver() {
		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 0.0, 0, 0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 0.0, 0, 0, 0.0, 0.0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardFinderTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void dashboardFinderTemplate(final String beanName, final Double avgOfResultsInFindersExpected, final Integer maximumOfResultsInFindersExpected, final Integer minimumOfResultsInFindersExpected, final Double sDOfResultsInFindersExpected,
		final Double ratioOfEmptyVsNotEmptyFindersExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfResultsInFinders = this.administratorService.getAvgOfResultsInFinders();
			final Integer maximumOfResultsInFinders = this.administratorService.getMaximumOfResultsInFinders();
			final Integer minimumOfResultsInFinders = this.administratorService.getMinimumOfResultsInFinders();
			final Double sDOfResultsInFinders = this.administratorService.getSDOfResultsInFinders();
			Double ratioOfEmptyVsNotEmptyFinders = this.administratorService.getRatioOfEmptyVsNotEmptyFinders();
			if (ratioOfEmptyVsNotEmptyFinders == null)
				ratioOfEmptyVsNotEmptyFinders = 0.0;
			Assert.isTrue(avgOfResultsInFinders.equals(avgOfResultsInFindersExpected) && maximumOfResultsInFinders.equals(maximumOfResultsInFindersExpected) && minimumOfResultsInFinders.equals(minimumOfResultsInFindersExpected)
				&& sDOfResultsInFinders.equals(sDOfResultsInFindersExpected) && ratioOfEmptyVsNotEmptyFinders.equals(ratioOfEmptyVsNotEmptyFindersExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
