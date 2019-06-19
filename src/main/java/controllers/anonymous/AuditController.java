
package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	private AuditService			auditService;

	@Autowired
	private ConfigurationService	configurationService;


	//List ------------------------------------------------------------------------------
	@RequestMapping(value = "/listByPosition", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int positionId) {

		final ModelAndView result;
		final Collection<Audit> audits;

		audits = this.auditService.findFinalAuditsByPositionId(positionId);

		final String banner = this.configurationService.findConfiguration().getBanner();
		final String uri = "audit/listByPosition.do?positionId=" + positionId;

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", uri);
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "");

		return result;

	}
}
