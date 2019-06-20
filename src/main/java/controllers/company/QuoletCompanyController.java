
package controllers.company;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/quolet/customer")
public class QuoletCompanyController extends AbstractController {

	@Autowired
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	private QuoletService			quoletService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ApplicationService		applicationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView display(@RequestParam final int quoletId) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Quolet quolet = this.quoletService.findOne(quoletId);
		final Integer applicationId = quolet.getApplication().getId();

		final Boolean security = this.applicationService.securityCompany(applicationId);

		if (security) {
			result = new ModelAndView("pusit/display");
			result.addObject("autoridad", "company");
			result.addObject("banner", banner);
			result.addObject("quolet", quolet);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("applicationId", applicationId);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		Collection<Quolet> quolets;

		quolets = this.quoletService.pusitsPerFixUpTaskId(fixUpTaskId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean security = this.pusitService.fixUpTaskSecurity(fixUpTaskId);

		if (security) {
			result = new ModelAndView("pusit/list");
			result.addObject("pusits", pusits);
			result.addObject("banner", banner);
			result.addObject("autoridad", "customer");
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("fixUpTaskId", fixUpTaskId);
			result.addObject("requestURI", "pusit/customer/list.do");
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		final Pusit pusit;

		final Boolean security = this.pusitService.fixUpTaskSecurity(fixUpTaskId);

		if (security) {
			pusit = this.pusitService.create(fixUpTaskId);
			result = this.createEditModelAndView(pusit);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView edit(@RequestParam final int pusitId) {
		final ModelAndView result;
		final Pusit pusit;

		final Boolean security = this.pusitService.pusitSecurity(pusitId);

		pusit = this.pusitService.findOne(pusitId);
		Assert.notNull(pusit);
		if (!pusit.getFinalMode() && security)
			result = this.createEditModelAndView(pusit);
		else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView save(@Valid final Pusit pusit, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(pusit);
		else
			try {
				this.pusitService.save(pusit);
				result = new ModelAndView("redirect:list.do?fixUpTaskId=" + pusit.getFixUpTask().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(pusit, "pusit.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView delete(final Pusit pusit, final BindingResult binding) {
		ModelAndView result;

		try {
			this.pusitService.delete(pusit);
			result = new ModelAndView("redirect:list.do?fixUpTaskId=" + pusit.getFixUpTask().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(pusit, "pusit.commit.error");
		}

		return result;
	}

	// Ancillary methods
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	protected ModelAndView createEditModelAndView(final Pusit pusit) {
		ModelAndView result;

		result = this.createEditModelAndView(pusit, null);

		return result;
	}

	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	protected ModelAndView createEditModelAndView(final Pusit pusit, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Integer fixUpTaskId = pusit.getFixUpTask().getId();

		result = new ModelAndView("pusit/edit");
		result.addObject("pusit", pusit);
		result.addObject("banner", banner);
		result.addObject("autoridad", "customer");
		result.addObject("fixUpTaskId", fixUpTaskId);
		result.addObject("messageError", messageCode);

		return result;
	}

}
