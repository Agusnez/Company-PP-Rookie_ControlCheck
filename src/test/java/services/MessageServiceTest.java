
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.HACKERRANK
	 * a)(Level A) Requirement 23.1: An actor who is authenticated must be able to: Manage his or her messages.
	 * 
	 * b) Negative cases:
	 * 2. Body is blank
	 * 3. Subject is blank
	 * 
	 * c) Sentence coverage
	 * -create3(): 100%
	 * -save():49%
	 * d) Data coverage
	 */

	@Test
	public void driverExchangeMessage() {
		final Object testingData[][] = {
			{
				"company1", "rookie1", "Body1", "Subject1", "Tag1", null
			},
			//1.All right
			{
				"company1", "rookie1", "", "Subject1", "Tag1", ConstraintViolationException.class
			},//2.Body blank
			{
				"company1", "rookie1", "Body1", "", "Tag1", ConstraintViolationException.class
			},//3.Subject blank

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateExchangeMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void templateExchangeMessage(final String sender, final String recipient, final String body, final String subject, final String tags, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			if (sender != null)
				super.authenticate(sender);

			final Message message = this.messageService.create3();
			message.setBody(body);
			message.setRecipient(this.actorService.findOne(super.getEntityId(recipient)));
			message.setSubject(subject);
			message.setTags(tags);

			this.messageService.save(message);
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		if (sender != null)
			super.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 4.1: An actor who is authenticated as an administrator must be able to: Run a procedure to notify the existing users of the rebranding. The system must guarantee that the process is run only once.
	 * 
	 * b) Negative cases:
	 * 2. Invalid authority
	 * 
	 * c) Sentence coverage
	 * -create3(): 100%
	 * -notificationRebranding(): 100%
	 * -save2(): 100%
	 * 
	 * d) Data coverage
	 * -Message = 0%
	 */

	@Test
	public void driverNotificationRebranding() {
		final Object testingData[][] = {
			{
				"admin", null
			},//1. All fine
			{
				"company1", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateNotificationRebranding((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateNotificationRebranding(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.authenticate(username);

			this.messageService.notificationRebranding();
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage MessageService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * MessageService = 16.2%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Message = 0%
	 */

}
