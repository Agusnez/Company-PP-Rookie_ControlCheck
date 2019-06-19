
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
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ItemService				itemService;


	//List para todo el mundo ------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Provider> providers;

		providers = this.providerService.findAll();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("provider/list");
		result.addObject("providers", providers);
		result.addObject("requestURI", "provider/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		return result;

	}

	//Display para todo el mundo ------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int itemId) {
		ModelAndView result;

		final Item item = this.itemService.findOne(itemId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (item == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			result = new ModelAndView("provider/display");
			result.addObject("provider", item.getProvider());
			result.addObject("banner", banner);
			result.addObject("backUri", "item/list.do");

		}
		return result;
	}

}
