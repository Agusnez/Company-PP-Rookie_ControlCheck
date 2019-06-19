
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.Position;
import domain.Rookie;

@Service
@Transactional
public class FinderService {

	// Managed Repository ------------------------
	@Autowired
	private FinderRepository	finderRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private Validator			validator;

	@Autowired
	private RookieService		rookieService;


	public Finder create() {

		Finder result;

		result = new Finder();

		result.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		return result;

	}

	public Collection<Finder> findAll() {

		final Collection<Finder> result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Finder findOne(final int finderId) {

		final Finder result = this.finderRepository.findOne(finderId);
		return result;

	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		if (finder.getId() != 0) {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			final Authority hW = new Authority();
			hW.setAuthority(Authority.ROOKIE);
			if (actor.getUserAccount().getAuthorities().contains(hW)) {
				final Rookie owner = finder.getRookie();
				Assert.notNull(owner);
				Assert.isTrue(actor.getId() == owner.getId());
				finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
				final Collection<Position> positionSearchedList = this.positionService.findPositionsByFinder(finder);
				finder.setPositions(positionSearchedList);
			}
		} else
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		final Finder result = this.finderRepository.save(finder);

		return result;

	}

	public Finder saveAdmin(final Finder finder) {

		final Finder result = this.finderRepository.save(finder);

		return result;
	}

	public void deletePositions(final Finder finder) {
		final Collection<Position> positions = new HashSet<Position>();
		finder.setPositions(positions);
	}

	public void deleteFinderActor(final int actorId) {

		final Finder finder = this.finderRepository.findFinderByRookie(actorId);

		this.finderRepository.delete(finder);

	}

	// Other business rules

	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		final Finder finderBBDD = this.findOne(finder.getId());

		finder.setRookie(finderBBDD.getRookie());

		finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(finder, binding);

		return finder;

	}

	public Finder findFinderByRookie(final int rookieId) {
		final Finder finder = this.finderRepository.findFinderByRookie(rookieId);
		return finder;
	}

	public Collection<Finder> findFindersByPositionId(final int positionId) {

		final Collection<Finder> result = this.finderRepository.findFindersByPositionId(positionId);

		return result;
	}

	public Boolean security(final int finderId) {
		Boolean res = false;
		Assert.notNull(finderId);

		final Rookie hackerNow = this.rookieService.findByPrincipal();

		final Finder finder = this.findOne(finderId);

		final Rookie owner = finder.getRookie();

		if (hackerNow.getId() == owner.getId())
			res = true;

		return res;
	}

	public Finder clear(final Finder finderSearched) {
		Assert.isTrue(this.security(finderSearched.getId()));

		Finder finder = finderSearched;

		final Collection<Position> positionsEmpty = new HashSet<Position>();

		finder.setPositions(positionsEmpty);

		finder = this.finderRepository.save(finder);

		return finder;

	}

}
