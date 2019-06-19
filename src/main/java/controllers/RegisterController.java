
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Credentials;
import services.CompanyService;
import services.ConfigurationService;
import services.ProviderService;
import services.RookieService;
import domain.Company;
import domain.Provider;
import domain.Rookie;
import forms.RegisterCompanyForm;
import forms.RegisterProviderForm;
import forms.RegisterRookieForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private RookieService			rookieService;
	
	@Autowired
	private ProviderService			providerService;
	
	@Autowired
	private ConfigurationService	configurationService;


	//Registrar Company
	@RequestMapping(value = "/createCompany", method = RequestMethod.GET)
	public ModelAndView createCompany() {
		final ModelAndView result;
		final RegisterCompanyForm company = new RegisterCompanyForm();

		result = this.createEditModelAndViewCompany(company);

		return result;
	}

	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCompany(@ModelAttribute("company") final RegisterCompanyForm form, final BindingResult binding) {
		ModelAndView result;
		final Company company;

		company = this.companyService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewCompany(form);
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.companyService.save(company);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(company.getUserAccount().getUsername());
				credentials.setPassword(company.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCompany(form, "company.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewCompany(final RegisterCompanyForm company) {
		ModelAndView result;

		result = this.createEditModelAndViewCompany(company, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCompany(final RegisterCompanyForm company, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpCompany");
		result.addObject("company", company);
		result.addObject("banner", banner);
		result.addObject("laguageURI", "register/createCompany.do");
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//Registrar Rookie

	@RequestMapping(value = "/createRookie", method = RequestMethod.GET)
	public ModelAndView createRookie() {
		final ModelAndView result;
		final RegisterRookieForm rookie = new RegisterRookieForm();

		result = this.createEditModelAndViewRookie(rookie);

		return result;
	}

	@RequestMapping(value = "/saveRookie", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRookie(@ModelAttribute("rookie") final RegisterRookieForm form, final BindingResult binding) {
		ModelAndView result;
		final Rookie rookie;

		rookie = this.rookieService.reconstruct(form, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewRookie(form);
		else
			try {
				Assert.isTrue(form.getCheckbox());
				Assert.isTrue(form.checkPassword());
				this.rookieService.save(rookie);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(rookie.getUserAccount().getUsername());
				credentials.setPassword(rookie.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewRookie(form, "rookie.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndViewRookie(final RegisterRookieForm rookie) {
		ModelAndView result;

		result = this.createEditModelAndViewRookie(rookie, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRookie(final RegisterRookieForm rookie, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpRookie");
		result.addObject("rookie", rookie);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
	
	
	//Registrar Provider

		@RequestMapping(value = "/createProvider", method = RequestMethod.GET)
		public ModelAndView createProvider() {
			final ModelAndView result;
			final RegisterProviderForm provider = new RegisterProviderForm();

			result = this.createEditModelAndViewProvider(provider);

			return result;
		}

		@RequestMapping(value = "/saveProvider", method = RequestMethod.POST, params = "save")
		public ModelAndView saveProvider(@ModelAttribute("provider") final RegisterProviderForm form, final BindingResult binding) {
			ModelAndView result;
			final Provider provider;

			provider = this.providerService.reconstruct(form, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewProvider(form);
			else
				try {
					Assert.isTrue(form.getCheckbox());
					Assert.isTrue(form.checkPassword());
					this.providerService.save(provider);
					final Credentials credentials = new Credentials();
					credentials.setJ_username(provider.getUserAccount().getUsername());
					credentials.setPassword(provider.getUserAccount().getPassword());
					result = new ModelAndView("redirect:/security/login.do");
					result.addObject("credentials", credentials);
				} catch (final Throwable oops) {
					result = this.createEditModelAndViewProvider(form, "provider.commit.error");
				}
			return result;
		}
		protected ModelAndView createEditModelAndViewProvider(final RegisterProviderForm provider) {
			ModelAndView result;

			result = this.createEditModelAndViewProvider(provider, null);

			return result;
		}

		protected ModelAndView createEditModelAndViewProvider(final RegisterProviderForm provider, final String messageCode) {
			ModelAndView result;

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("security/signUpProvider");
			result.addObject("provider", provider);
			result.addObject("banner", banner);
			result.addObject("messageError", messageCode);
			final String countryCode = this.configurationService.findConfiguration().getCountryCode();
			result.addObject("defaultCountry", countryCode);

			return result;
		}

}
