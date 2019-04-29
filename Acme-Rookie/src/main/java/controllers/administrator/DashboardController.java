
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CompanyService;
import services.PositionService;
import services.RookieService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Rookie;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private RookieService			rookieService;


	@RequestMapping(value = "/administrator/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("administrator/dashboard");

		final Double avgOfPositionsPerCompany = this.administratorService.getAvgOfPositionsPerCompany();
		if (avgOfPositionsPerCompany != null)
			result.addObject("avgOfPositionsPerCompany", avgOfPositionsPerCompany);
		else
			result.addObject("avgOfPositionsPerCompany", 0.0);

		final Integer minimumOfPositionsPerCompany = this.administratorService.getMinimumOfPositionsPerCompany();
		if (minimumOfPositionsPerCompany != null)
			result.addObject("minimumOfPositionsPerCompany", minimumOfPositionsPerCompany);
		else
			result.addObject("minimumOfPositionsPerCompany", 0);

		final Integer maximumOfPositionsPerCompany = this.administratorService.getMaximumOfPositionsPerCompany();
		if (maximumOfPositionsPerCompany != null)
			result.addObject("maximumOfPositionsPerCompany", maximumOfPositionsPerCompany);
		else
			result.addObject("maximumOfPositionsPerCompany", 0);

		final Double sDOfPositionsPerCompany = this.administratorService.getSDOfPositionsPerCompany();
		if (sDOfPositionsPerCompany != null)
			result.addObject("sDOfPositionsPerCompany", sDOfPositionsPerCompany);
		else
			result.addObject("sDOfPositionsPerCompany", 0.0);

		final Double avgOfApplicationsPerRookie = this.administratorService.getAvgOfApplicationsPerRookie();
		if (avgOfApplicationsPerRookie != null)
			result.addObject("avgOfApplicationsPerRookie", avgOfApplicationsPerRookie);
		else
			result.addObject("avgOfApplicationsPerRookie", 0.0);

		final Integer minimumOfApplicationsPerRookie = this.administratorService.getMinimumOfApplicationsPerRookie();
		if (minimumOfApplicationsPerRookie != null)
			result.addObject("minimumOfApplicationsPerRookie", minimumOfApplicationsPerRookie);
		else
			result.addObject("minimumOfApplicationsPerRookie", 0);

		final Integer maximumOfApplicationsPerRookie = this.administratorService.getMaximumOfApplicationsPerRookie();
		if (maximumOfApplicationsPerRookie != null)
			result.addObject("maximumOfApplicationsPerRookie", maximumOfApplicationsPerRookie);
		else
			result.addObject("maximumOfApplicationsPerRookie", 0);

		final Double sDOfApplicationsPerRookie = this.administratorService.getSDOfApplicationsPerRookie();
		if (sDOfApplicationsPerRookie != null)
			result.addObject("sDOfApplicationsPerRookie", sDOfApplicationsPerRookie);
		else
			result.addObject("sDOfApplicationsPerRookie", 0.0);

		final Double avgOfSalariesOffered = this.administratorService.getAvgOfSalariesOffered();
		if (avgOfSalariesOffered != null)
			result.addObject("avgOfSalariesOffered", avgOfSalariesOffered);
		else
			result.addObject("avgOfSalariesOffered", 0.0);

		final Integer minimumOfSalariesOffered = this.administratorService.getMinimumOfSalariesOffered();
		if (minimumOfSalariesOffered != null)
			result.addObject("minimumOfSalariesOffered", minimumOfSalariesOffered);
		else
			result.addObject("minimumOfSalariesOffered", 0);

		final Integer maximumOfSalariesOffered = this.administratorService.getMaximumOfSalariesOffered();
		if (maximumOfSalariesOffered != null)
			result.addObject("maximumOfSalariesOffered", maximumOfSalariesOffered);
		else
			result.addObject("maximumOfSalariesOffered", 0);

		final Double sDOfSalariesOffered = this.administratorService.getSDOfSalariesOffered();
		if (sDOfSalariesOffered != null)
			result.addObject("sDOfSalariesOffered", sDOfSalariesOffered);
		else
			result.addObject("sDOfSalariesOffered", 0.0);

		final Double avgOfCurriculaPerRookie = this.administratorService.getAvgOfCurriculaPerRookie();
		if (avgOfCurriculaPerRookie != null)
			result.addObject("avgOfCurriculaPerRookie", avgOfCurriculaPerRookie);
		else
			result.addObject("avgOfCurriculaPerRookie", 0.0);

		final Integer minimumOfCurriculaPerRookie = this.administratorService.getMinimumOfCurriculaPerRookie();
		if (minimumOfCurriculaPerRookie != null)
			result.addObject("minimumOfCurriculaPerRookie", minimumOfCurriculaPerRookie);
		else
			result.addObject("minimumOfCurriculaPerRookie", 0);

		final Integer maximumOfCurriculaPerRookie = this.administratorService.getMaximumOfCurriculaPerRookie();
		if (maximumOfCurriculaPerRookie != null)
			result.addObject("maximumOfCurriculaPerRookie", maximumOfCurriculaPerRookie);
		else
			result.addObject("maximumOfCurriculaPerRookie", 0);

		final Double sDOfCurriculaPerRookie = this.administratorService.getSDOfCurriculaPerRookie();
		if (sDOfCurriculaPerRookie != null)
			result.addObject("sDOfCurriculaPerRookie", sDOfCurriculaPerRookie);
		else
			result.addObject("sDOfCurriculaPerRookie", 0.0);

		final Double avgOfResultsInFinders = this.administratorService.getAvgOfResultsInFinders();
		if (avgOfResultsInFinders != null)
			result.addObject("avgOfResultsInFinders", avgOfResultsInFinders);
		else
			result.addObject("avgOfResultsInFinders", 0.0);

		final Integer minimumOfResultsInFinders = this.administratorService.getMinimumOfResultsInFinders();
		if (minimumOfResultsInFinders != null)
			result.addObject("minimumOfResultsInFinders", minimumOfResultsInFinders);
		else
			result.addObject("minimumOfResultsInFinders", 0);

		final Integer maximumOfResultsInFinders = this.administratorService.getMaximumOfResultsInFinders();
		if (maximumOfResultsInFinders != null)
			result.addObject("maximumOfResultsInFinders", maximumOfResultsInFinders);
		else
			result.addObject("maximumOfResultsInFinders", 0);

		final Double sDOfResultsInFinders = this.administratorService.getSDOfResultsInFinders();
		if (sDOfResultsInFinders != null)
			result.addObject("sDOfResultsInFinders", sDOfResultsInFinders);
		else
			result.addObject("sDOfResultsInFinders", 0.0);

		final Double ratioOfEmptyVsNotEmptyFinders = this.administratorService.getRatioOfEmptyVsNotEmptyFinders();
		if (ratioOfEmptyVsNotEmptyFinders != null)
			result.addObject("ratioOfEmptyVsNotEmptyFinders", ratioOfEmptyVsNotEmptyFinders);
		else
			result.addObject("ratioOfEmptyVsNotEmptyFinders", 0.0);

		final Collection<Position> positionsWithTheBestSalary = this.positionService.getPositionsWithTheBestSalary();
		result.addObject("positionsWithTheBestSalary", positionsWithTheBestSalary);

		final Collection<Position> positionsWithTheWorstSalary = this.positionService.getPositionsWithTheWorstSalary();
		result.addObject("positionsWithTheWorstSalary", positionsWithTheWorstSalary);

		final Collection<Rookie> rookiesWithMoreApplications = this.rookieService.getRookiesWithMoreApplications();
		result.addObject("rookiesWithMoreApplications", rookiesWithMoreApplications);

		final Collection<Company> companiesWithMoreOffersOfPositions = this.companyService.getCompaniesWithMoreOffersOfPositions();
		result.addObject("companiesWithMoreOffersOfPositions", companiesWithMoreOffersOfPositions);

		result.addObject("requestURI", "dashboard/administrator/display.do");

		this.configValues(result);
		return result;
	}
}
