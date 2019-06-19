
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PositionService	positionService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.1: An actor who is authenticated as a company must be able to: Manage their positions
	 * 
	 * b) Negative cases:
	 * 6. The deadline is past
	 * 7. The title is null
	 * 8. The description is null
	 * 9. The deadline is null
	 * 10. The profile is null
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 59,3%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverCreatePosition() {
		final Object testingData[][] = {
			{
				"title", "description", "2019/12/12", "profile", "skills", "technologies", 12.0, "company1", null
			},//1. All fine
			{
				"problemas varios", "problemas servicios web", "2019/09/12", "desarrollador javascript", "mecanograf�a r�pida", "pc 8 gb ram", 500.0, "company2", null
			},//2. All fine
			{
				"testing", "jmeter's problems", "2019/09/12", "jtester", "knowledge about http errors", "pc with jmeter", 1000.0, "company3", null
			},//3. All fine
			{
				"UML", "I need to make a uml diagram", "2019/09/12", "software engineering", "astah skills", "pc with astah", 1000.0, "company4", null
			},//4. All fine
			{
				"Organisation group", "I need to coordinate a DP group", "2019/09/12", "Project Manager", "Knowledge about resolve group's conflicts", "trello,drive,skype", 1000.0, "company5", null
			},//5. All fine
			{
				"title", "description", "2018/12/12", "profile", "skills", "technologies", 12.0, "company1", IllegalArgumentException.class
			},//6. The deadline is past
			{
				null, "description", "2019/12/12", "profile", "skills", "technologies", 12.0, "company1", ConstraintViolationException.class
			},//7. The title is null
			{
				"title", null, "2018/12/12", "profile", "skills", "technologies", 12.0, "company1", IllegalArgumentException.class
			},//8. The description is null
			{
				"title", "description", null, "profile", "skills", "technologies", 12.0, "company1", NullPointerException.class
			},//9. The deadline is null
			{
				"title", "description", "2019/12/12", null, "skills", "technologies", 12.0, "company1", ConstraintViolationException.class
			},//10. The profile is null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePosition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void templateCreatePosition(final String title, final String description, final String deadline, final String profile, final String skills, final String technologies, final Double offeredSalary, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Position position = this.positionService.create();

			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			final Date deadlineFormatted = format.parse(deadline);
			position.setDeadline(deadlineFormatted);

			position.setDescription(description);
			position.setOfferedSalary(offeredSalary);
			position.setProfile(profile);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setTitle(title);

			this.positionService.save(position);
			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.1: An actor who is authenticated as a company must be able to: Manage their positions
	 * 
	 * b) Negative cases:
	 * 2. The position is in final mode, it can't be edited
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -save(): 82,7%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverEditPosition() {
		final Object testingData[][] = {
			{
				"title", "description", "2019/12/12", "profile", "skills", "technologies", 12.0, "company1", "position2", null
			},//1. All fine
			{
				"title", "description", "2019/12/12", "profile", "skills", "technologies", 12.0, "company1", "position1", IllegalArgumentException.class
			},//2. The position is in final mode, it can't be edited

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPosition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void templateEditPosition(final String title, final String description, final String deadline, final String profile, final String skills, final String technologies, final Double offeredSalary, final String username,
		final String positionToEdit, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Position position = this.positionService.findOne(super.getEntityId(positionToEdit));

			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			final Date deadlineFormatted = format.parse(deadline);
			position.setDeadline(deadlineFormatted);

			position.setDescription(description);
			position.setOfferedSalary(offeredSalary);
			position.setProfile(profile);
			position.setSkills(skills);
			position.setTechnologies(technologies);
			position.setTitle(title);

			this.positionService.save(position);
			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.1: An actor who is authenticated as a company must be able to: Manage their positions
	 * 
	 * b) Negative cases:
	 * 2. The position is in final mode, it can't be edited
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * -delete(): 79,2%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverDeletePosition() {
		final Object testingData[][] = {
			{
				"company1", "position2", null
			},//1. All fine
			{
				"company1", "position1", IllegalArgumentException.class
			},//2. The position is in final mode, it can't be edited

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeletePosition((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeletePosition(final String username, final String positionToDelete, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Position position = this.positionService.findOne(super.getEntityId(positionToDelete));
			this.positionService.delete(position);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.1: An actor who is authenticated as a company must be able to: Manage their positions
	 * 
	 * b) Negative cases:
	 * 2. The number of position is incorrect
	 * 
	 * c) Sentence coverage
	 * -findPositionByCompanyId(): 100%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverListPosition() {
		final Object testingData[][] = {
			{
				"company1", 3, null
			},//1. All fine
			{
				"company1", 0, IllegalArgumentException.class
			},//2. The number of position is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListPosition((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListPosition(final String company, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Position> positions = this.positionService.findPositionsByCompanyId(super.getEntityId(company));

			Assert.isTrue(positions.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.2: An actor who is not authenticated as a company must be able to: List the positions available and navigate to the corresponding companies
	 * Requirement 8.1: An actor who is authenticated must be able to: Do the same as an actor who is not authenticated, but register to the system.
	 * 
	 * b) Negative cases:
	 * 3. Company lists the positions available, but the number of positions is incorrect
	 * 
	 * c) Sentence coverage
	 * -findPositionsFinalModeTrue(): 100%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverListPositionAvailable() {
		final Object testingData[][] = {
			{
				"company1", 6, null
			},//1. Company lists the positions available (All fine)
			{
				null, 6, null
			},//2. Not registered actor lists the positions available (All fine) 
			{
				"company1", 0, IllegalArgumentException.class
			},//3. Company lists the positions available, but the number of positions is incorrect
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListPositionAvailable((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListPositionAvailable(final String username, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			if (username != null)
				this.authenticate(username);

			final Collection<Position> positions = this.positionService.findPositionsFinalModeTrue();

			Assert.isTrue(positions.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		if (username != null)
			this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.3: An actor who is not authenticated as a company must be able to: List the comapanies available and navigate to the corresponding positions
	 * Requirement 8.1: An actor who is authenticated must be able to: Do the same as an actor who is not authenticated, but register to the system.
	 * 
	 * b) Negative cases:
	 * 3. Company lists the positions available by a company, but the number of positions is incorrect
	 * 
	 * c) Sentence coverage
	 * -findPositionsByCompanyIdAndFinalModeTrue(): 100%
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverListPositionAvailableByCompany() {
		final Object testingData[][] = {
			{
				"company1", 1, "company1", null
			},//1. Company lists the positions available by a company (All fine)
			{
				null, 1, "company1", null
			},//2. Not registered actor lists the positions available by a company (All fine) 
			{
				"company1", 28, "company1", IllegalArgumentException.class
			},//3. Company lists the positions available by a company, but the number of positions is incorrect
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListPositionAvailableByCompany((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateListPositionAvailableByCompany(final String username, final Integer number, final String company, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			if (username != null)
				this.authenticate(username);

			final Collection<Position> positions = this.positionService.findPositionsByCompanyIdAndFinalModeTrue(super.getEntityId(company));

			Assert.isTrue(positions.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		if (username != null)
			this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 7.4: An actor who is not authenticated must be able to:
	 * Search for a position using a single key word that must be contained in its title, its
	 * description, its profile, its skills, its technologies, or the name of the corresponding
	 * company.
	 * 
	 * b) Negative cases:
	 * 2. The number of the positions finded is wrong
	 * 
	 * c) Sentence coverage
	 * -findPositionsByFilter(): 94,4%
	 * d) Data coverage
	 */
	@Test
	public void driverFilter() {
		final Object testingData[][] = {
			{
				"PC", 0, null
			},//1. All fine filter
			{
				"PC", 1, IllegalArgumentException.class
			},//2. The number of the positions finded is wrong

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFilter((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateFilter(final String keyword, final Integer results, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Collection<Position> positionsFinded = this.positionService.findPositionsByFilter(keyword, null);

			Assert.isTrue(positionsFinded.size() == results);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 3.1: An actor who is authenticated as an auditor must be able to: Self-assign a position to audit
	 * List of Positions not assigned to any Auditor
	 * 
	 * b) Negative cases:
	 * 2. The number of positions is incorrect
	 * 
	 * c) Sentence coverage
	 * -findPositionsFinalModeTrueWithoutDeadline():100%
	 * 
	 * d) Data coverage
	 * -Position: 0%
	 */
	@Test
	public void driverListPositionsToAssign() {
		final Object testingData[][] = {
			{
				9, null
			},//1. All fine
			{
				1, IllegalArgumentException.class
			},//2. The number of positions is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListPositionsToAssign((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListPositionsToAssign(final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Position> positions = this.positionService.findPositionsFinalModeTrueWithoutDeadline();

			final Integer tamano = positions.size();
			Assert.isTrue(tamano == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage PositionService
	 * ----TOTAL SENTENCE COVERAGE:
	 * PositionService = 49,7%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Position = 0%
	 */
}
