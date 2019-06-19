
package controllers.rookie;

import java.util.Date;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.FinderService;
import services.RookieService;
import controllers.AbstractController;
import domain.Actor;
import domain.Finder;

@Controller
@RequestMapping("/finder/rookie")
public class FinderRookieController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	RookieService					rookieService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView showFinder() {
		ModelAndView result;
		final Finder finder;

		final Actor actor = this.actorService.findByPrincipal();
		finder = this.finderService.findFinderByRookie(actor.getId());

		final Date currentTime = new Date(System.currentTimeMillis() - 1000);
		final Interval interval = new Interval(finder.getLastUpdate().getTime(), currentTime.getTime());

		final Integer timeOut = this.configurationService.findConfiguration().getFinderTime();
		final Integer pagesize = this.configurationService.findConfiguration().getFinderResult();

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (interval.toDuration().getStandardHours() > timeOut)
			this.finderService.deletePositions(finder);

		result = new ModelAndView("position/list");
		result.addObject("positions", finder.getPositions());
		result.addObject("finder", finder);
		result.addObject("requestURI", "finder/rookie/find.do");
		result.addObject("requestAction", "finder/rookie/find.do");
		result.addObject("banner", banner);
		result.addObject("pagesize", pagesize);
		result.addObject("AmILogged", true);
		result.addObject("AmInFinder", true);

		return result;

	}
	@RequestMapping(value = "/find", method = RequestMethod.POST, params = "find")
	public ModelAndView editFinder(Finder finder, final BindingResult binding) {
		ModelAndView result;

		final Finder finderSearched = this.finderService.findOne(finder.getId());
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (finderSearched != null) {
			finder = this.finderService.reconstruct(finder, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(finder, null);
			else
				try {
					this.finderService.save(finder);
					result = new ModelAndView("redirect:find.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(finder, "finder.commit.error");

				}
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST, params = "clear")
	public ModelAndView clearFinder(Finder finder, final BindingResult binding) {
		ModelAndView result;

		final Finder finderSearched = this.finderService.findOne(finder.getId());
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (finderSearched != null) {
			finder = this.finderService.reconstruct(finder, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(finder, null);
			else
				try {
					this.finderService.clear(finder);
					result = new ModelAndView("redirect:find.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(finder, "finder.commit.error");

				}
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("position/list");

		final String banner = this.configurationService.findConfiguration().getBanner();

		result.addObject("finder", finder);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("AmILogged", true);
		result.addObject("AmInFinder", true);

		return result;
	}
}
