
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CompanyService;
import services.ConfigurationService;
import services.ProviderService;
import services.RookieService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Company;
import domain.Provider;
import domain.Rookie;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services-----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();

		Collection<Rookie> rookies;
		Collection<Company> companies;
		Collection<Administrator> administrators;
		Collection<Provider> providers;
		Collection<Auditor> auditors;

		rookies = this.rookieService.findAll();
		companies = this.companyService.findAll();
		administrators = this.administratorService.findAll();
		providers = this.providerService.findAll();
		auditors = this.auditorService.findAll();

		if (rookies.contains(actor))
			rookies.remove(actor);
		else if (companies.contains(actor))
			companies.remove(actor);
		else if (administrators.contains(actor))
			administrators.remove(actor);
		else if (providers.contains(actor))
			providers.remove(actor);
		else if (auditors.contains(actor))
			auditors.remove(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/list");
		result.addObject("rookies", rookies);
		result.addObject("companies", companies);
		result.addObject("administrators", administrators);
		result.addObject("providers", providers);
		result.addObject("auditors", auditors);
		result.addObject("banner", banner);

		result.addObject("requestURI", "actor/list.do");

		return result;

	}

}
