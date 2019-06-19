
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private AuditService	auditService;

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
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 3.2: An actor who is authenticated as an auditor must be able to: Manage his or her audits
	 * Create Audit
	 * 
	 * b) Negative cases:
	 * 2. Hacker is trying to create an Audit
	 * 
	 * c) Sentence coverage
	 * -create():100%
	 * -save(): 76.6%
	 * -flush():100%
	 * d) Data coverage
	 * -Audit: 0%
	 */
	@Test
	public void driverCreateAudit() {
		final Object testingData[][] = {
			{
				"text1", 2.0, true, "auditor1", "position1", null
			},//1. All fine
			{
				"text1", 2.0, true, "hacker1", "position1", IllegalArgumentException.class
			},//2. Hacker is trying to create an audit

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAudit((String) testingData[i][0], (Double) testingData[i][1], (Boolean) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void templateCreateAudit(final String text, final Double score, final Boolean finalMode, final String username, final String position, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Audit audit = this.auditService.create(this.positionService.findOne(super.getEntityId(position)));

			audit.setText(text);
			audit.setScore(score);
			audit.setFinalMode(finalMode);

			this.auditService.save(audit);
			this.auditService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 3.2: An actor who is authenticated as an auditor must be able to: Manage his or her audits
	 * Edit an Audit
	 * 
	 * b) Negative cases:
	 * 2. The audit is in final mode, it can't be edited
	 * 
	 * c) Sentence coverage
	 * -findOne():100%
	 * -save():96.6%
	 * d) Data coverage
	 * -Audit: 0%
	 */
	@Test
	public void driverEditAudit() {
		final Object testingData[][] = {
			{
				"text1", "auditor1", "audit2", null
			},//1. All fine
			{
				"text1", "auditor1", "audit1", IllegalArgumentException.class
			},//2. The audit is in final mode, it can't be edited

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAudit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateEditAudit(final String text, final String username, final String auditToEdit, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Audit audit = this.auditService.findOne(super.getEntityId(auditToEdit));

			audit.setText(text);

			this.auditService.save(audit);
			this.auditService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 3.2: An actor who is authenticated as an auditor must be able to: Manage his or her audits
	 * Delete an Audit
	 * 
	 * b) Negative cases:
	 * 2. The audit is in final mode, it can't be deleted
	 * 
	 * c) Sentence coverage
	 * -findOne():100%
	 * -delete():96.3%
	 * d) Data coverage
	 * -Audit: 0%
	 */
	@Test
	public void driverDeleteProblem() {
		final Object testingData[][] = {
			{
				"auditor1", "audit2", null
			},//1. All fine
			{
				"auditor1", "audit1", IllegalArgumentException.class
			},//2. The audit is in final mode, it can't be deleted

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteProblem((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteProblem(final String username, final String auditToDelete, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);

			final Audit audit = this.auditService.findOne(super.getEntityId(auditToDelete));
			this.auditService.delete(audit);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 3.2: An actor who is authenticated as an auditor must be able to: Manage his or her audits
	 * List Audits
	 * 
	 * b) Negative cases:
	 * 2. The number of audits is incorrect
	 * 
	 * c) Sentence coverage
	 * -findAuditsByAuditorId():100%
	 * 
	 * d) Data coverage
	 * -Audit: 0%
	 */
	@Test
	public void driverListAudits() {
		final Object testingData[][] = {
			{
				"auditor1", 3, null
			},//1. All fine
			{
				"auditor1", 0, IllegalArgumentException.class
			},//2. The number of audits is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListAudits((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListAudits(final String auditor, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Audit> audits = this.auditService.findAuditsByAuditorId(super.getEntityId(auditor));

			Assert.isTrue(audits.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 5: Everywhere a position is shown, the corresponding actor must be able to browse its audits
	 * 
	 * 
	 * b) Negative cases:
	 * 2. The number of audits is incorrect
	 * 
	 * c) Sentence coverage
	 * -findAuditsByPositionId():100%
	 * 
	 * d) Data coverage
	 * -Audit: 0%
	 */
	@Test
	public void driverListAuditsByPositionId() {
		final Object testingData[][] = {
			{
				"position1", 3, null
			},//1. All fine
			{
				"position1", 0, IllegalArgumentException.class
			},//2. The number of audits is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListAuditsBypositionId((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListAuditsBypositionId(final String position, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Audit> audits = this.auditService.findAuditsByPositionId(super.getEntityId(position));

			Assert.isTrue(audits.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage AuditService
	 * ----TOTAL SENTENCE COVERAGE:
	 * AuditService = 58%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Audit = 0%
	 */

}
