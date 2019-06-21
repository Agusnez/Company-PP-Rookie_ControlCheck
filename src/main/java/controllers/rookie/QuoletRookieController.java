
package controllers.rookie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
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

	@Autowired
	private ApplicationService		applicationService;


	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (this.applicationService.existApplication(applicationId)) {

			quolets = this.quoletService.quoletsPublishedPerApplicationId(applicationId);

			final Boolean security = this.applicationService.securityRookie(applicationId);

			if (security) {

				result = new ModelAndView("quolet/list");
				result.addObject("quolets", quolets);
				result.addObject("banner", banner);
				result.addObject("autoridad", "rookie");
				result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
				result.addObject("applicationId", applicationId);
				result.addObject("requestURI", "quolet/rookie/list.do");

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
}
