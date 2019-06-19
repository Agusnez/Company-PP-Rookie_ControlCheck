
package controllers.rookie;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculumService;
import services.PositionService;
import services.ProblemService;
import services.RookieService;
import domain.Application;
import domain.Curriculum;
import domain.Problem;
import domain.Rookie;
import forms.ApplicationForm;

@Controller
@RequestMapping("/application/rookie")
public class ApplicationRookieController {

	// Services ---------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.applicationService.existApplication(applicationId);

		if (exist) {

			final Boolean security = this.applicationService.securityRookie(applicationId);

			if (security) {

				application = this.applicationService.findOne(applicationId);

				result = new ModelAndView("application/display");
				result.addObject("application", application);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/application/rookie/list.do");
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Application> applicationAccepted;
		Collection<Application> applicationRejected;
		Collection<Application> applicationSubmitted;
		Collection<Application> applicationPending;
		Collection<Application> applicationCancelled;

		final Rookie rookie = this.rookieService.findByPrincipal();

		applicationAccepted = this.applicationService.findAllAcceptedByRookie(rookie.getId());
		applicationRejected = this.applicationService.findAllRejectedByRookie(rookie.getId());
		applicationSubmitted = this.applicationService.findAllSubmittedByRookie(rookie.getId());
		applicationPending = this.applicationService.findAllPendingByRookie(rookie.getId());
		applicationCancelled = this.applicationService.findAllCancelledByRookie(rookie.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/list");
		result.addObject("applicationAccepted", applicationAccepted);
		result.addObject("applicationRejected", applicationRejected);
		result.addObject("applicationSubmitted", applicationSubmitted);
		result.addObject("applicationPending", applicationPending);
		result.addObject("applicationCancelled", applicationCancelled);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/rookie/list.do");

		return result;
	}

	@RequestMapping(value = "/listObsoletes", method = RequestMethod.GET)
	public ModelAndView listObsoletes() {
		final ModelAndView result;
		Collection<Application> applicationObsoletes;

		final Rookie rookie = this.rookieService.findByPrincipal();

		applicationObsoletes = this.applicationService.findAllDeadLinePastByRookie(rookie.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/listObsoletes");
		result.addObject("applicationObsoletes", applicationObsoletes);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/rookie/listObsoletes.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		ModelAndView result;
		final ApplicationForm applicationForm = new ApplicationForm();

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.positionService.exist(positionId);

		final Date now = new Date(System.currentTimeMillis() - 1000);

		if (exist && this.positionService.findOne(positionId).getDeadline().after(now) && this.positionService.findOne(positionId).getCancellation() == null) {

			final Problem problemDisplay = this.positionService.ramdomProblem(positionId);

			applicationForm.setPosition(positionId);
			applicationForm.setProblem(problemDisplay.getId());
			result = this.createEditModelAndView(applicationForm);
			result.addObject("problemDisplay", problemDisplay);

		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Boolean security;

		final Boolean exist = this.applicationService.existApplication(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (exist && this.applicationService.findOne(applicationId).getPosition().getCancellation() == null) {

			security = this.applicationService.securityRookie(applicationId);
			final Application application = this.applicationService.findOne(applicationId);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			if (security && (application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING")) && application.getPosition().getDeadline().after(now)) {

				final ApplicationForm applicationForm = this.applicationService.editForm(application);

				result = this.createEditModelAndView(applicationForm, null);

				Problem problemDisplay;

				if (applicationForm.getId() == 0)
					problemDisplay = this.positionService.ramdomProblem(applicationForm.getPosition());
				else
					problemDisplay = this.problemService.findOne(applicationForm.getProblem());

				result.addObject("problemDisplay", problemDisplay);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "application") final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result;
		Application application = null;

		final Boolean security = this.applicationService.securityRookie(applicationForm.getId());
		final Boolean exist = this.positionService.exist(applicationForm.getPosition());
		final Boolean exist2 = this.problemService.exist(applicationForm.getProblem());

		if (security && exist && exist2)
			application = this.applicationService.reconstruct(applicationForm, binding);

		final Date now = new Date(System.currentTimeMillis() - 1000);

		if (exist && exist2 && this.problemService.findProblemsByPositionId(applicationForm.getPosition()).contains(this.problemService.findOne(applicationForm.getProblem())) && security
			&& (application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING")) && application.getPosition().getDeadline().after(now) && application.getPosition().getFinalMode()) {

			if (binding.hasErrors())
				result = this.createEditModelAndView(applicationForm, null);
			else
				try {

					final Curriculum curriculum = application.getCurriculum();

					if (application.getId() == 0) {
						final Curriculum copy = this.curriculumService.copyCurriculum(curriculum);
						application.setCurriculum(copy);
					}

					this.applicationService.save(application);
					result = new ModelAndView("redirect:/application/rookie/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(applicationForm, "application.commit.error");

				}

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;

		result = this.createEditModelAndView(applicationForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ApplicationForm applicationForm, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Collection<Curriculum> curriculums = this.curriculumService.findByRookieId(this.rookieService.findByPrincipal().getId());

		final Map<Integer, String> curriculumsMap = new HashMap<>();

		for (final Curriculum curriculum : curriculums)
			curriculumsMap.put(curriculum.getId(), curriculum.getPersonalData().getStatement());

		result = new ModelAndView("application/edit");
		result.addObject("application", applicationForm);
		result.addObject("curriculumsMap", curriculumsMap);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
