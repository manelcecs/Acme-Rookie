
package controllers.rookie;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RookieService;
import controllers.AbstractController;
import domain.Rookie;
import forms.RookieForm;

@Controller
@RequestMapping("/rookie")
public class RookieController extends AbstractController {

	@Autowired
	private RookieService	rookieService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final RookieForm rookieForm = new RookieForm();

		res = this.createModelAndViewEdit(rookieForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final RookieForm rookieForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Rookie rookieRect = this.rookieService.reconstruct(rookieForm, binding);
			this.rookieService.save(rookieRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(rookieForm);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(rookieForm, "rookie.edit.commit.error");
		}

		return res;

	}

	@RequestMapping(value = "/rookie/save", method = RequestMethod.POST, params = "submit")
	public ModelAndView saveAdmin(final Rookie rookie, final BindingResult binding) {
		ModelAndView res;

		try {
			final Rookie rookieRect = this.rookieService.reconstruct(rookie, binding);
			this.rookieService.save(rookieRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createModelAndViewEdit(rookie);
		} catch (final Throwable oops) {
			res = this.createModelAndViewEdit(rookie);
		}
		return res;
	}

	/*
	 * MODELANDVIEWS METHODS
	 */

	protected ModelAndView createModelAndViewEdit(final RookieForm rookieForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("rookie/edit");
		result.addObject("rookieForm", rookieForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createModelAndViewEdit(final Rookie rookie, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("rookie/edit");
		result.addObject("rookie", rookie);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

}
