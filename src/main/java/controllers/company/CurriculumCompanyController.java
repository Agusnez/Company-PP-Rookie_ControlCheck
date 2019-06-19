
package controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ConfigurationService;
import services.CurriculumService;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum/company")
public class CurriculumCompanyController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId, @RequestParam final int applicationId) {
		final ModelAndView result;
		final Curriculum curriculum;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.curriculumService.exist(curriculumId);
		final Boolean exist2 = this.applicationService.existApplication(applicationId);

		if (exist && exist2) {

			final Boolean security = this.curriculumService.securityCompany(applicationId);

			if (security) {

				curriculum = this.curriculumService.findOne(curriculumId);

				result = new ModelAndView("curriculum/displayCurriculum");
				result.addObject("curriculum", curriculum);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/application/company/list.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
}
