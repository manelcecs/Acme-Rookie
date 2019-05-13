
package controllers.auditor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping("/save")
	public ModelAndView save(final Auditor auditor, final BindingResult binding) {
		ModelAndView res;

		try {
			final Auditor result = this.auditorService.reconstruct(auditor, binding);
			this.auditorService.save(result);
			res = new ModelAndView("redirect:/");
		} catch (final ValidationException oops) {
			res = this.createModelAndView(auditor);
		} catch (final Throwable oops) {
			res = this.createModelAndView(auditor, "auditor.edit.commit.error");
		}
		return res;
	}

	protected ModelAndView createModelAndView(final Auditor auditor, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("edit", true);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
