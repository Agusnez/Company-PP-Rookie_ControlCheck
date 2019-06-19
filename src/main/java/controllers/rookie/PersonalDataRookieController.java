
package controllers.rookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CurriculumService;
import services.PersonalDataService;
import domain.Curriculum;
import domain.PersonalData;
import forms.PersonalDataForm;

@Controller
@RequestMapping("/personalData/rookie")
public class PersonalDataRookieController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		final PersonalDataForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.personalDataService.exist(personalDataId);

		if (!(personalDataId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.personalDataService.createForm(personalDataId);

			final Boolean security = this.personalDataService.security(personalDataId);

			final Curriculum c = this.curriculumService.findByPersonalDataId(personalDataId);

			if (c != null && security && c.getNoCopy())
				result = this.createEditModelAndView(form);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("personalData") final PersonalDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final PersonalData personalReconstruct = this.personalDataService.reconstruct(form, binding);

		final Boolean existPersonalData = this.personalDataService.exist(form.getId());

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		if (form.getId() != 0 && form.getCurriculumId() != 0 && existPersonalData && existCurriculum) {

			final Boolean security1 = this.personalDataService.security(personalReconstruct.getId());

			final Boolean security2 = this.curriculumService.security(form.getCurriculumId());

			final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

			if (security1 && security2 && c.getNoCopy()) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						this.personalDataService.save(personalReconstruct);
						result = new ModelAndView("redirect:/curriculum/rookie/display.do?curriculumId=" + form.getCurriculumId());

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(form, "curriculum.commit.error");
					}

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalDataForm form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalDataForm form, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editPersonalData");
		result.addObject("personalData", form);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
