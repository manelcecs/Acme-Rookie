
package controllers.provider;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ItemService;
import services.ProviderService;
import utiles.ValidateCollectionURL;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final Item item = new Item();
		return this.createEditModelAndView(item);

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idItem) {
		ModelAndView result;

		final Item item = this.itemService.findOne(idItem);
		final Provider provider = this.providerService.findByPrincipal(LoginService.getPrincipal());
		if (item.getProvider().getId() != provider.getId())
			result = this.listModelAndView("item.cannot.edit");
		else
			result = this.createEditModelAndView(item);
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Item item, final BindingResult binding) {
		ModelAndView result;
		if (item.getLinks() != null) {
			final Collection<String> links = ValidateCollectionURL.deleteURLBlanksInCollection(item.getLinks());
			item.setLinks(links);
			if (ValidateCollectionURL.validateURLCollection(item.getLinks()) != true)
				binding.rejectValue("links", "item.edit.links.error.url");
		}

		if (item.getPictures() != null) {
			final Collection<String> pictures = ValidateCollectionURL.deleteURLBlanksInCollection(item.getPictures());
			item.setPictures(pictures);
			if (ValidateCollectionURL.validateURLCollection(item.getPictures()) != true)
				binding.rejectValue("pictures", "item.edit.pictures.error.url");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(item);
		else
			try {
				final Item itemRec = this.itemService.reconstruct(item, binding);
				this.itemService.save(itemRec);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(item, "cannot.save.item");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView(null);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idItem) {
		ModelAndView result;
		final Item item = this.itemService.findOne(idItem);

		try {
			this.itemService.delete(item);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("item.cannot.delete");
		}
		return result;

	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("item/list");
		final Provider provider = this.providerService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Item> items = this.itemService.getItemsOfProvider(provider.getId());
		result.addObject("items", items);
		result.addObject("provider", true);
		result.addObject("requestURI", "problem/company/list.do");
		result.addObject("message", message);
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idItem) {
		final ModelAndView result;

		final Item item = this.itemService.findOne(idItem);

		result = new ModelAndView("item/display");

		result.addObject("item", item);

		this.configValues(result);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Item item) {
		return this.createEditModelAndView(item, null);
	}

	protected ModelAndView createEditModelAndView(final Item item, final String message) {
		final ModelAndView result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("message", message);
		this.configValues(result);

		return result;
	}
}
