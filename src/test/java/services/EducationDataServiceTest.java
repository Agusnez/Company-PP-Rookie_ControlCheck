
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
import domain.EducationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private EducationDataService	educationDataService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Create education data
	 * 
	 * b) Negative cases:
	 * 2. Degree = null
	 * 3. Degree = blank
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Education Data: 20%
	 */

	@Test
	public void driverCreateEducationData() {
		final Object testingData[][] = {
			{
				"rookie1", "test", "test", 5.0, "1998/06/29", "2000/06/29", null
			},//1. All fine
			{
				"rookie1", null, "test", 5.0, "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//2. Degree = null
			{
				"rookie1", "		", "test", 5.0, "1998/06/29", "2000/06/29", ConstraintViolationException.class
			},//3. Degree = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateEducationData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], this.convertStringToDate((String) testingData[i][4]),
				this.convertStringToDate((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	protected void templateCreateEducationData(final String username, final String degree, final String institution, final Double mark, final Date startDate, final Date endDate, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final EducationData data = this.educationDataService.create();

			data.setDegree(degree);
			data.setInstitution(institution);
			data.setMark(mark);
			data.setStartDate(startDate);
			data.setEndDate(endDate);

			this.educationDataService.save(data);
			this.educationDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Edit education data
	 * 
	 * b) Negative cases:
	 * 2. Mark < 0
	 * 3. Mark > 10
	 * 4. Mark = null
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Education Data: 20%
	 */

	@Test
	public void driverEditEducationData() {
		final Object testingData[][] = {
			{
				"educationData14", "degree1", "institution1", 5.0, "1996/06/29", "1998/06/29", null
			},//1. All fine
			{
				"educationData14", "degree1", "institution1", -9.1, "1996/06/29", "1998/06/29", ConstraintViolationException.class
			},//2. Mark < 0 
			{
				"educationData14", "degree1", "institution1", 12.5, "1996/06/29", "1998/06/29", ConstraintViolationException.class
			},//3. Mark > 10
			{
				"educationData14", "degree1", "institution1", null, "1996/06/29", "1998/06/29", ConstraintViolationException.class
			},//4. Mark = null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditEducationData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], this.convertStringToDate((String) testingData[i][4]),
				this.convertStringToDate((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}

	protected void templateEditEducationData(final String dataBean, final String degree, final String institution, final Double mark, final Date startDate, final Date endDate, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			final EducationData data = this.educationDataService.findOne(super.getEntityId(dataBean));

			data.setDegree(degree);
			data.setInstitution(institution);
			data.setMark(mark);
			data.setStartDate(startDate);
			data.setEndDate(endDate);

			this.educationDataService.save(data);
			this.educationDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Delete education data
	 * 
	 * b) Negative cases:
	 * 2. Not Curriculum
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -delete(): 92.9%
	 * 
	 * d) Data coverage
	 * -Education Data: 0%
	 */

	@Test
	public void driverDeleteEducationData() {
		final Object testingData[][] = {

			{
				"educationData14", null
			},//1. All fine
			{
				"rookie1", IllegalArgumentException.class
			},//2. Not Curriculum

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteEducationData((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateDeleteEducationData(final String dataBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.educationDataService.delete(this.educationDataService.findOne(super.getEntityId(dataBean)));

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
	 * -------Coverage EducationDataService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * EducationDataService = 29,6%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * EducationData = 40%
	 */
}
