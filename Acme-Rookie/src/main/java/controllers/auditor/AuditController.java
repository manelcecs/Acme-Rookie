
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.AuditorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;
import forms.AuditForm;

@Controller
@RequestMapping("/audit/auditor")
public class AuditController extends AbstractController {

	@Autowired
	AuditService	auditService;

	@Autowired
	AuditorService	auditorService;

	@Autowired
	PositionService	positionService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idAudit) {
		ModelAndView result = new ModelAndView("audit/display");

		final Audit audit = this.auditService.findOne(idAudit);
		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());

		if (!audit.getPosition().getAuditor().equals(auditorLogged))
			result = new ModelAndView("redirect:list.do");
		else {
			result.addObject("audit", audit);
			result.addObject("logged", true);
		}

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView(null);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idAudit) {
		ModelAndView result;
		final Audit audit = this.auditService.findOne(idAudit);
		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());

		if (!audit.getPosition().getAuditor().equals(auditorLogged))
			result = this.listModelAndView("cannot.delete.audit");
		else
			try {
				this.auditService.delete(idAudit);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.listModelAndView("cannot.delete.audit");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final Integer idPosition) {
		final Audit audit = this.auditService.create();

		if (idPosition != null) {
			final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());
			final Position position = this.positionService.findOne(idPosition);

			if (position.getAuditor().equals(auditorLogged))
				audit.setPosition(position);
		}

		return this.createEditModelAndView(this.auditService.castToForm(audit));
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer idAudit) {
		final ModelAndView result;
		final Audit audit = this.auditService.findOne(idAudit);

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());
		if (!audit.getPosition().getAuditor().equals(auditorLogged) || !audit.getDraft())
			result = this.listModelAndView("cannot.edit.audit");
		else
			result = this.createEditModelAndView(this.auditService.castToForm(audit));

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final AuditForm auditForm, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(auditForm);
		else
			try {
				this.auditService.save(this.auditService.reconstruct(auditForm, bindingResult));
				result = new ModelAndView("redirect:list.do");
			} catch (final ValidationException validationException) {
				result = this.createEditModelAndView(auditForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditForm, "cannot.save.audit");
				oops.printStackTrace();
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditForm auditForm) {
		return this.createEditModelAndView(auditForm, null);
	}

	protected ModelAndView createEditModelAndView(final AuditForm auditForm, final String message) {
		final ModelAndView result = new ModelAndView("audit/edit");
		final Collection<Position> positions = this.positionService.getPositionsByAuditor();

		result.addObject("auditForm", auditForm);
		result.addObject("positions", positions);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("audit/list");

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Audit> audits = this.auditService.getAuditsOfAnAuditor(auditorLogged.getId());

		result.addObject("audits", audits);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}
}
