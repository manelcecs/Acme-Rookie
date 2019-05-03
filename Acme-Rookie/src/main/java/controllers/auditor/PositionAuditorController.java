
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/position/auditor")
public class PositionAuditorController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/assign.do", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int idPosition) {
		ModelAndView result;
		try {
			this.positionService.assignPosition(idPosition);
			result = new ModelAndView("redirect:listAssigned.do");
		} catch (final Throwable oops) {
			final Collection<Position> positionsAssigned = this.positionService.getPositionsByAuditor();
			result = this.listModelAndView(positionsAssigned, "position/auditor/listUnassigned.do", "cannot.assign.position");
			oops.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView list() {
		final Collection<Position> allPositions = this.positionService.getAllPositionsFiltered();
		return this.listModelAndView(allPositions, "position/auditor/list.do", null);
	}

	@RequestMapping(value = "/listAssigned.do", method = RequestMethod.GET)
	public ModelAndView listAssigned() {
		final Collection<Position> positionsAssigned = this.positionService.getPositionsByAuditor();

		return this.listModelAndView(positionsAssigned, "position/auditor/listAssigned.do", null);
	}

	@RequestMapping(value = "/listUnassigned.do", method = RequestMethod.GET)
	public ModelAndView listUnassigned() {
		final Collection<Position> positionsAssigned = this.positionService.getPositionsWithoutAuditor();

		return this.listModelAndView(positionsAssigned, "position/auditor/listUnassigned.do", null);
	}
	protected ModelAndView listModelAndView(final Collection<Position> positions, final String requestURI, final String message) {
		final ModelAndView result = new ModelAndView("position/list");

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());

		result.addObject("auditorLogged", auditorLogged);
		result.addObject("positions", positions);
		result.addObject("viewAll", true);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}
}
