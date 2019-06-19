
package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private PositionService			positionService;


	//List para todo el mundo ------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Company> companies;

		companies = this.companyService.findAll();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		return result;

	}

	//Display para todo el mundo ------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		final Position position = this.positionService.findOne(positionId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			result = new ModelAndView("company/display");
			result.addObject("company", position.getCompany());
			result.addObject("banner", banner);
			result.addObject("backUri", "position/list.do");

		}
		return result;
	}
}
