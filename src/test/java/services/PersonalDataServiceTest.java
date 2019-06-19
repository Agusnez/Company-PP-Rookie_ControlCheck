
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PersonalDataService	personalDataService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Create personal data
	 * 
	 * b) Negative cases:
	 * 2. Statement = null
	 * 3. Statement = blank
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Personal Data: 20%
	 */

	@Test
	public void driverCreatePersonalData() {
		final Object testingData[][] = {
			{
				"rookie1", "test", "test", "954920633", "http://github.com", "http://linkedIn.com", null
			},//1. All fine
			{
				"rookie1", "test", null, "954920633", "http://github.com", "http://linkedIn.com", ConstraintViolationException.class
			},//2. Statement = null
			{
				"rookie1", "test", "		", "954920633", "http://github.com", "http://linkedIn.com", ConstraintViolationException.class
			},//3. Statement = blank
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePersonalData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void templateCreatePersonalData(final String username, final String fullName, final String statement, final String phone, final String linkGitHubProfile, final String linkLinkedInProfile, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PersonalData data = this.personalDataService.create();

			data.setFullName(fullName);
			data.setStatement(statement);
			data.setPhone(phone);
			data.setLinkGitHubProfile(linkGitHubProfile);
			data.setLinkLinkedInProfile(linkLinkedInProfile);

			this.personalDataService.save(data);
			this.personalDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level B) Requirement 17.1: An actor who is authenticated as an hacker must be able to: Manage his or her curricula: Edit personal data
	 * 
	 * b) Negative cases:
	 * 2. LinkGitHubProfile = blank
	 * 3. LinkGitHubProfile = null
	 * 4. LinkGitHubProfile = invalid url
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save(): 100%
	 * 
	 * d) Data coverage
	 * -Personal Data: 0%
	 */

	@Test
	public void driverEditPersonalData() {
		final Object testingData[][] = {
			{
				"personalData1", "Hacker1 hacker1surname", "statement1", "954909090", "http://www.gitHub.com/albacorare", "http://www.linkedIn.com/", null
			},//1. All fine
			{
				"personalData1", "Hacker1 hacker1surname", "statement1", "954909090", "		", "http://www.linkedIn.com/", ConstraintViolationException.class
			},//2. LinkGitHubProfile = blank
			{
				"personalData1", "Hacker1 hacker1surname", "statement1", "954909090", null, "http://www.linkedIn.com/", ConstraintViolationException.class
			},//3. LinkGitHubProfile = null
			{
				"personalData1", "Hacker1 hacker1surname", "statement1", "954909090", "hppp:", "http://www.linkedIn.com/", ConstraintViolationException.class
			},//4. LinkGitHubProfile = invalid url
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPersonalData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void templateEditPersonalData(final String dataBean, final String fullName, final String statement, final String phone, final String linkGitHubProfile, final String linkLinkedInProfile, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			final PersonalData data = this.personalDataService.findOne(super.getEntityId(dataBean));

			data.setFullName(fullName);
			data.setStatement(statement);
			data.setPhone(phone);
			data.setLinkGitHubProfile(linkGitHubProfile);
			data.setLinkLinkedInProfile(linkLinkedInProfile);

			this.personalDataService.save(data);
			this.personalDataService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage PersonalDataService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * PersonalDataService = 21,1%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * PersonalData = 20%
	 */

}
