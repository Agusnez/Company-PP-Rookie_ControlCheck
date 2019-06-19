
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.Rookie;
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private RookieService		rookieService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Create curriculum
	 * 
	 * b) Negative cases:
	 * 2. Invalid authority
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Curriculum: 0%
	 */

	@Test
	public void driverCreateCurriculum() {
		final Object testingData[][] = {
			{
				"rookie1", null
			},//1. All fine
			{
				"company2", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateHistory((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void templateCreateHistory(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final Rookie rookie = this.rookieService.findOne(super.getEntityId(username));

			final Curriculum c = this.curriculumService.create();

			final PersonalData pd = this.personalDataService.create();
			pd.setFullName("example");
			pd.setStatement("example");
			pd.setPhone("+34 999888777666");
			pd.setLinkLinkedInProfile("http://example.com");
			pd.setLinkGitHubProfile("http://example.com");
			this.personalDataService.save(pd);
			this.personalDataService.flush();

			c.setTicker("180918-F4T78H");
			c.setNoCopy(true);
			c.setRookie(rookie);
			c.setPersonalData(pd);

			this.curriculumService.save(c);
			this.curriculumService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: List curriculum
	 * 
	 * b) Negative cases:
	 * 2. Incorrect result
	 * 
	 * c) Sentence coverage
	 * -findAllByRookieId(): 100%
	 * 
	 * d) Data coverage
	 * -Curriculum: 0%
	 */

	@Test
	public void driverListCurriculum() {
		final Object testingData[][] = {

			{
				"rookie1", 2, null
			},//1. All fine
			{
				"rookie1", 1651, IllegalArgumentException.class
			},//2. Incorrect result

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListCurriculum((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void templateListCurriculum(final String actor, final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Rookie rookie = this.rookieService.findOne(super.getEntityId(actor));
			final Integer result = this.curriculumService.findAllByRookieId(rookie.getId()).size();
			Assert.isTrue(expectedInt == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Display curriculum
	 * 
	 * b) Negative cases:
	 * 2. Unexpected position datas size
	 * 3. Unexpected education datas size
	 * 4. Unexpected miscellaneous datas size
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * 
	 * d) Data coverage
	 * -Curriculum: 0%
	 */

	@Test
	public void driverDisplayCurriculum() {
		final Object testingData[][] = {

			{
				"curriculum14", 1, 1, 1, null
			},//1. All fine
			{
				"curriculum1", 10000, 1, 1, IllegalArgumentException.class
			},//2. Unexpected position datas size
			{
				"curriculum1", 1, 199999, 1, IllegalArgumentException.class
			},//3. Unexpected education datas size
			{
				"curriculum1", 1, 1, 145678, IllegalArgumentException.class
			},//4. Unexpected miscellaneous datas size

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDisplayCurriculum((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	protected void templateDisplayCurriculum(final String curriculumBean, final Integer expectedPositionInt, final Integer expectedEducationInt, final Integer expectedMiscellaneousInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Curriculum c = this.curriculumService.findOne(super.getEntityId(curriculumBean));

			Assert.isTrue(expectedPositionInt == c.getPositionDatas().size());
			Assert.isTrue(expectedEducationInt == c.getEducationDatas().size());
			Assert.isTrue(expectedMiscellaneousInt == c.getMiscellaneousDatas().size());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an rookie must be able to: Manage his or her curricula: Delete curriculum
	 * 
	 * b) Negative cases:
	 * 2. Not Curriculum
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -delete(): 100%
	 * 
	 * d) Data coverage
	 * -Curriculum: 0%
	 */

	@Test
	public void driverDeleteCurriculum() {
		final Object testingData[][] = {

			{
				"curriculum1", null
			},//1. All fine
			{
				"rookie1", IllegalArgumentException.class
			},//2. Not Curriculum

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteCurriculum((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateDeleteCurriculum(final String curriculumBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.curriculumService.delete(this.curriculumService.findOne(super.getEntityId(curriculumBean)));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage CurriculumService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * CurriculumService = 12,7%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Curriculum = 0%
	 */
}
