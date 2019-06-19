
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Create miscellaneous data
	 * 
	 * b) Negative cases:
	 * 2. Text = null
	 * 3. Text = blank
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Miscellaneous Data: 50%
	 */

	@Test
	public void driverCreateMiscellanousData() {
		final Object testingData[][] = {
			{
				"rookie1", "test", "http://test.com", null
			},//1. All fine
			{
				"rookie1", null, "http://test.com", ConstraintViolationException.class
			},//2. Text = null
			{
				"rookie1", "		", "http://test.com", ConstraintViolationException.class
			},//3. Text = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateMiscellanousData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateCreateMiscellanousData(final String username, final String text, final String attachment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final MiscellaneousData data = this.miscellaneousDataService.create();
			data.setText(text);
			final Collection<String> attachments = new HashSet<>();
			attachments.add(attachment);
			data.setAttachments(attachments);

			this.miscellaneousDataService.save(data);
			this.miscellaneousDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Edit miscellaneous data
	 * 
	 * b) Negative cases:
	 * 2. Text = null
	 * 3. Text = blank
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Miscellaneous Data: 0%
	 */

	@Test
	public void driverEditMiscellanousData() {
		final Object testingData[][] = {
			{
				"miscellaneousData14", "test", null
			},//1. All fine
			{
				"miscellaneousData14", "		", ConstraintViolationException.class
			},//1. All fine
			{
				"miscellaneousData14", null, ConstraintViolationException.class
			},//1. All fine
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditMiscellanousData((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateEditMiscellanousData(final String dataBean, final String text, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			final MiscellaneousData data = this.miscellaneousDataService.findOne(super.getEntityId(dataBean));
			data.setText(text);

			this.miscellaneousDataService.save(data);
			this.miscellaneousDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Delete miscellaneous data
	 * 
	 * b) Negative cases:
	 * 2. Not Curriculum
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -delete(): 92.9%
	 * 
	 * d) Data coverage
	 * -Miscellaneous Data: 0%
	 */

	@Test
	public void driverDeleteMiscellanousData() {
		final Object testingData[][] = {

			{
				"miscellaneousData14", null
			},//1. All fine
			{
				"rookie1", IllegalArgumentException.class
			},//2. Not Curriculum

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMiscellanousData((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateDeleteMiscellanousData(final String dataBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.miscellaneousDataService.delete(this.miscellaneousDataService.findOne(super.getEntityId(dataBean)));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage MiscellaneousDataService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * MiscellaneousDataService = 34,4%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * MiscellaneousData = 50%
	 */
}
