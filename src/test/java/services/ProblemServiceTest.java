
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ProblemService	problemService;

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
	 * a)(Level C) Requirement 9.2: An actor who is authenticated as a company must be able to: Manage their database of problems
	 * 
	 * b) Negative cases:
	 * 2. Hacker is trying to create a problem
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 74,5%
	 * d) Data coverage
	 * -Problem: 0%
	 */
	@Test
	public void driverCreateProblem() {
		final Object testingData[][] = {
			{
				"title1", "statement1", "hint1", "https://google.com", true, "company1", null
			},//1. All fine
			{
				"title1", "statement1", "hint1", "https://google.com", true, "hacker1", IllegalArgumentException.class
			},//2. Hacker is trying to create a problem

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateProblem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void templateCreateProblem(final String title, final String statement, final String hint, final String attachment, final Boolean finalMode, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Problem problem = this.problemService.create();

			final Collection<String> attachments = new ArrayList<>();
			attachments.add(attachment);

			problem.setTitle(title);
			problem.setStatement(statement);
			problem.setHint(hint);
			problem.setAttachments(attachments);
			problem.setFinalMode(finalMode);

			this.problemService.save(problem);
			this.problemService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.2: An actor who is authenticated as a company must be able to: Manage their database of problems
	 * 
	 * b) Negative cases:
	 * 2. The problem is in final mode, it can't be edited
	 * 
	 * c) Sentence coverage
	 * -findOne():100%
	 * -save():98,2%
	 * d) Data coverage
	 * -Problem: 0%
	 */
	@Test
	public void driverEditProblem() {
		final Object testingData[][] = {
			{
				"title1", "company1", "problem11", null
			},//1. All fine
			{
				"title1", "company1", "problem1", IllegalArgumentException.class
			},//2. The problem is in final mode, it can't be edited

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditProblem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateEditProblem(final String title, final String username, final String problemToEdit, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Problem problem = this.problemService.findOne(super.getEntityId(problemToEdit));

			problem.setTitle(title);

			this.problemService.save(problem);
			this.problemService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 9.2: An actor who is authenticated as a company must be able to: Manage their database of problems
	 * 
	 * b) Negative cases:
	 * 2. The position is in final mode, it can't be deleted
	 * 
	 * c) Sentence coverage
	 * -findOne():100%
	 * -delete(): 97,7%
	 * d) Data coverage
	 * -Problem: 0%
	 */
	@Test
	public void driverDeleteProblem() {
		final Object testingData[][] = {
			{
				"company1", "problem11", null
			},//1. All fine
			{
				"company1", "problem1", IllegalArgumentException.class
			},//2. The problem is in final mode, it can't be deleted

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteProblem((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteProblem(final String username, final String problemToDelete, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Problem problem = this.problemService.findOne(super.getEntityId(problemToDelete));
			this.problemService.delete(problem);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) (Level C) Requirement 9.2: An actor who is authenticated as a company must be able to: Manage their database of problems
	 * 
	 * b) Negative cases:
	 * 2. The number of problems is incorrect
	 * 
	 * c) Sentence coverage
	 * -findProblemsByCompanyId():100%
	 * d) Data coverage
	 * -Problem: 0%
	 */
	@Test
	public void driverListProblem() {
		final Object testingData[][] = {
			{
				"company1", 6, null
			},//1. All fine
			{
				"company1", 0, IllegalArgumentException.class
			},//2. The number of problems is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListProblem((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListProblem(final String company, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Problem> problems = this.problemService.findProblemByCompanyId(super.getEntityId(company));

			Assert.isTrue(problems.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level C) Requirement 5: Companies can manage a database of problems that they can associate with their positions
	 * 
	 * b) Negative cases:
	 * 2. The problem is in final mode, it can't be edited
	 * 
	 * c) Sentence coverage
	 * -findOne():100%
	 * -addPositionToProblem():94,5%
	 * d) Data coverage
	 * -Problem: 0%
	 */
	@Test
	public void driverAddPositionToProblem() {
		final Object testingData[][] = {
			{
				"company1", "problem2", "position2", null
			},//1. All fine
			{
				"hacker1", "problem3", "position1", IllegalArgumentException.class
			},//2. Hacker cannot add position to problem

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAddPositionToProblem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateAddPositionToProblem(final String username, final String problem, final String position, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Problem problemBBDD = this.problemService.findOne(super.getEntityId(problem));
			final Position positionBBDD = this.positionService.findOne(super.getEntityId(position));
			this.problemService.addPositionToProblem(positionBBDD, problemBBDD);

			this.problemService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage ProblemService
	 * ----TOTAL SENTENCE COVERAGE:
	 * ProblemService = 62,1%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Problem = 0%
	 */
}
