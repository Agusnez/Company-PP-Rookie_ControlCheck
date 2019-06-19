
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
public class ActorServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
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
	 * a)(Level A) Requirement 24.3: An actor who is authenticated as an administrator must be able to: Ban an actor
	 * 
	 * b) Negative cases:
	 * 2. Ban yourself
	 * 
	 * c) Sentence coverage
	 * -banOrUnBanActor(): 58.5%
	 * 
	 * d) Data coverage
	 * -Actor: 0%
	 */

	@Test
	public void driverBanActor() {

		final Object testingData[][] = {

			{
				"company1", null
			}, //1. All fine
			{
				"administrator1", IllegalArgumentException.class
			}, //2. Ban yourself

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateBanActor(final String actorBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.startTransaction();

			super.authenticate("admin");

			final Actor actor = this.actorService.findOne(super.getEntityId(actorBean));

			this.actorService.banOrUnBanActor(actor);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level A) Requirement 24.4: An actor who is authenticated as an administrator must be able to: Unban an actor
	 * 
	 * b) Negative cases:
	 * 2. Unban yourself
	 * 
	 * c) Sentence coverage
	 * -banOrUnBanActor(): 56.1%
	 * 
	 * d) Data coverage
	 * -Actor: 0%
	 */

	@Test
	public void driverUnbanActor() {

		final Object testingData[][] = {

			{
				"rookie1", null
			}, //1. All fine
			{
				"administrator1", IllegalArgumentException.class
			}, //2. Unban yourself

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateUnbanActor(final String actorBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.startTransaction();

			super.authenticate("admin");

			final Actor actor = this.actorService.findOne(super.getEntityId(actorBean));

			this.actorService.banOrUnBanActor(actor);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * -------Coverage ActorService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * ActorService = 25,8%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Actor = 0%
	 */
}
