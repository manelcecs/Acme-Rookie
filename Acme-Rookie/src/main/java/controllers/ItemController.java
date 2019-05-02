
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService	itemService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer idProvider) {
		final ModelAndView result = new ModelAndView("item/list");
		Collection<Item> items;

		if (idProvider == null)
			items = this.itemService.findAll();
		else {
			result.addObject("idProvider", idProvider);
			items = this.itemService.getItemsOfProvider(idProvider);
		}

		result.addObject("items", items);
		result.addObject("viewAll", true);
		result.addObject("requestURI", "item/list.do");

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idItem) {
		ModelAndView result;

		final Item item = this.itemService.findOne(idItem);

		result = new ModelAndView("item/display");
		result.addObject("item", item);
		result.addObject("requestURI", "item/display.do?idItem=" + idItem);

		this.configValues(result);
		return result;
	}
}
