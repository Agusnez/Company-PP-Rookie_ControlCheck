
package services;

import java.net.URL;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.Actor;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	// Managed Repository ------------------------
	@Autowired
	private ItemRepository	itemRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private Validator		validator;


	public Collection<Item> findAll() {

		final Collection<Item> items = this.itemRepository.findAll();

		Assert.notNull(items);

		return items;
	}

	public Item findOne(final int itemId) {

		final Item item = this.itemRepository.findOne(itemId);

		return item;

	}

	public Item create() {

		Item result;

		result = new Item();

		final Actor actor = this.actorService.findByPrincipal();

		final Provider p = this.providerService.findOne(actor.getId());

		result.setProvider(p);

		return result;

	}

	public Item save(final Item item) {

		Assert.notNull(item);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.isTrue(item.getProvider().equals(actor));

		Item result;

		this.checkPictures(item.getPictures());

		result = this.itemRepository.save(item);

		return result;

	}

	public void delete(final Item item) {

		Assert.notNull(item);
		Assert.isTrue(item.getId() != 0);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(item.getProvider().equals(actor));

		this.itemRepository.delete(item);

	}

	public void deleteAll(final int actorId) {

		final Collection<Item> items = this.itemRepository.findItemsByProviderId(actorId);

		if (!items.isEmpty())
			for (final Item i : items)
				this.itemRepository.delete(i);
	}

	//Other business methods
	public Collection<Item> findItemsByProviderId(final int providerId) {

		final Collection<Item> res = this.itemRepository.findItemsByProviderId(providerId);

		return res;
	}

	public boolean existId(final int itemId) {
		Boolean res = false;

		final Item item = this.itemRepository.findOne(itemId);

		if (item != null)
			res = true;

		return res;
	}

	public Boolean securityItem(final int itemId) {

		Boolean res = false;

		final Item i = this.itemRepository.findOne(itemId);

		final Actor login = this.actorService.findByPrincipal();

		final Actor provider = i.getProvider();

		if (login.equals(provider))
			res = true;

		return res;
	}

	public Item reconstruct(final Item item, final BindingResult binding) {

		Item result;

		if (item.getId() == 0 || item == null) {

			final Item itemNew = this.create();

			item.setProvider(itemNew.getProvider());

			this.validator.validate(item, binding);

			result = item;
		} else {

			final Item itemBBDD = this.findOne(item.getId());

			if (itemBBDD != null) {

				item.setProvider(itemBBDD.getProvider());

				this.validator.validate(item, binding);
			}

			result = item;

		}

		return result;
	}

	public void flush() {
		this.itemRepository.flush();
	}

	public void checkPictures(final Collection<String> attachments) {

		for (final String url : attachments)
			try {
				new URL(url);
			} catch (final Exception e) {
				throw new DataIntegrityViolationException("Invalid URL");
			}
	}

}
