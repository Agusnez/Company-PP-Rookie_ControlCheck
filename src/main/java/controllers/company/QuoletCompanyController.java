
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/quolet/company")
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
			result = new ModelAndView("quolet/display");
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
	public ModelAndView list(@RequestParam final int applicationId) {
		final ModelAndView result;
		Collection<Quolet> quolets;

		quolets = this.quoletService.quoletsPerApplicationId(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean security = this.applicationService.securityCompany(applicationId);

		if (security) {
			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("banner", banner);
			result.addObject("autoridad", "company");
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("applicationId", applicationId);
			result.addObject("requestURI", "quolet/company/list.do");
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView create(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Quolet quolet;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean security = this.applicationService.securityCompany(applicationId);

		if (this.applicationService.existApplication(applicationId)) {

			if (security) {
				quolet = this.quoletService.create(applicationId);
				result = this.createEditModelAndView(quolet);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView edit(@RequestParam final int quoletId) {
		final ModelAndView result;
		final Quolet quolet;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean security = this.quoletService.quoletSecurityCompany(quoletId);

		if (this.quoletService.existQuolet(quoletId)) {

			quolet = this.quoletService.findOne(quoletId);
			Assert.notNull(quolet);
			if (!quolet.getFinalMode() && security)
				result = this.createEditModelAndView(quolet);
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView save(@ModelAttribute(value = "quolet") Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (this.quoletService.existQuoletPost(quolet)) {

			quolet = this.quoletService.reconstruct(quolet, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(quolet);
			else
				try {
					this.quoletService.save(quolet);
					result = new ModelAndView("redirect:list.do?applicationId=" + quolet.getApplication().getId());
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(quolet, "quolet.commit.error");
				}
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public ModelAndView delete(final Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (quolet != null && this.quoletService.existQuolet(quolet.getId())) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else
			try {
				this.quoletService.delete(quolet);
				result = new ModelAndView("redirect:list.do?applicationId=" + quolet.getApplication().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(quolet, "quolet.commit.error");
			}

		return result;
	}

	// Ancillary methods
	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	protected ModelAndView createEditModelAndView(final Quolet quolet) {
		ModelAndView result;

		result = this.createEditModelAndView(quolet, null);

		return result;
	}

	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	protected ModelAndView createEditModelAndView(final Quolet quolet, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Integer applicationId = quolet.getApplication().getId();

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("banner", banner);
		result.addObject("autoridad", "company");
		result.addObject("applicationId", applicationId);
		result.addObject("messageError", messageCode);

		return result;
	}

}
