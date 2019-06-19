
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ConfigurationService;
import services.PositionService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.Sponsorship;
import forms.FilterForm;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ConfigurationService	configurationService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Position> positions;
		final Company c;

		c = this.companyService.findByPrincipal();

		positions = this.positionService.findPositionsByCompanyId(c.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/company/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		final FilterForm filterForm = new FilterForm();
		result.addObject("filterForm", filterForm);

		//Esto es por el filter, para que no se muestre siempre en la vista de position/list
		//Que solo se muestre unicamente con el controlador de listar position anonimo
		result.addObject("AmILogged", true);

		//Esto es para reutilizar vista de position/list en el create
		result.addObject("AmInCompanyController", true);

		return result;

	}

	//Create-----------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Position position = this.positionService.create();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("banner", banner);

		return result;

	}

	//Editar-------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Boolean security;

		final Position position = this.positionService.findOne(positionId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position == null || position.getCancellation() != null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			security = this.positionService.positionCompanySecurity(positionId);
			if (security)
				if (position.getFinalMode())
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = this.createEditModelAndView(position, null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "position") Position position, final BindingResult binding) {
		ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position.getId() != 0 && this.positionService.findOne(position.getId()) == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			position = this.positionService.reconstruct(position, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(position, null);
			else
				try {
					this.positionService.save(position);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(position, "position.commit.error");

				}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Position position, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position.getId() != 0 && this.positionService.findOne(position.getId()) == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			position = this.positionService.findOne(position.getId());
			final Boolean security = this.positionService.positionCompanySecurity(position.getId());

			if (security)
				try {
					this.positionService.delete(position);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(position, "position.commit.error");
				}
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {
		ModelAndView result;
		Boolean security;

		final Position position = this.positionService.findOne(positionId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			security = this.positionService.positionCompanySecurity(positionId);
			if (security) {
				if (!position.getFinalMode())
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					try {
						this.positionService.cancel(position);
						result = new ModelAndView("redirect:list.do");
					} catch (final Throwable oops) {
						result = new ModelAndView("redirect:/welcome/index.do");
					}
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	//Display------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;

		final Position position = this.positionService.findOne(positionId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (position == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			final Company login = this.companyService.findByPrincipal();

			if (position.getCompany().equals(login) || position.getFinalMode()) {
				
				result = new ModelAndView("position/display");
				result.addObject("position", position);
				result.addObject("banner", banner);
				//Esto es para reutilizar vista de position/list en el create
				result.addObject("AmInCompanyController", true);
				final Boolean security = this.positionService.positionCompanySecurity(position.getId());
				result.addObject("security", security);

				try {
					final Sponsorship s = this.sponsorshipService.ramdomSponsorship(positionId);

					if (s != null) {
						result.addObject("find", true);
						result.addObject("bannerSponsorship", s.getBanner());
					}

					else
						result.addObject("find", false);
				} catch (final Throwable oops) {
					result.addObject("find", false);
				}

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

			

		}
		return result;
	}

	//Other business methods------------------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;
	}

}
