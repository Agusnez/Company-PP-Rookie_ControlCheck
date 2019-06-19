
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.Rookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private FinderService	finderService;

	@Autowired
	private RookieService	rookieService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 17.2: An actor who is authenticated as a rookie must be able to:
	 * Manage his or her finder, which involves updating the search criteria, listing its contents,
	 * and clearing it.
	 * 
	 * b) Negative cases:
	 * 2. The number of the positions finded is wrong
	 * 
	 * c) Sentence coverage
	 * -save(): 87,3%
	 * -findFinderByRookie(): 100%
	 * d) Data coverage
	 */
	@Test
	public void driverFinder() {
		final Object testingData[][] = {
			{
				"PC", "rookie1", 0, null
			},//1. All fine filter
			{
				"PC", "rookie1", 1, IllegalArgumentException.class
			},//2. The number of the positions finded is wrong

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFinder((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateFinder(final String keyword, final String username, final Integer results, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Rookie rookie = this.rookieService.findByPrincipal();

			final Finder finder = this.finderService.findFinderByRookie(rookie.getId());

			finder.setKeyWord(keyword);

			final Finder saved = this.finderService.save(finder);

			Assert.isTrue(saved.getPositions().size() == results);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();
		super.checkExceptions(expected, caught);
	}

	/*
	 * -------Coverage FinderService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * FinderService = 42,6%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Finder = 0%
	 */

}
