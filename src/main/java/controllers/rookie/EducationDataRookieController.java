
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
import services.EducationDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationData;
import forms.EducationDataForm;

@Controller
@RequestMapping("/educationData/rookie")
public class EducationDataRookieController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EducationDataForm educationData;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.curriculumService.exist(curriculumId);

		if (exist) {
			final Boolean security = this.curriculumService.security(curriculumId);

			final Curriculum c = this.curriculumService.findOne(curriculumId);

			if (security && c.getNoCopy()) {
				educationData = new EducationDataForm();

				educationData.setCurriculumId(curriculumId);

				result = this.createEditModelAndView(educationData);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		final EducationDataForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.educationDataService.exist(educationRecordId);

		if (!(educationRecordId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.educationDataService.creteForm(educationRecordId);

			final Boolean security = this.educationDataService.security(educationRecordId);

			final Curriculum c = this.curriculumService.findByEducationDataId(educationRecordId);

			if (c != null && security && c.getNoCopy())
				result = this.createEditModelAndView(form);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("educationData") final EducationDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final EducationData educationReconstruct = this.educationDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.educationDataService.exist(form.getId());

		if ((form.getId() == 0 && existCurriculum) || (form.getId() != 0 && existData && existCurriculum)) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.educationDataService.security(form.getId(), form.getCurriculumId());

			final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

			if (c.getNoCopy() && ((securityCurriculum && securityData) || (form.getId() != 0 && securityData && securityCurriculum))) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						if (form.getId() == 0) {
							final EducationData e = this.educationDataService.save(educationReconstruct);
							c.getEducationDatas().add(e);
							this.curriculumService.save(c);

						} else
							this.educationDataService.save(educationReconstruct);

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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("educationData") final EducationDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final EducationData educationReconstruct = this.educationDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.educationDataService.exist(form.getId());

		if (form.getId() != 0 && existData && existCurriculum) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.educationDataService.security(form.getId(), form.getCurriculumId());

			final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

			if (c.getNoCopy() && ((form.getId() == 0 && securityCurriculum) || (form.getId() != 0 && securityData && securityCurriculum))) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {

						c.getEducationDatas().remove(educationReconstruct);
						this.curriculumService.save(c);
						this.educationDataService.delete(educationReconstruct);

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

	protected ModelAndView createEditModelAndView(final EducationDataForm educationData) {
		ModelAndView result;

		result = this.createEditModelAndView(educationData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationDataForm educationData, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editEducationData");
		result.addObject("educationData", educationData);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
