
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Position;
import java.util.Arrays;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConfigurationService configurationService;

	// Methods

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Double avgPC = this.administratorService.avgOfPositionsPerCompany();
		final Integer minPC = this.administratorService.minOfPositionsPerCompany();
		final Integer maxPC = this.administratorService.maxOfPositionsPerCompany();
		final Double stdPC = this.administratorService.stdOfPositionsPerCompany();

		final Double avgAH = this.administratorService.avgOfApplicationsPerRookie();
		final Integer minAH = this.administratorService.minOfApplicationsPerRookie();
		final Integer maxAH = this.administratorService.maxOfApplicationsPerRookie();
		final Double stdAH = this.administratorService.stdOfApplicationsPerRookie();

		final List<String> topC = this.administratorService.topCompaniesWithMorePositions();

		final List<String> topH = this.administratorService.topRookiesWithMoreApplications();

		final Double avgS = this.administratorService.avgSalaries();
		final Integer minS = this.administratorService.minSalary();
		final Integer maxS = this.administratorService.maxSalary();
		final Double stdS = this.administratorService.stdSalaries();

		final Position bP = this.administratorService.findBestPosition();
		final Position wP = this.administratorService.findWorstPosition();

		final Integer minCH = this.administratorService.minNumberOfCurriculaPerRookie();
		final Integer maxCH = this.administratorService.maxNumberOfCurriculaPerRookie();
		final Double avgCH = this.administratorService.avgNumberOfCurriculaPerRookie();
		final Double stdCH = this.administratorService.stdNumberOfCurriculaPerRookie();

		final Double stdRF = this.administratorService.stdNumberOfResultsInFinders();
		final Integer minRF = this.administratorService.minNumberOfResultsInFinders();
		final Integer maxRF = this.administratorService.maxNumberOfResultsInFinders();
		final Double avgRF = this.administratorService.avgNumberOfResultsInFinders();

		final Double ratioEF = this.administratorService.ratioEmptyNotEmptyFinders();

		// ----- Acme-Rookies -----
		final Double avgSP = this.administratorService.avgScorePositions();
		final Double minSP = this.administratorService.minScorePositions();
		final Double maxSP = this.administratorService.maxScorePositions();
		final Double stdSP = this.administratorService.stdScorePositions();

		final Double avgSC = this.administratorService.avgScoreCompany();
		final Double minSC = this.administratorService.minScoreCompany();
		final Double maxSC = this.administratorService.maxScoreCompany();
		final Double stdSC = this.administratorService.stdScoreCompany();

		final List<String> topSC = this.administratorService.topScoreCompany();

		final Double avgTC = this.administratorService.avgSalaryTopCompany();

		final Double minSa = this.administratorService.minItemPerProvider();
		final Double maxSa = this.administratorService.maxItemPerProvider();
		final Double avgSa = this.administratorService.avgItemPerProvider();
		final Double stdSa = this.administratorService.stdItemPerProvider();

		final List<String> topP = this.administratorService.top5Provider();

		final Double avgSPr = this.administratorService.avgSponsorshipPerProvider();
		final Double minSPr = this.administratorService.minSponsorshipPerProvider();
		final Double maxSPr = this.administratorService.maxSponsorshipPerProvider();
		final Double stdSPr = this.administratorService.stdSponsorshipPerProvider();

		final Double avgSPo = this.administratorService.avgSponsorshipPerPosition();
		final Double minSPo = this.administratorService.minSponsorshipPerPosition();
		final Double maxSPo = this.administratorService.maxSponsorshipPerPosition();
		final Double stdSPo = this.administratorService.stdSponsorshipPerPosition();

		final List<String> supP = this.administratorService.superiorProviders();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgPC", avgPC);
		result.addObject("minPC", minPC);
		result.addObject("maxPC", maxPC);
		result.addObject("stdPC", stdPC);
		result.addObject("avgAH", avgAH);
		result.addObject("minAH", minAH);
		result.addObject("maxAH", maxAH);
		result.addObject("stdAH", stdAH);
		result.addObject("topC", topC);
		result.addObject("topH", topH);
		result.addObject("avgS", avgS);
		result.addObject("stdS", stdS);
		result.addObject("minS", minS);
		result.addObject("maxS", maxS);
		result.addObject("bP", bP);
		result.addObject("wP", wP);
		result.addObject("minCH", minCH);
		result.addObject("maxCH", maxCH);
		result.addObject("avgCH", avgCH);
		result.addObject("stdCH", stdCH);
		result.addObject("avgRF", avgRF);
		result.addObject("minRF", minRF);
		result.addObject("maxRF", maxRF);
		result.addObject("stdRF", stdRF);
		result.addObject("ratioEF", ratioEF);
		result.addObject("banner", banner);

		result.addObject("avgSP", avgSP);
		result.addObject("maxSP", maxSP);
		result.addObject("minSP", minSP);
		result.addObject("stdSP", stdSP);
		
		result.addObject("avgSC", avgSC);
		result.addObject("minSC", minSC);
		result.addObject("maxSC", maxSC);
		result.addObject("stdSC", stdSC);
		
		result.addObject("topSC", topSC);
		
		result.addObject("avgTC", avgTC);
		
		result.addObject("minSa", minSa);
		result.addObject("maxSa", maxSa);
		result.addObject("avgSa", avgSa);
		result.addObject("stdSa", stdSa);
		
		result.addObject("topP", topP);
		
		result.addObject("avgSPr", avgSPr);
		result.addObject("minSPr", minSPr);
		result.addObject("maxSPr", maxSPr);
		result.addObject("stdSPr", stdSPr);
		
		result.addObject("avgSPo", avgSPo);
		result.addObject("minSPo", minSPo);
		result.addObject("maxSPo", maxSPo);
		result.addObject("stdSPo", stdSPo);
		
		result.addObject("supP", supP);

		return result;
	}
}
