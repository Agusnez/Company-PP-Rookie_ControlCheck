
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Auditor;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private AuditorService	auditorService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.ROOKIES
	 * a)(Level C) Requirement 4.2: An actor who is authenticated as an administrator must be able to: Create user accounts for new auditors.
	 * 
	 * b) Negative cases:
	 * 2. Name = null
	 * 3. Name = blank
	 * 
	 * c) Sentence coverage
	 * -save(): 52%
	 * -create(): 100%
	 * 
	 * d) Data coverage
	 * -Auditor: 9,09091%
	 */

	@Test
	public void driverRegisterAuditor() {
		final Object testingData[][] = {
			{
				"name1", "surnames", 1205310889, "https://google.com", "email1@gmail.com", "672195205", "address1", "auditor100", "auditor100", "functionalTest", "VISA", "380004663288126", "12", "2020", "123", null
			},//1. All fine
			{
				null, "surnames", 1205310889, "https://google.com", "email1@gmail.com", "672195205", "address1", "auditor100", "auditor100", "functionalTest", "VISA", "380004663288126", "12", "2020", "123", ConstraintViolationException.class
			},//2. Name = null
			{
				"		", "surnames", 1205310889, "https://google.com", "email1@gmail.com", "672195205", "address1", "auditor100", "auditor100", "functionalTest", "VISA", "380004663288126", "12", "2020", "123", ConstraintViolationException.class
			},//3. Name = blank

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterAuditor((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}

	protected void templateRegisterAuditor(final String name, final String surnames, final Integer vat, final String photo, final String email, final String phone, final String address, final String username, final String password,
		final String holderName, final String make, final String number, final String expMonth, final String expYear, final String cvv, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate("admin");

			final Auditor auditor = this.auditorService.create();

			auditor.setName(name);
			auditor.setSurnames(surnames);
			auditor.setVat(vat);

			final CreditCard creditCard = new CreditCard();
			creditCard.setHolderName(holderName);
			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setMake(make);
			creditCard.setNumber(number);

			auditor.setCreditCard(creditCard);
			auditor.setPhoto(photo);
			auditor.setEmail(email);
			auditor.setPhone(phone);
			auditor.setAddress(address);

			auditor.getUserAccount().setUsername(username);
			auditor.getUserAccount().setPassword(password);

			this.startTransaction();

			this.auditorService.save(auditor);
			this.auditorService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

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
	 * -findOne():100%
	 * -save():84.8%
	 * d) Data coverage
	 */
	@Test
	public void driverEditAuditor() {
		final Object testingData[][] = {
			{
				"name1", "surnames", 1234654580, "https://google.com", "email1@gmail.com", "672195205", "address1", "auditor1", "functionalTest", "VISA", "377964663288126", "12", "2020", "123", "auditor1", null
			},//1. All fine
			{
				"name1", "surnames", 1234654580, "https://google.com", "email1gmail.com", "672195205", "address1", "auditor1", "functionalTest", "VISA", "377964663288126", "12", "2018", "123", "auditor1", ConstraintViolationException.class
			},//2. The expiration year of the credit card is past

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAuditor((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}

	protected void templateEditAuditor(final String name, final String surnames, final Integer vat, final String photo, final String email, final String phone, final String address, final String username, final String holderName, final String make,
		final String number, final String expMonth, final String expYear, final String cvv, final String auditorToEdit, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			this.authenticate(username);
			final Auditor auditor = this.auditorService.findOne(super.getEntityId(auditorToEdit));

			auditor.setName(name);
			auditor.setSurnames(surnames);
			auditor.setVat(vat);

			final CreditCard creditCard = auditor.getCreditCard();

			creditCard.setCvv(new Integer(cvv));
			creditCard.setExpMonth(new Integer(expMonth));
			creditCard.setExpYear(new Integer(expYear));
			creditCard.setHolderName(holderName);
			creditCard.setMake(make);
			creditCard.setNumber(number);

			auditor.setPhoto(photo);
			auditor.setEmail(email);
			auditor.setPhone(phone);
			auditor.setAddress(address);

			this.auditorService.save(auditor);
			this.auditorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.unauthenticate();
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage AuditorService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * AuditorService: 57.2%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Auditor = 9,09091%
	 */
}
