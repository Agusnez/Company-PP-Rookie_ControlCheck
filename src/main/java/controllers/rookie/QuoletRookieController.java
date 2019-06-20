
package controllers.rookie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.QuoletService;
import controllers.AbstractController;
import domain.Quolet;

@Controller
//TODO CAMBIO CONTROL: cambiar nombre nueva clase
@RequestMapping("/quolet/rookie")
public class QuoletRookieController extends AbstractController {

	@Autowired
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	private QuoletService			quoletService;

	@Autowired
	private ConfigurationService	configurationService;


	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Quolet quolet = this.quoletService.findOne(quoletId);
		final Integer applicationId = quolet.getApplication().getId();

		result = new ModelAndView("pusit/display");
		result.addObject("autoridad", "rookie");
		result.addObject("banner", banner);
		result.addObject("quolet", quolet);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("applicationId", applicationId);

		return result;

	}

	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		quolets = this.quoletService.quoletsPublishedPerApplicationId(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("pusit/list");
		result.addObject("quolets", quolets);
		result.addObject("banner", banner);
		result.addObject("autoridad", "rookie");
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("applicationId", applicationId);
		result.addObject("requestURI", "quolet/rookie/list.do");

		return result;
	}
}
