
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Position;
import domain.Sponsorship;
import forms.SponsorshipForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private PositionService		positionService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME-ROOKIES
	 * a)(Level A) Requirement 13.1: Providers manage his/her sponsorships: Edit
	 * 
	 * b) Negative cases:
	 * 2. Wrong user, same authority
	 * 3. Wrong authority
	 * 4. No authority
	 * 5. No register user
	 * 6. Credit Card expired year
	 * 7. Credit Card expired month
	 * 8. Credit Card expired both month and year
	 * 9. Incorrect banner URL
	 * 10. Incorrect target URL
	 * 11. Null banner URL
	 * 12. Null target URL
	 * 13. Null holder name
	 * 14. Null number
	 * 15. Incorrect number
	 * 
	 * c) Sentence coverage
	 * -save() = 100%
	 * -findOne() = 100%
	 * 
	 * d) Data coverage
	 * -Sponsorship = 33,3%
	 * -CreditCard = 66,6%
	 */

	@Test
	public void driverEditSponsorsip() {
		final Object testingData[][] = {

			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2020, 123, true, 0.0, null
			},//1. All fine
			{
				"provider2", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2020, 123, true, 0.0,
				IllegalArgumentException.class
			},//2. Wrong user, same authority
			{
				"brotherhood1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2020, 123, true, 0.0,
				IllegalArgumentException.class
			},//3. Wrong authority
			{
				"", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2020, 123, true, 0.0,
				IllegalArgumentException.class
			},//4. No authority 
			{
				"providerNoExistTest", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2020, 123, true, 0.0,
				IllegalArgumentException.class
			},//5. No register user
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12, 2018, 123, true, 0.0,
				IllegalArgumentException.class
			},//6. Credit Card expired year
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 01, 2019, 123, true, 0.0,
				IllegalArgumentException.class
			},//7. Credit Card expired month
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 01, 2018, 123, true, 0.0,
				IllegalArgumentException.class
			},//8. Credit Card expired both month and year 
			{
				"provider1", "sponsorship1", "test", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12,
				2020, 123, true, 0.0, ConstraintViolationException.class
			},//9. Incorrect banner URL
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "test", "José", "VISA", "1111222233334444", 12,
				2020, 123, true, 0.0, ConstraintViolationException.class
			},//10. Incorrect target URL
			{
				"provider1", "sponsorship1", null, "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1111222233334444", 12,
				2020, 123, true, 0.0, ConstraintViolationException.class
			},//11. Null banner URL
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", null, "José", "VISA", "1111222233334444", 12,
				2020, 123, true, 0.0, ConstraintViolationException.class
			},//12. Null target URL
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", null, "VISA", "1111222233334444", 12, 2020, 123, true, 0.0,
				ConstraintViolationException.class
			},//13. Null holder name
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", null, 12, 2020, 123, true, 0.0,
				ConstraintViolationException.class
			},//14. Null number
			{
				"provider1", "sponsorship1", "http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png",
				"http://www.foxandfiddlecalifornia.com/wp-content/uploads/2018/12/logo-clip-art-vector-online-royalty-free-public-domain-latest-copyright-logos-valuable-3.png", "José", "VISA", "1234", 12, 2020, 123, true, 0.0,
				ConstraintViolationException.class
			},//15. Incorrect number

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Boolean) testingData[i][10], (Double) testingData[i][11], (Class<?>) testingData[i][12]);

	}
	protected void templateEditSponsorship(final String username, final int sponsorshipId, final String banner, final String targetUrl, final String holderName, final String make, final String number, final Integer expMonth, final Integer expYear,
		final Integer cvv, final Boolean activated, final Double cost, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.authenticate(username);

			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);

			final CreditCard creditCard = new CreditCard();

			creditCard.setHolderName(holderName);
			creditCard.setMake(make);
			creditCard.setNumber(number);
			creditCard.setExpMonth(expMonth);
			creditCard.setExpYear(expYear);
			creditCard.setCvv(cvv);

			sponsorship.setBanner(banner);
			sponsorship.setTarget(targetUrl);
			sponsorship.setCreditCard(creditCard);
			sponsorship.setCost(cost);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * ACME-ROOKIES
	 * a)(Level A) Requirement 13.1: Providers manage his/her sponsorships : Create
	 * 
	 * b) Negative cases:
	 * 2. Wrong authority
	 * 3. No authority
	 * 4. No register user
	 * 5. Parade not accepted
	 * 6. Parade not exists
	 * 
	 * c) Sentence coverage
	 * -create() = 100%
	 * 
	 * d) Data coverage
	 * -Sponsorship = 0%
	 */
	@Test
	public void driverCreateSponsorship() {
		final Object testingData[][] = {

			{
				"provider1", "position3", null
			},//1. All fine
			{
				"brotherhood", "position3", IllegalArgumentException.class
			},//2. Wrong authority
			{
				null, "position3", IllegalArgumentException.class
			},//3. No authority
			{
				"sponsorNoTest", "position3", IllegalArgumentException.class
			},//4. No register user
			{
				"provider1", "position4", IllegalArgumentException.class
			},//5. Parade not accepted
			{
				"provider1", "paradeNoTest", AssertionError.class
			},//6. Parade not exists

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSponsorship((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateCreateSponsorship(final String username, final String position, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			this.authenticate(username);

			final int positionId = super.getEntityId(position);

			final SponsorshipForm sponsorshipForm = this.sponsorshipService.create(positionId);

			Assert.isTrue(sponsorshipForm != null);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.unauthenticate();

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * ACME-ROOKIES
	 * a)(Level A) Requirement 13.1: Providers manage his/her sponsorships : List
	 * 
	 * b) Negative cases:
	 * 2. Incorrect results
	 * 
	 * c) Sentence coverage
	 * -findAllBySponsorId() = 100%
	 * 
	 * d) Data coverage
	 * -Sponsorship = 0%
	 */

	@Test
	public void driverListSponsorship() {
		final Object testingData[][] = {

			{
				"provider1", 2, null
			},//1. All fine 
			{
				"provider1", 28, IllegalArgumentException.class
			},//2. Incorrect results

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListSponsorship((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void templateListSponsorship(final String username, final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			super.authenticate(username);

			final int id = this.providerService.findByPrincipal().getId();

			final Integer result = this.sponsorshipService.findAllByProviderId(id).size();
			Assert.isTrue(expectedInt == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME-ROOKIES
	 * a)(Level A) Requirement 15: Every time a position with sponsorships is displayed, a random sponsorship must be selected and its banner must be shown as little intrusively as possible.
	 * 
	 * b) Negative cases:
	 * 2. Wrong return
	 * 
	 * c) Sentence coverage
	 * -findOne() = 100%
	 * -ramdomSponsorship() = 74,7%
	 * 
	 * d) Data coverage
	 * -Sponsorship = 0%
	 */

	@Test
	public void driverRandomSponsorship() {
		final Object testingData[][] = {

			{
				"position1", "sponsorship1", "sponsorship4", null
			},//1. All fine 
			{
				"position1", "sponsorship3", "sponsorship2", IllegalArgumentException.class
			},//2. Wrong return

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRandomSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void templateRandomSponsorship(final String positionBean, final String sponsorshipBean1, final String sponsorshipBean2, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Position position = this.positionService.findOne(super.getEntityId(positionBean));

			final Collection<Sponsorship> sponsorships = new HashSet<>();
			final Sponsorship s1 = this.sponsorshipService.findOne(super.getEntityId(sponsorshipBean1));
			final Sponsorship s2 = this.sponsorshipService.findOne(super.getEntityId(sponsorshipBean2));
			sponsorships.add(s1);
			sponsorships.add(s2);

			this.startTransaction();

			final Sponsorship s = this.sponsorshipService.ramdomSponsorship(position.getId());

			Assert.isTrue(sponsorships.contains(s));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage SponsorshipService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * SponsorshipService = 47,3%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * -Sponsorship: 33,3%
	 * -CreditCard: 66,6%
	 */

}
