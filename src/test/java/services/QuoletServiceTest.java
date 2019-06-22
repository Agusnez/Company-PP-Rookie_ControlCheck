
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Quolet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuoletServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private QuoletService	quoletService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.ROOKIES
	 * a) An actor who is authenticated as company must be able to: Create a quolet
	 * 
	 * b) Negative cases:
	 * 2. Body = blank
	 * 
	 * c) Sentence coverage
	 * -create()=100%
	 * -save()=61.2%
	 * 
	 * d) Data coverage
	 * -Quolet: 0%
	 */
	@Test
	public void driverCreateQuolet() {
		final Object testingData[][] = {
			{
				"company1", "application3", "test", "test", "http://test.com", null
			},//1. All fine
			{
				"company1", "application3", "test", "		", "http://test.com", ConstraintViolationException.class
			},//2. Body = blank
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateQuolet((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);

	}

	protected void templateCreateQuolet(final String username, final String applicationBean, final String ticker, final String body, final String picture, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Quolet quolet = this.quoletService.create(super.getEntityId(applicationBean));

			quolet.setTicker(ticker);
			quolet.setBody(body);
			quolet.setFinalMode(false);
			quolet.setPicture(picture);
			quolet.setPublicationMoment(null);

			this.quoletService.save(quolet);
			this.quoletService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage QuoletService
	 * ----TOTAL SENTENCE COVERAGE:
	 * QuoletService = 24.7%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Quolet = 0%
	 */
}
