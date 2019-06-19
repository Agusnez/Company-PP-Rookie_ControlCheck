
package controllers.company;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.RookieService;
import domain.Application;
import domain.Company;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController {

	// Services ---------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.applicationService.existApplication(applicationId);

		if (exist) {

			final Boolean security = this.applicationService.securityCompany(applicationId);

			if (security) {

				application = this.applicationService.findOne(applicationId);

				result = new ModelAndView("application/display");
				result.addObject("application", application);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/application/company/list.do");
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

		final Company company = this.companyService.findByPrincipal();

		applicationAccepted = this.applicationService.findAllAcceptedByCompany(company.getId());
		applicationRejected = this.applicationService.findAllRejectedByCompany(company.getId());
		applicationSubmitted = this.applicationService.findAllSubmittedByCompany(company.getId());
		applicationPending = this.applicationService.findAllPendingByCompany(company.getId());
		applicationCancelled = this.applicationService.findAllCancelledByCompany(company.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/list");
		result.addObject("applicationAccepted", applicationAccepted);
		result.addObject("applicationRejected", applicationRejected);
		result.addObject("applicationSubmitted", applicationSubmitted);
		result.addObject("applicationPending", applicationPending);
		result.addObject("applicationCancelled", applicationCancelled);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/company/list.do");

		return result;
	}

	@RequestMapping(value = "/listObsoletes", method = RequestMethod.GET)
	public ModelAndView listObsoletes() {
		final ModelAndView result;
		Collection<Application> applicationObsoletes;

		final Company company = this.companyService.findByPrincipal();

		applicationObsoletes = this.applicationService.findAllDeadLinePastByCompany(company.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/listObsoletes");
		result.addObject("applicationObsoletes", applicationObsoletes);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/company/listObsoletes.do");

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.applicationService.existApplication(applicationId);

		if (exist) {

			final Boolean security = this.applicationService.securityCompany(applicationId);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			if (security && this.applicationService.findOne(applicationId).getStatus().equals("SUBMITTED") && this.applicationService.findOne(applicationId).getPosition().getDeadline().after(now)
				&& this.applicationService.findOne(applicationId).getPosition().getCancellation() == null) {

				this.applicationService.accept(applicationId);

				result = new ModelAndView("redirect:/application/company/list.do");

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else
			result = new ModelAndView("misc/notExist");

		result.addObject("banner", banner);

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.applicationService.existApplication(applicationId);

		if (exist) {

			final Boolean security = this.applicationService.securityCompany(applicationId);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			if (security && this.applicationService.findOne(applicationId).getStatus().equals("SUBMITTED") && this.applicationService.findOne(applicationId).getPosition().getDeadline().after(now)
				&& this.applicationService.findOne(applicationId).getPosition().getCancellation() == null) {

				this.applicationService.reject(applicationId);

				result = new ModelAndView("redirect:/application/company/list.do");

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		} else
			result = new ModelAndView("misc/notExist");

		result.addObject("banner", banner);

		return result;
	}

}
