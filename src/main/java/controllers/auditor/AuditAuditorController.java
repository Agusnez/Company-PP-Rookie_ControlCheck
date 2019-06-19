
package controllers.auditor;

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

import security.Authority;
import services.ActorService;
import services.AuditService;
import services.AuditorService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService			auditService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//PONER URL
	public ModelAndView list() {

		final ModelAndView result;

		final Auditor auditor = this.auditorService.findByPrincipal();

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (auditor.getPositions() == null) {
			result = new ModelAndView("misc/noPosition");
			result.addObject("banner", banner);

		} else {
			final Collection<Audit> audits = this.auditService.findAuditsByAuditorId(auditor.getId());
			final Collection<Audit> auditsCancelled = this.auditService.findAuditsCancelledByAuditorId(auditor.getId());
			result = new ModelAndView("audit/list");
			result.addObject("audits", audits);
			result.addObject("auditsCancelled", auditsCancelled);
			result.addObject("requestURI", "audit/auditor/list.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("autoridad", "auditor");
		}

		return result;

	}
	//Create-----------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//PONER URL
	public ModelAndView create(@RequestParam final int positionId) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Actor actor = this.actorService.findByPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority("AUDITOR");
		if (actor.getUserAccount().getAuthorities().contains(authority)) {
			if (this.auditorService.findByPrincipal().getPositions() == null || this.auditorService.findByPrincipal().getPositions().isEmpty()
				|| (this.positionService.findOne(positionId) != null && !this.auditorService.findByPrincipal().getPositions().contains(this.positionService.findOne(positionId)))) {
				result = new ModelAndView("misc/noPosition");
				result.addObject("banner", banner);
			} else if (this.positionService.findOne(positionId) == null || this.positionService.findOne(positionId).getCancellation() != null) {
				result = new ModelAndView("misc/notExist");
				result.addObject("banner", banner);
			} else {
				final Position position = this.positionService.findOne(positionId);
				final Audit audit = this.auditService.create(position);
				result = this.createEditModelAndView(audit, null);
			}

		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		}
		return result;

	}
	//Editar-------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (this.auditService.findOne(auditId) == null || this.auditService.findOne(auditId).getPosition().getCancellation() != null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else if (this.auditService.findOne(auditId).getFinalMode() == true) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		} else {
			final Audit auditFind = this.auditService.findOne(auditId);
			security = this.auditService.auditAuditorSecurity(auditId);

			if (security && auditFind.getAuditor().getPositions().contains(auditFind.getPosition()))
				result = this.createEditModelAndView(auditFind, null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "audit") Audit audit, final BindingResult binding) {
		ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		if ((audit.getId() != 0 && this.auditService.findOne(audit.getId()) == null) || audit.getPosition() == null || audit.getPosition().getFinalMode() == false
			|| (audit.getId() != 0 && audit.getPosition().getId() != this.auditService.findOne(audit.getId()).getPosition().getId()) || (audit.getId() != 0 && audit.getPosition().getCancellation() != null)) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			audit = this.auditService.reconstruct(audit, audit.getPosition(), binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(audit, null);
			else
				try {
					this.auditService.save(audit);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(audit, "problem.commit.error");

				}
		}
		return result;
	}
	//Delete--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Audit audit, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();
		if (this.auditService.findOne(audit.getId()) == null || this.auditService.findOne(audit.getId()).getPosition().getCancellation() != null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			audit = this.auditService.findOne(audit.getId());
			final Auditor auditor = this.auditorService.findByPrincipal();

			if (audit.getAuditor().getId() == auditor.getId() && audit.getFinalMode() == false && auditor.getPositions() != null && !auditor.getPositions().isEmpty() && this.positionService.findOne(audit.getPosition().getId()) != null)
				try {
					this.auditService.delete(audit);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(audit, "problem.commit.error");
				}
			else
				result = new ModelAndView("redirect:/welcome/index.do");

		}
		return result;
	}

	//Display------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (this.auditorService.findByPrincipal().getPositions() == null || this.auditorService.findByPrincipal().getPositions().isEmpty()) {
			result = new ModelAndView("misc/noPosition");
			result.addObject("banner", banner);

		} else if (this.auditService.findOne(auditId) == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			security = this.auditService.auditAuditorSecurity(auditId);

			if (security) {
				final Audit auditFind = this.auditService.findOne(auditId);
				result = new ModelAndView("audit/display");
				result.addObject("audit", auditFind);
				result.addObject("banner", banner);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	//Ancillary methods---------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;
	}
}
