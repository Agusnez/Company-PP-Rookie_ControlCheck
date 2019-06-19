
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)Manage system's configuration
	 * b) Negative cases:
	 * 2. Finder time < 1
	 * 3. Finder time > 100
	 * 
	 * c) Sentence coverage
	 * -findConfiguration(): 100%
	 * -save(): 100%
	 * d) Data coverage
	 * -Company: 0%
	 */
	@Test
	public void EditConfigurationTest() {
		final Object testingData[][] = {
			{
				"admin", "http://example.com", "+34", "2", "40", "example", "example", null
			},//1. All fine
			{
				"admin", "http://example.com", "+34", "-1", "40", "example", "example", ConstraintViolationException.class
			},//2. Finder time < 1
			{
				"admin", "http://example.com", "+34", "102", "40", "example", "example", ConstraintViolationException.class
			},//3. Finder time > 100

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditConfigurationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], Integer.valueOf((String) testingData[i][3]), Integer.valueOf((String) testingData[i][4]), (String) testingData[i][5],
				(String) testingData[i][6], (Class<?>) testingData[i][7]);
	}

	protected void EditConfigurationTemplate(final String username, final String banner, final String countryCode, final Integer finderTime, final Integer finderResult, final String welcomeMessage, final String welcomeMessageEs, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final Configuration config = this.configurationService.findConfiguration();

			config.setBanner(banner);
			config.setCountryCode(countryCode);
			config.setFinderResult(finderResult);
			config.setFinderTime(finderTime);
			config.setWelcomeMessage(welcomeMessage);
			config.setWelcomeMessageEs(welcomeMessageEs);

			this.configurationService.save(config);
			this.configurationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		this.checkExceptions(expected, caught);
	}

	/*
	 * -------Coverage ConfigurationService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * ConfigurationService = 22,6%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Configuration = 0%
	 */
}
