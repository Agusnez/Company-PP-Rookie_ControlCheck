
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AuditorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Auditor;
import forms.RegisterAdministratorForm;
import forms.RegisterAuditorForm;

@Controller
@RequestMapping("/administrator")
public class RegisterAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Methods

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final RegisterAdministratorForm administrator;

		administrator = new RegisterAdministratorForm();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administrator") final RegisterAdministratorForm form, final BindingResult binding) {
		ModelAndView result;

		final Administrator adminReconstruct = this.administratorService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(form);
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndView(form, "administrator.commit.error");
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndView(form, "administrator.commit.error.username");
		else
			try {
				this.administratorService.save(adminReconstruct);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "actor.commit.error");
			}
		return result;
	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final RegisterAdministratorForm administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RegisterAdministratorForm administrator, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/signUpAdministrator");
		result.addObject("administrator", administrator);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
	
	//------AUDITOR--------
	
	@RequestMapping(value = "/createAuditor", method = RequestMethod.GET)
	public ModelAndView createAuditor() {
		final ModelAndView result;
		final RegisterAuditorForm auditor;

		auditor = new RegisterAuditorForm();
		result = this.createEditModelAndViewAuditor(auditor);

		return result;
	}

	@RequestMapping(value = "/editAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@ModelAttribute("auditor") final RegisterAuditorForm form, final BindingResult binding) {
		ModelAndView result;

		final Auditor auditorReconstruct = this.auditorService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewAuditor(form);
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndViewAuditor(form, "auditor.commit.error");
		else if (!form.checkPassword() || !form.getCheckbox())
			result = this.createEditModelAndViewAuditor(form, "auditor.commit.error.username");
		else
			try {
				this.auditorService.save(auditorReconstruct);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewAuditor(form, "actor.commit.error");
			}
		return result;
	}
	
	protected ModelAndView createEditModelAndViewAuditor(final RegisterAuditorForm auditor) {
		ModelAndView result;

		result = this.createEditModelAndViewAuditor(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAuditor(final RegisterAuditorForm auditor, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpAuditor");
		result.addObject("auditor", auditor);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
}
