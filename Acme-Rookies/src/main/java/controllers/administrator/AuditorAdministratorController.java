
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdminConfigService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;
import forms.AuditorForm;

@Controller
@RequestMapping("/auditor/administrator")
public class AuditorAdministratorController extends AbstractController {

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private AdminConfigService	adminConfigService;


	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView res;

		final AuditorForm auditorForm = new AuditorForm();

		res = this.createModelAndView(auditorForm);

		return res;
	}

	@RequestMapping("/save")
	public ModelAndView save(final AuditorForm auditorForm, final BindingResult binding) {
		ModelAndView res;

		try {
			final Auditor auditor = this.auditorService.reconstruct(auditorForm, binding);
			this.auditorService.save(auditor);
			res = new ModelAndView("redirect:/");
		} catch (final ValidationException oops) {
			res = this.createModelAndView(auditorForm);
		} catch (final Throwable oops) {
			res = this.createModelAndView(auditorForm, "auditor.edit.commit.error");
		}
		return res;
	}

	protected ModelAndView createModelAndView(final AuditorForm auditorForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("auditorForm", auditorForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
