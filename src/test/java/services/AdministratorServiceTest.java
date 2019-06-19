
package services;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.CreditCard;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private AdministratorService	adminService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 11.1: An actor who is authenticated as an administrator must be able to: Create user accounts for new administrators.
	 * 
	 * b) Negative cases:
	 * 2. Name = blank
	 * 3. Name = null
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save():53,3%
	 * d) Data coverage
	 * -Admin: 12,5% (name)
	 */

	@Test
	public void driverRegisterAdmin() {
		final Object testingData[][] = {
			{
				"admin", "name1", "surname1", "12100", "https://google.com", "email1@gmail.com", "cvycjwbi", "visa", "1111222233334444", "12", "2020", "900", "672195205", "address1", "admin55", "admin55", null
			},//1. All fine
			{
				"admin", "		", "surname1", "12100", "https://google.com", "email1@gmail.com", "cvycjwbi", "visa", "1111222233334444", "12", "2020", "900", "672195205", "address1", "admin55", "admin55", ConstraintViolationException.class
			},//2. Name = blank
			{
				"admin", null, "surname1", "12100", "https://google.com", "email1@gmail.com", "cvycjwbi", "visa", "1111222233334444", "12", "2020", "900", "672195205", "address1", "admin55", "admin55", ConstraintViolationException.class
			},//3. Name = null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterAdmin((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], Integer.valueOf((String) testingData[i][3]), (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], Integer.valueOf((String) testingData[i][9]), Integer.valueOf((String) testingData[i][10]), Integer.valueOf((String) testingData[i][11]), (String) testingData[i][12],
				(String) testingData[i][13], (String) testingData[i][14], (String) testingData[i][15], (Class<?>) testingData[i][16]);
	}
	protected void templateRegisterAdmin(final String usernameLogin, final String name, final String surnames, final Integer vat, final String photo, final String email, final String holderName, final String make, final String number,
		final Integer expMonth, final Integer expYear, final Integer cvv, final String phone, final String address, final String username, final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(usernameLogin);

			final CreditCard cc = new CreditCard();
			cc.setHolderName(holderName);
			cc.setMake(make);
			cc.setNumber(number);
			cc.setExpMonth(expMonth);
			cc.setExpYear(expYear);
			cc.setCvv(cvv);

			final Administrator admin = this.adminService.create();

			admin.setName(name);
			admin.setSurnames(surnames);
			admin.setVat(vat);
			admin.setPhoto(photo);
			admin.setEmail(email);
			admin.setCreditCard(cc);
			admin.setPhone(phone);
			admin.setAddress(address);

			admin.getUserAccount().setUsername(username);
			admin.getUserAccount().setPassword(password);

			this.adminService.save(admin);
			this.adminService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 8.2: An actor who is authenticated must be able to: Edit his/her personal data.
	 * 
	 * b) Negative cases:
	 * 2. The expiration year of the credit card is past
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save():53,3%
	 * d) Data coverage
	 * -Admin: 0%
	 */
	@Test
	public void driverEditAdmin() {
		final Object testingData[][] = {
			{
				"endesa", "name1", "surnames", 12000, "https://google.com", "email1@gmail.com", "672195205", "address1", "admin", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", "administrator1", null
			},//1. All fine
			{
				"endesa", "name1", "surnames", 12000, "https://google.com", "email1gmail.com", "672195205", "address1", "admin", "functionalTest", "VISA", "377964663288126", "12", "2018", "123", "administrator1", ConstraintViolationException.class
			},//2. The expiration year of the credit card is past

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdmin((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	protected void templateEditAdmin(final String commercialName, final String name, final String surnames, final Integer vat, final String photo, final String email, final String phone, final String address, final String username,
		final String holderName, final String make, final String number, final String expMonth, final String expYear, final String cvv, final String adminToEdit, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			final Administrator administrator = this.adminService.findOne(super.getEntityId(adminToEdit));

			administrator.setName(name);
			administrator.setSurnames(surnames);
			administrator.setVat(vat);

			final CreditCard creditCard = administrator.getCreditCard();

			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setHolderName(holderName);
			creditCard.setMake(make);
			creditCard.setNumber(number);

			administrator.setPhoto(photo);
			administrator.setEmail(email);
			administrator.setPhone(phone);
			administrator.setAddress(address);

			this.adminService.save(administrator);
			this.adminService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		super.checkExceptions(expected, caught);

	}

	@Test
	public void SpammerTest() {
		final Object testingData[][] = {
			{
				"admin", null
			},//1. All fine
			{
				"rookie1", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.AuthoritySpammerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void AuthoritySpammerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			this.adminService.spammer();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	@Test
	public void DashboardTest() {
		final Object testingData[][] = {
			{
				2.5714, 1, 7, 1.9898, 1.0, 1, 1, 0.0, "[Wallace Inc, Russell, Skinner and Wilson, Petty, Sanchez and Davis]", "[Steven, Kimberly, Ryan]", 2045.5555555555557, 1227.3068176667798, 500, 4471, 2721, 2712, 2, 2, 2.0, 0.0, 0.0, 0, 0, 0.0, 0.0,
				null
			},//1. All fine
			{
				1.20, 2, 5, 1.5, 1.0, 1, 1, 0.0, "[Wallace Inc, Petty, Sanchez and Davis, Russell, Skinner and Wilson]", "[Angela, Steven, Kimberly]", 2354.6666666666665, 1110.9637057778061, 755, 4471, 2721, 2712, 2, 2, 2.0, 0.0, 0.0, 0, 0, 0.0, 0.0,
				IllegalArgumentException.class
			}
		//1. Wrong
		};

		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate((Double) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Double) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Double) testingData[i][10], (Double) testingData[i][11], (Integer) testingData[i][12], (Integer) testingData[i][13], (Integer) testingData[i][14],
				(Integer) testingData[i][15], (Integer) testingData[i][16], (Integer) testingData[i][17], (Double) testingData[i][18], (Double) testingData[i][19], (Double) testingData[i][20], (Integer) testingData[i][21], (Integer) testingData[i][22],
				(Double) testingData[i][23], (Double) testingData[i][24], (Class<?>) testingData[i][25]);
	}

	protected void DashboardTemplate(final Double expectedResult, final Integer expectedResult2, final Integer expectedResult3, final Double expectedResult4, final Double expectedResult5, final Integer expectedResult6, final Integer expectedResult7,
		final Double expectedResult8, final String expectedResult9, final String expectedResult10, final Double expectedResult11, final Double expectedResult12, final Integer expectedResult13, final Integer expectedResult14,
		final Integer expectedResult15, final Integer expectedResult16, final Integer expectedResult17, final Integer expectedResult18, final Double expectedResult19, final Double expectedResult20, final Double expectedResult21,
		final Integer expectedResult22, final Integer expectedResult23, final Double expectedResult24, final Double expectedResult25, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("admin");

			final Double avgPC = this.adminService.avgOfPositionsPerCompany();
			final Integer minPC = this.adminService.minOfPositionsPerCompany();
			final Integer maxPC = this.adminService.maxOfPositionsPerCompany();
			final Double stdPC = this.adminService.stdOfPositionsPerCompany();

			final Double avgAH = this.adminService.avgOfApplicationsPerRookie();
			final Integer minAH = this.adminService.minOfApplicationsPerRookie();
			final Integer maxAH = this.adminService.maxOfApplicationsPerRookie();
			final Double stdAH = this.adminService.stdOfApplicationsPerRookie();

			final List<String> topC = this.adminService.topCompaniesWithMorePositions();

			final Double avgS = this.adminService.avgSalaries();
			final Integer minS = this.adminService.minSalary();
			final Integer maxS = this.adminService.maxSalary();
			final Double stdS = this.adminService.stdSalaries();

			final Position bP = this.adminService.findBestPosition();
			final Position wP = this.adminService.findWorstPosition();

			final Integer minCH = this.adminService.minNumberOfCurriculaPerRookie();
			final Integer maxCH = this.adminService.maxNumberOfCurriculaPerRookie();
			final Double avgCH = this.adminService.avgNumberOfCurriculaPerRookie();
			final Double stdCH = this.adminService.stdNumberOfCurriculaPerRookie();

			final Double stdRF = this.adminService.stdNumberOfResultsInFinders();
			final Integer minRF = this.adminService.minNumberOfResultsInFinders();
			final Integer maxRF = this.adminService.maxNumberOfResultsInFinders();
			final Double avgRF = this.adminService.avgNumberOfResultsInFinders();

			final Double ratioEF = this.adminService.ratioEmptyNotEmptyFinders();

			Assert.isTrue(avgPC.equals(expectedResult));
			Assert.isTrue(minPC.equals(expectedResult2));
			Assert.isTrue(maxPC.equals(expectedResult3));
			Assert.isTrue(stdPC.equals(expectedResult4));
			Assert.isTrue(avgAH.equals(expectedResult5));
			Assert.isTrue(minAH.equals(expectedResult6));
			Assert.isTrue(maxAH.equals(expectedResult7));
			Assert.isTrue(stdAH.equals(expectedResult8));
			Assert.isTrue(avgS.equals(expectedResult11));
			Assert.isTrue(stdS.equals(expectedResult12));
			Assert.isTrue(minS.equals(expectedResult13));
			Assert.isTrue(maxS.equals(expectedResult14));
			Assert.isTrue(minCH.equals(expectedResult17));
			Assert.isTrue(maxCH.equals(expectedResult18));
			Assert.isTrue(avgCH.equals(expectedResult19));
			Assert.isTrue(stdCH.equals(expectedResult20));
			Assert.isTrue(avgRF.equals(expectedResult21));
			Assert.isTrue(minRF.equals(expectedResult22));
			Assert.isTrue(maxRF.equals(expectedResult23));
			Assert.isTrue(stdRF.equals(expectedResult24));
			Assert.isTrue(ratioEF.equals(expectedResult25));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/*
	 * -------Coverage AdministratorService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * AdministratorService = 58%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Administrator = 12,5%%
	 */

}
