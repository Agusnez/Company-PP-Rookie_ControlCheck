
package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService				itemService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ProviderService			providerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();
		final Collection<Item> items;

		items = this.itemService.findAll();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "item/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		return result;

	}

	@RequestMapping(value = "/listByProvider", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int providerId) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Provider notFound = this.providerService.findOne(providerId);

		if (notFound == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			final Collection<Item> items;

			items = this.itemService.findItemsByProviderId(providerId);

			result = new ModelAndView("item/list");
			result.addObject("items", items);
			result.addObject("requestURI", "item/listByProvider.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("autoridad", "");

		}
		return result;

	}

}
