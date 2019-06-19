
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PositionDataService	positionDataService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Create position data
	 * 
	 * b) Negative cases:
	 * 2. Title = null
	 * 3. Title = blank
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Position Data: 25%
	 */

	@Test
	public void driverCreatePositionData() {
		final Object testingData[][] = {
			{
				"rookie1", "test", "test", "1998/06/29", "2000/06/29", null
			},//1. All fine
			{
				"rookie1", null, "test", "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//2. Title = null
			{
				"rookie1", "		", "test", "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//3. Title = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePositionData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], this.convertStringToDate((String) testingData[i][3]), this.convertStringToDate((String) testingData[i][4]),
				(Class<?>) testingData[i][5]);
	}

	protected void templateCreatePositionData(final String username, final String title, final String description, final Date startDate, final Date endDate, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PositionData data = this.positionDataService.create();

			data.setTitle(title);
			data.setDescription(description);
			data.setStartDate(startDate);
			data.setEndDate(endDate);

			this.positionDataService.save(data);
			this.positionDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Edit position data
	 * 
	 * b) Negative cases:
	 * 2. Title = null
	 * 3. Title = blank
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Position Data: 0%
	 */

	@Test
	public void driverEditPositionData() {
		final Object testingData[][] = {
			{
				"positionData14", "test", "description1", "1998/06/29", "2000/06/29", null
			},//1. All fine
			{
				"positionData14", null, "description1", "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//2. Title = null
			{
				"positionData14", "		", "description1", "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//3. Title = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPositionData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], this.convertStringToDate((String) testingData[i][3]), this.convertStringToDate((String) testingData[i][4]),
				(Class<?>) testingData[i][5]);
	}

	protected void templateEditPositionData(final String dataBean, final String title, final String description, final Date startDate, final Date endDate, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			final PositionData data = this.positionDataService.findOne(super.getEntityId(dataBean));

			data.setTitle(title);
			data.setDescription(description);
			data.setStartDate(startDate);
			data.setEndDate(endDate);

			this.positionDataService.save(data);
			this.positionDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Delete position data
	 * 
	 * b) Negative cases:
	 * 2. Not Curriculum
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -delete(): 92.9%
	 * 
	 * d) Data coverage
	 * -Position Data: 0%
	 */

	@Test
	public void driverDeletePositionData() {
		final Object testingData[][] = {

			{
				"positionData14", null
			},//1. All fine
			{
				"rookie1", IllegalArgumentException.class
			},//2. Not Curriculum

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeletePositionData((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateDeletePositionData(final String dataBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.positionDataService.delete(this.positionDataService.findOne(super.getEntityId(dataBean)));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	//Methods

	protected Date convertStringToDate(final String dateString) {
		Date date = null;

		if (dateString != null) {
			final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			try {
				date = df.parse(dateString);
			} catch (final Exception ex) {
				System.out.println(ex);
			}
		}

		return date;
	}

	/*
	 * -------Coverage PositionDataService
	 * ----TOTAL SENTENCE COVERAGE:
	 * PositionDataService = 31%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * PositionData = 25%
	 */
}
