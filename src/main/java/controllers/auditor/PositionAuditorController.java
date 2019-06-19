
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/position/auditor")
public class PositionAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private PositionService			positionService;


	//Lista de posiciones que puede asignarse un auditor
	@RequestMapping(value = "/listPosition", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Position> positions;
		final Auditor auditor = this.auditorService.findByPrincipal();

		positions = this.positionService.findPositionsFinalModeTrueWithoutDeadline();
		final Collection<Position> myPositions = auditor.getPositions();

		final Collection<Position> positionsCancelled = this.positionService.findPositionsCancelled();

		positions.removeAll(myPositions);
		positions.removeAll(positionsCancelled);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("banner", banner);
		result.addObject("requestURI", "position/auditor/listPosition.do");
		result.addObject("pagesize", 5);
		result.addObject("AmILogged", true);
		result.addObject("AmInFinder", false);
		result.addObject("AmInCompanyController", false);

		return result;
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView select(@RequestParam final int positionId) {
		ModelAndView result;

		final Auditor auditor = this.auditorService.findByPrincipal();
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Collection<Position> positions = auditor.getPositions();

		if (this.positionService.findOne(positionId) == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else if (this.positionService.findOne(positionId).getFinalMode() == false || positions.contains(this.positionService.findOne(positionId)))
			result = new ModelAndView("redirect:listPosition.do");
		else
			try {
				final Position position = this.positionService.findOne(positionId);
				positions.add(position);
				auditor.setPositions(positions);
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:listMyPosition.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("misc/error");

				result.addObject("banner", banner);
			}

		return result;

	}

	//Lista de posiciones a las que puede auditar un auditor
	@RequestMapping(value = "/listMyPosition", method = RequestMethod.GET)
	public ModelAndView listMyPosition() {
		final ModelAndView result;
		Collection<Position> positions;
		final Auditor auditor = this.auditorService.findByPrincipal();

		positions = this.positionService.findPositionsNotCancelledByAuditorId(auditor.getId());

		final Collection<Position> myPositionsCancelled = this.positionService.findPositionsCancelledByAuditorId(auditor.getId());

		positions.removeAll(myPositionsCancelled);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("myPositionsCancelled", myPositionsCancelled);
		result.addObject("banner", banner);
		result.addObject("requestURI", "position/auditor/listMyPosition.do");
		result.addObject("pagesize", 5);
		result.addObject("AmILogged", true);
		result.addObject("AmInFinder", false);

		return result;
	}
}
