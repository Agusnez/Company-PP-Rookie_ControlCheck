/*
 * ProfileController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.Credentials;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.ConfigurationService;
import services.ProviderService;
import services.RookieService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.Provider;
import domain.Rookie;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/displayPrincipal", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(this.actorService.findByPrincipal().getId());
		Assert.notNull(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean company = this.actorService.isCompany(actor.getId());

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/displayPrincipal.do");
		result.addObject("admin", false);

		if (company)
			result.addObject("isCompany", true);
		else
			result.addObject("isCompany", false);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Actor actor;

		final Actor principal = this.actorService.findByPrincipal();
		actor = this.actorService.findOne(principal.getId());
		Assert.isTrue(actor.equals(principal));

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.COMPANY);

		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.ROOKIE);

		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.ADMIN);

		final Authority authority4 = new Authority();
		authority4.setAuthority(Authority.AUDITOR);

		final Authority authority5 = new Authority();
		authority5.setAuthority(Authority.PROVIDER);

		String auth = null;
		String action = null;
		if (actor.getUserAccount().getAuthorities().contains(authority1)) {
			auth = "company";
			action = "editCompany.do";

		} else if (actor.getUserAccount().getAuthorities().contains(authority2)) {
			auth = "rookie";
			action = "editRookie.do";
		} else if (actor.getUserAccount().getAuthorities().contains(authority3)) {
			auth = "administrator";
			action = "editAdministrator.do";
		} else if (actor.getUserAccount().getAuthorities().contains(authority4)) {
			auth = "auditor";
			action = "editAuditor.do";
		} else if (actor.getUserAccount().getAuthorities().contains(authority5)) {
			auth = "provider";
			action = "editProvider.do";
		}

		final String banner = this.configurationService.findConfiguration().getBanner();
		final String defaultCountry = this.configurationService.findConfiguration().getCountryCode();
		result = new ModelAndView("actor/edit");
		result.addObject("actionURI", action);
		result.addObject(auth, actor);
		result.addObject("authority", auth);
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/edit.do");
		result.addObject("defaultCountry", defaultCountry);

		return result;
	}

	//---------------------COMPANY---------------------
	@RequestMapping(value = "/editCompany", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCompany(@ModelAttribute("company") final Company company, final BindingResult binding) {
		ModelAndView result;

		final Company companyReconstruct = this.companyService.reconstruct(company, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewCompany(companyReconstruct);
		else
			try {
				this.companyService.save(companyReconstruct);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(companyReconstruct.getUserAccount().getUsername());
				credentials.setPassword(companyReconstruct.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCompany(companyReconstruct, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewCompany(final Company company) {
		ModelAndView result;

		result = this.createEditModelAndViewCompany(company, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCompany(final Company company, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");

		result.addObject("company", company);
		result.addObject("authority", "company");
		result.addObject("actionURI", "editCompany.do");
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/edit.do");
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//--------------------------ROOKIE------------------------------

	@RequestMapping(value = "/editRookie", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRookie(@ModelAttribute("rookie") final Rookie rookie, final BindingResult binding) {
		ModelAndView result;

		final Rookie rookieReconstruct = this.rookieService.reconstruct(rookie, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewRookie(rookieReconstruct);
		else
			try {
				this.rookieService.save(rookieReconstruct);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(rookieReconstruct.getUserAccount().getUsername());
				credentials.setPassword(rookieReconstruct.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewRookie(rookieReconstruct, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewRookie(final Rookie rookie) {
		ModelAndView result;

		result = this.createEditModelAndViewRookie(rookie, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRookie(final Rookie rookie, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");

		result.addObject("rookie", rookie);
		result.addObject("authority", "rookie");
		result.addObject("actionURI", "editRookie.do");
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/edit.do");
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//--------------------------ADMINISTRATOR------------------------------

	@RequestMapping(value = "/editAdministrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@ModelAttribute("administrator") final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		final Administrator adminReconstruct = this.administratorService.reconstruct(admin, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewAdmin(adminReconstruct);
		else
			try {
				this.administratorService.save(adminReconstruct);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(adminReconstruct.getUserAccount().getUsername());
				credentials.setPassword(adminReconstruct.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewAdmin(adminReconstruct, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewAdmin(final Administrator admin) {
		ModelAndView result;

		result = this.createEditModelAndViewAdmin(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAdmin(final Administrator admin, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");

		result.addObject("administrator", admin);
		result.addObject("authority", "administrator");
		result.addObject("actionURI", "editAdministrator.do");
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/edit.do");
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//--------------------------Auditor------------------------------

	@RequestMapping(value = "/editAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@ModelAttribute("auditor") final Auditor auditor, final BindingResult binding) {
		ModelAndView result;

		final Auditor auditorReconstruct = this.auditorService.reconstruct(auditor, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewAuditor(auditorReconstruct);
		else
			try {
				this.auditorService.save(auditorReconstruct);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(auditorReconstruct.getUserAccount().getUsername());
				credentials.setPassword(auditorReconstruct.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewAuditor(auditorReconstruct, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewAuditor(final Auditor auditor) {
		ModelAndView result;

		result = this.createEditModelAndViewAuditor(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAuditor(final Auditor auditor, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");

		result.addObject("auditor", auditor);
		result.addObject("authority", "auditor");
		result.addObject("actionURI", "editAuditor.do");
		result.addObject("banner", banner);
		result.addObject("laguageURI", "profile/edit.do");
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	//--------------------------Provider------------------------------

	@RequestMapping(value = "/editProvider", method = RequestMethod.POST, params = "save")
	public ModelAndView saveProvider(@ModelAttribute("provider") final Provider provider, final BindingResult binding) {
		ModelAndView result;

		final Provider providerReconstruct = this.providerService.reconstruct(provider, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewProvider(providerReconstruct);
		else
			try {
				this.providerService.save(providerReconstruct);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(providerReconstruct.getUserAccount().getUsername());
				credentials.setPassword(providerReconstruct.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewProvider(providerReconstruct, "actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewProvider(final Provider provider) {
		ModelAndView result;

		result = this.createEditModelAndViewProvider(provider, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewProvider(final Provider provider, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");

		result.addObject("provider", provider);
		result.addObject("authority", "provider");
		result.addObject("actionURI", "editProvider.do");
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
