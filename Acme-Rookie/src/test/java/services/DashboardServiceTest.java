
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import domain.Provider;
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
	@Autowired
	private ProviderService			providerService;


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
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, 7999.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, 7999.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, 7999.0, IllegalArgumentException.class
			}, {
				/**
				 * a) 11.2: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 5514.923076923077, 7999, 3200, 2323.9076991315383, bestPositions, worstPositions, 7999.0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardSalaryTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Collection<Position>) testingData[i][5],
				(Collection<Position>) testingData[i][6], (Double) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void dashboardSalaryTemplate(final String beanName, final Double avgOfSalariesOfferedExpected, final Integer maximumOfSalariesOfferedExpected, final Integer minimumOfSalariesOfferedExpected, final Double sDOfSalariesOfferedExpected,
		final Collection<Position> positionsWithTheBestSalaryExpected, final Collection<Position> positionsWithTheWorstSalaryExpected, final Double avgOfSalaryOfPositionsWithTheHighestAvgAuditScoreExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfSalariesOffered = this.administratorService.getAvgOfSalariesOffered();
			final Integer maximumOfSalariesOffered = this.administratorService.getMaximumOfSalariesOffered();
			final Integer minimumOfSalariesOffered = this.administratorService.getMinimumOfSalariesOffered();
			final Double sDOfSalariesOffered = this.administratorService.getSDOfSalariesOffered();
			final Double avgOfSalaryOfPositionsWithTheHighestAvgAuditScore = this.administratorService.getAvgOfSalaryOfPositionWithTheHighestAvgOfAuditScore();

			final Collection<Position> positionsWithTheBestSalary = this.positionService.getPositionsWithTheBestSalary();
			final Collection<Position> positionsWithTheWorstSalary = this.positionService.getPositionsWithTheWorstSalary();

			Assert.isTrue(positionsWithTheWorstSalary.containsAll(positionsWithTheWorstSalaryExpected) && positionsWithTheWorstSalaryExpected.size() == positionsWithTheWorstSalary.size());
			Assert.isTrue(positionsWithTheBestSalary.containsAll(positionsWithTheBestSalaryExpected) && positionsWithTheBestSalaryExpected.size() == positionsWithTheBestSalary.size());
			Assert.isTrue(avgOfSalariesOffered.equals(avgOfSalariesOfferedExpected) && maximumOfSalariesOffered.equals(maximumOfSalariesOfferedExpected) && minimumOfSalariesOffered.equals(minimumOfSalariesOfferedExpected)
				&& sDOfSalariesOffered.equals(sDOfSalariesOfferedExpected) && avgOfSalaryOfPositionsWithTheHighestAvgAuditScore.equals(avgOfSalaryOfPositionsWithTheHighestAvgAuditScoreExpected));

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

	/**
	 * This test reefer to use case 18.1
	 * here we're going to test the dashboard metrics related to finders for the administrator
	 * One positive
	 * Three negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void dashboardAuditDriver() {

		final Collection<Company> companiesWithTheHighestAuditScore = new ArrayList<Company>();
		companiesWithTheHighestAuditScore.add(this.companyService.findOne(this.getEntityId("company2")));
		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 7.166666667, 5.5, 10.0, 2.013840996, 7.166666667, 5.5, 10.0, 2.013840996, companiesWithTheHighestAuditScore, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 7.166666667, 5.5, 10.0, 2.013840996, 7.166666667, 5.5, 10.0, 2.013840996, companiesWithTheHighestAuditScore, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 7.166666667, 5.5, 10.0, 2.013840996, 7.166666667, 5.5, 10.0, 2.013840996, companiesWithTheHighestAuditScore, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 7.166666667, 5.5, 10.0, 2.013840996, 7.166666667, 5.5, 10.0, 2.013840996, companiesWithTheHighestAuditScore, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardAuditTemplate((String) testingData[i][0], (Double) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(Double) testingData[i][7], (Double) testingData[i][8], (Collection<Company>) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void dashboardAuditTemplate(final String beanName, final Double avgOfAuditScoreOfPositionExpected, final Double minimumOfAuditScoreOfPositionExpected, final Double maximumOfAuditScoreOfPositionExpected,
		final Double sDOfAuditScoreOfPositionExpected, final Double avgOfAuditScoreOfCompanyExpected, final Double minimumOfAuditScoreOfCompanyExpected, final Double maximumOfAuditScoreOfCompanyExpected, final Double sDOfAuditScoreOfCompanyExpected,
		final Collection<Company> companiesWithTheHighestAuditScoreExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfAuditScoreOfPosition = this.administratorService.getAvgOfAuditScoreOfPosition();
			final Double minimumOfAuditScoreOfPosition = this.administratorService.getMinimumOfAuditScoreOfPosition();
			final Double maximumOfAuditScoreOfPosition = this.administratorService.getMaximumOfAuditScoreOfPosition();
			final Double sDOfAuditScoreOfPosition = this.administratorService.getSDOfAuditScoreOfPosition();
			final Double avgOfAuditScoreOfCompany = this.administratorService.getAvgOfAuditScoreOfCompany();
			final Double minimumOfAuditScoreOfCompany = this.administratorService.getMinimumOfAuditScoreOfCompany();
			final Double maximumOfAuditScoreOfCompany = this.administratorService.getMaximumOfAuditScoreOfCompany();
			final Double sDOfAuditScoreOfCompany = this.administratorService.getSDOfAuditScoreOfCompany();
			final Collection<Company> companiesWithTheHighestAuditScore = this.companyService.getCompaniesWithTheHighestAuditScore();
			Assert.isTrue(avgOfAuditScoreOfPosition.equals(avgOfAuditScoreOfPositionExpected) && maximumOfAuditScoreOfPosition.equals(maximumOfAuditScoreOfPositionExpected) && minimumOfAuditScoreOfPosition.equals(minimumOfAuditScoreOfPositionExpected)
				&& sDOfAuditScoreOfPosition.equals(sDOfAuditScoreOfPositionExpected));
			Assert.isTrue(avgOfAuditScoreOfCompany.equals(avgOfAuditScoreOfCompanyExpected) && maximumOfAuditScoreOfCompany.equals(maximumOfAuditScoreOfCompanyExpected) && minimumOfAuditScoreOfCompany.equals(minimumOfAuditScoreOfCompanyExpected)
				&& sDOfAuditScoreOfCompany.equals(sDOfAuditScoreOfCompanyExpected));
			Assert.isTrue(companiesWithTheHighestAuditScore.containsAll(companiesWithTheHighestAuditScoreExpected) && companiesWithTheHighestAuditScoreExpected.size() == companiesWithTheHighestAuditScore.size());

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
	public void dashboardSponsorshipDriver() {

		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 1.0, 1, 1, 0.0, 0.2353, 0, 1, 0.5455, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 1.0, 1, 1, 0.0, 0.2353, 0, 1, 0.5455, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 1.0, 1, 1, 0.0, 0.2353, 0, 1, 0.5455, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 1.0, 1, 1, 0.0, 0.2353, 0, 1, 0.5455, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardSponsorshipTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Double) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void dashboardSponsorshipTemplate(final String beanName, final Double avgOfSponsorshipsPerProviderExpected, final Integer minimumOfSponsorshipsPerProviderExpected, final Integer maximumOfSponsorshipsPerProviderExpected,
		final Double sDOfSponsorshipsPerProviderExpected, final Double avgOfSponsorshipsPerPositionExpected, final Integer minimumOfSponsorshipsPerPositionExpected, final Integer maximumOfSponsorshipsPerPositionExpected,
		final Double sDOfSponsorshipsPerPositionExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfSponsorshipsPerProvider = this.administratorService.getAvgOfSponsorshipsPerProvider();
			final Integer minimumOfSponsorshipsPerProvider = this.administratorService.getMinimumOfSponsorshipsPerProvider();
			final Integer maximumOfSponsorshipsPerProvider = this.administratorService.getMaximumOfSponsorshipsPerProvider();
			final Double sDOfSponsorshipsPerProvider = this.administratorService.getSDOfSponsorshipsPerProvider();
			final Double avgOfSponsorshipsPerPosition = this.administratorService.getAvgOfSponsorshipsPerPosition();
			final Integer minimumOfSponsorshipsPerPosition = this.administratorService.getMinimumOfSponsorshipsPerPosition();
			final Integer maximumOfSponsorshipsPerPosition = this.administratorService.getMaximumOfSponsorshipsPerPosition();
			final Double sDOfSponsorshipsPerPosition = this.administratorService.getSDOfSponsorshipsPerPosition();
			Assert.isTrue(avgOfSponsorshipsPerProvider.equals(avgOfSponsorshipsPerProviderExpected) && maximumOfSponsorshipsPerProvider.equals(maximumOfSponsorshipsPerProviderExpected)
				&& minimumOfSponsorshipsPerProvider.equals(minimumOfSponsorshipsPerProviderExpected) && sDOfSponsorshipsPerProvider.equals(sDOfSponsorshipsPerProviderExpected));
			Assert.isTrue(avgOfSponsorshipsPerPosition.equals(avgOfSponsorshipsPerPositionExpected) && maximumOfSponsorshipsPerPosition.equals(maximumOfSponsorshipsPerPositionExpected)
				&& minimumOfSponsorshipsPerPosition.equals(minimumOfSponsorshipsPerPositionExpected) && sDOfSponsorshipsPerPosition.equals(sDOfSponsorshipsPerPositionExpected));

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
	@SuppressWarnings({
		"unchecked", "null"
	})
	@Test
	public void dashboardItemDriver() {

		final List<Provider> top5OfProvidersWithMoreItems = new ArrayList<Provider>();

		top5OfProvidersWithMoreItems.add(this.providerService.findOne(this.getEntityId("provider0")));
		top5OfProvidersWithMoreItems.add(this.providerService.findOne(this.getEntityId("provider1")));

		final Object testingData[][] = {
			{
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(rookie)
				 * c) 50%
				 * d)
				 * 
				 */
				this.rookieService.findAll().iterator().next().getUserAccount().getUsername(), 2.0, 2, 2, 0.0, top5OfProvidersWithMoreItems, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(company)
				 * c) 50%
				 * d)
				 * 
				 */
				this.companyService.findAll().iterator().next().getUserAccount().getUsername(), 2.0, 2, 2, 0.0, top5OfProvidersWithMoreItems, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Must be an administrator(not logged)
				 * c) 50%
				 * d)
				 * 
				 */
				null, 2.0, 2, 2, 0.0, top5OfProvidersWithMoreItems, IllegalArgumentException.class
			}, {
				/**
				 * a) 18.1: show metrics as an Administrator
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				this.administratorService.findAll().iterator().next().getUserAccount().getUsername(), 2.0, 2, 2, 0.0, top5OfProvidersWithMoreItems, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardItemTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (List<Provider>) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void dashboardItemTemplate(final String beanName, final Double avgOfItemsPerProviderExpected, final Integer minimumOfItemsPerProviderExpected, final Integer maximumOfItemsPerProviderExpected, final Double sDOfItemsPerProviderExpected,
		final List<Provider> top5OfProvidersWithMoreItemsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfItemsPerProvider = this.administratorService.getAvgOfItemsPerProvider();
			final Integer minimumOfItemsPerProvider = this.administratorService.getMinimumOfItemsPerProvider();
			final Integer maximumOfItemsPerProvider = this.administratorService.getMaximumOfItemsPerProvider();
			final Double sDOfItemsPerProvider = this.administratorService.getSDOfItemsPerProvider();

			final List<Object[]> top5OfProvidersWithMoreItems = this.providerService.getTop5OfProvidersByItems();
			Assert.isTrue(avgOfItemsPerProvider.equals(avgOfItemsPerProviderExpected) && maximumOfItemsPerProvider.equals(maximumOfItemsPerProviderExpected) && minimumOfItemsPerProvider.equals(minimumOfItemsPerProviderExpected)
				&& sDOfItemsPerProvider.equals(sDOfItemsPerProviderExpected));

			Assert.isTrue(top5OfProvidersWithMoreItems.get(0)[0].equals(top5OfProvidersWithMoreItemsExpected.get(0)) && top5OfProvidersWithMoreItems.get(1)[0].equals(top5OfProvidersWithMoreItemsExpected.get(1)));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
