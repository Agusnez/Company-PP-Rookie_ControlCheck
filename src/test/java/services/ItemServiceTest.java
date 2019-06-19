
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
import domain.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ItemService	itemService;


	/*
	 * ----CALCULATE COVERAGE----
	 * The previous delivery, we calculate it manually. In this one instead we are using the plugin called EclEmma,
	 * with which we can automatically calculate the percentage.
	 * 
	 * Each of the test have their result just before them, and the coverage of the complete test is shown at the end of the document.
	 */

	/*
	 * ACME.ROOKIES
	 * a)(Level B) Requirement 10.1: An actor who is authenticated as a provider must be able to: Manage his or her catalogue of items, which includes LISTING, showing, creating, updating, and deleting them.
	 * 
	 * b) Negative cases:
	 * 2. Item doesn't belong Provider
	 * 
	 * 
	 * c) Sentence coverage
	 * -findOne(): 100%
	 * d) Data coverage
	 */

	@Test
	public void driverListItems() {
		final Object testingData[][] = {

			{
				"item1", "provider1", null
			//1. All right
			}, {
				"item1", "provider2", IllegalArgumentException.class
			//2. Item doesn't belong Provider
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListItems((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListItems(final String itemId, final String providerUsername, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			super.authenticate(providerUsername);
			final Integer itemIdInteger = super.getEntityId(itemId);
			final Integer providerIdInteger = super.getEntityId(providerUsername);

			final Item item = this.itemService.findOne(itemIdInteger);

			final Collection<Item> items = this.itemService.findItemsByProviderId(providerIdInteger);

			Assert.isTrue(items.contains(item));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level B) Requirement 10.1: An actor who is authenticated as a provider must be able to: Manage his or her catalogue of items, which includes listing, showing, CREATING, updating, and deleting them.
	 * 
	 * b) Negative cases:
	 * 2. name = blank
	 * 3. name = not safe html
	 * 4. description = blank
	 * 5. description = not safe html
	 * 
	 * c) Sentence coverage
	 * -create(): 100%
	 * -save(): 100%
	 * -findAll(): 100%
	 * d) Data coverage
	 * -Item: 50%
	 */

	@Test
	public void driverCreateItem() {
		final Object testingData[][] = {

			{
				"provider1", "name1", "description1", "https://www.youtube.com", "https://www.picture.com", null
			//1. All right
			}, {
				"provider1", "", "description1", "https://www.youtube.com", "https://www.picture.com", ConstraintViolationException.class

			//2. name = blank
			}, {
				"provider1", "<script>alert('hola')</script>", "description1", "https://www.youtube.com", "https://www.picture.com", ConstraintViolationException.class
			//3. name = not safe html
			}, {
				"provider1", "name1", "", "https://www.youtube.com", "https://www.picture.com", ConstraintViolationException.class
			//4. description = blank
			}, {
				"provider1", "name1", "<script>alert('hola')</script>", "https://www.youtube.com", "https://www.picture.com", ConstraintViolationException.class
			//5. description = not safe html
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateItem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);

	}
	protected void templateCreateItem(final String provider, final String name, final String description, final String link, final String picture, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			if (provider != null) {
				super.authenticate(provider);

				final Item item = this.itemService.create();
				item.setName(name);
				item.setLink(link);
				item.setDescription(description);
				final Collection<String> pictures = new HashSet<String>();
				pictures.add(picture);
				item.setPictures(pictures);

				final Item saved = this.itemService.save(item);
				this.itemService.flush();

				final Collection<Item> items = this.itemService.findAll();
				Assert.isTrue(items.contains(saved));
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		if (provider != null)
			super.unauthenticate();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level B) Requirement 10.1: An actor who is authenticated as a provider must be able to: Manage his or her catalogue of items, which includes listing, showing, creating, UPDATING, and deleting them.
	 * 
	 * b) Negative cases:
	 * 2. Item doesn't belong Provider
	 * 
	 * c) Sentence coverage
	 * -save(): 100%
	 * -findAll(): 100%
	 * -findOne(): 100%
	 * d) Data coverage
	 * -Item: 0%
	 */

	@Test
	public void driverUpdateItem() {
		final Object testingData[][] = {

			{
				"Provider1", "item1", "name1", null
			//1. All right
			}, {
				"Provider2", "item1", "name1", IllegalArgumentException.class
			//2. Item doesn't belong Provider
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateUpdateItem((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateUpdateItem(final String provider, final String itemId, final String name, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			super.authenticate(provider);

			final Item item = this.itemService.findOne(super.getEntityId(itemId));
			item.setName(name);

			//this.startTransaction();
			final Item saved = this.itemService.save(item);
			this.itemService.flush();

			final Collection<Item> items = this.itemService.findAll();

			Assert.isTrue(items.contains(saved));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		super.checkExceptions(expected, caught);
		//this.rollbackTransaction();

	}

	/*
	 * ACME.HACKERRANK
	 * a)(Level A) Requirement 23.1: An actor who is authenticated must be able to: Manage his or her social profiles, which includes listing, showing, creating, updating, and DELETING them.
	 * 
	 * b) Negative cases:
	 * 2. Item doesn't belong Provider
	 * 
	 * c) Sentence coverage
	 * -delete(): 96%
	 * -findAll(): 100%
	 * -findOne(): 100%
	 * d) Data coverage
	 * -Item: 0%
	 */

	@Test
	public void driverDeleteItem() {
		final Object testingData[][] = {

			{
				"Provider1", "item1", null
			//1. All right
			}, {
				"Provider2", "item1", IllegalArgumentException.class
			//2. Item doesn't belong Provider
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteItem((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateDeleteItem(final String provider, final String itemId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			super.authenticate(provider);

			final Item item = this.itemService.findOne(super.getEntityId(itemId));

			this.itemService.delete(item);
			this.itemService.flush();

			final Collection<Item> items = this.itemService.findAll();
			Assert.isTrue(!items.contains(item));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level B) Requirement 9.2: An actor who is not authenticated must be able to: Browse the list of items
	 * 
	 * 
	 * b) Negative cases:
	 * 2. The number of items is incorrect
	 * 
	 * c) Sentence coverage
	 * -findAll()=100%
	 * 
	 * d) Data coverage
	 * -Item: 0%
	 */
	@Test
	public void driverListAllItems() {
		final Object testingData[][] = {
			{
				10, null
			},//1. All fine
			{
				0, IllegalArgumentException.class
			},//2. The number of items is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListAllItems((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void templateListAllItems(final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Item> items = this.itemService.findAll();

			Assert.isTrue(items.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME.ROOKIES
	 * a)(Level B) Requirement 9.2: An actor who is not authenticated must be able to: Navigate to the item of a provider
	 * 
	 * 
	 * b) Negative cases:
	 * 2. The number of items is incorrect
	 * 
	 * c) Sentence coverage
	 * -findItemsByProviderId()=100%
	 * 
	 * d) Data coverage
	 * -Item: 0%
	 */
	@Test
	public void driverItemsOfProvider() {
		final Object testingData[][] = {
			{
				"provider1", 5, null
			},//1. All fine
			{
				"provider1", 0, IllegalArgumentException.class
			},//2. The number of items is incorrect

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateItemsOfProvider((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateItemsOfProvider(final String provider, final Integer number, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			final Collection<Item> items = this.itemService.findItemsByProviderId(super.getEntityId(provider));

			Assert.isTrue(items.size() == number);
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage ItemService
	 * ----TOTAL SENTENCE COVERAGE:
	 * ItemService = 57.9%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Item = 50%
	 */
}
