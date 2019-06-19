
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
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Controller
@RequestMapping("/miscellaneousData/rookie")
public class MiscellaneousDataRookieController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private ConfigurationService		configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		MiscellaneousDataForm miscellaneousData;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.curriculumService.exist(curriculumId);

		if (exist) {
			final Boolean security = this.curriculumService.security(curriculumId);

			final Curriculum c = this.curriculumService.findOne(curriculumId);

			if (c.getNoCopy() && security) {
				miscellaneousData = new MiscellaneousDataForm();

				miscellaneousData.setCurriculumId(curriculumId);

				result = this.createEditModelAndView(miscellaneousData);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		final MiscellaneousDataForm form;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.miscellaneousDataService.exist(miscellaneousRecordId);

		if (!(miscellaneousRecordId != 0 && exist)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			form = this.miscellaneousDataService.creteForm(miscellaneousRecordId);

			final Boolean security = this.miscellaneousDataService.security(miscellaneousRecordId);

			final Curriculum c = this.curriculumService.findByMiscellaneousDataId(miscellaneousRecordId);

			if (c != null && c.getNoCopy() && security)
				result = this.createEditModelAndView(form);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("miscellaneousData") final MiscellaneousDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final MiscellaneousData miscellaneousReconstruct = this.miscellaneousDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.miscellaneousDataService.exist(form.getId());

		if ((form.getId() == 0 && existCurriculum) || (form.getId() != 0 && existData && existCurriculum)) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.miscellaneousDataService.security(form.getId(), form.getCurriculumId());

			final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

			if (c.getNoCopy() && ((securityCurriculum && securityData) || (form.getId() != 0 && securityData && securityCurriculum))) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {
						if (form.getId() == 0) {
							final MiscellaneousData e = this.miscellaneousDataService.save(miscellaneousReconstruct);
							c.getMiscellaneousDatas().add(e);
							this.curriculumService.save(c);

						} else
							this.miscellaneousDataService.save(miscellaneousReconstruct);

						result = new ModelAndView("redirect:/curriculum/rookie/display.do?curriculumId=" + form.getCurriculumId());

					} catch (final Throwable oops) {
						if (oops.getMessage() == "Invalid URL")
							result = this.createEditModelAndView(form, "url.error");
						else
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
	public ModelAndView delete(@ModelAttribute("miscellaneousData") final MiscellaneousDataForm form, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final MiscellaneousData miscellanousReconstruct = this.miscellaneousDataService.reconstruct(form, binding);

		final Boolean existCurriculum = this.curriculumService.exist(form.getCurriculumId());

		final Boolean existData = this.miscellaneousDataService.exist(form.getId());

		if (form.getId() != 0 && existData && existCurriculum) {

			final Boolean securityCurriculum = this.curriculumService.security(form.getCurriculumId());
			final Boolean securityData = this.miscellaneousDataService.security(form.getId(), form.getCurriculumId());

			final Curriculum c = this.curriculumService.findOne(form.getCurriculumId());

			if (c.getNoCopy() && ((form.getId() == 0 && securityCurriculum) || (form.getId() != 0 && securityData && securityCurriculum))) {

				if (binding.hasErrors())
					result = this.createEditModelAndView(form);
				else
					try {

						c.getMiscellaneousDatas().remove(miscellanousReconstruct);
						this.curriculumService.save(c);
						this.miscellaneousDataService.delete(miscellanousReconstruct);

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

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm miscellaneousData) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousDataForm miscellaneousData, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("curriculum/editMiscellaneousData");
		result.addObject("miscellaneousData", miscellaneousData);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
