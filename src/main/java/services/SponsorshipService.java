
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.Authority;
import domain.Actor;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	public SponsorshipForm create(final int positionId) {

		final Provider provider = this.providerService.findByPrincipal();
		Assert.notNull(provider);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		Assert.isTrue(provider.getUserAccount().getAuthorities().contains(authority));

		final SponsorshipForm sponsorshipForm = new SponsorshipForm();

		final Position position = this.positionService.findOne(positionId);

		Assert.isTrue(position != null && position.getFinalMode());

		sponsorshipForm.setPositionId(positionId);

		return sponsorshipForm;

	}

	public Sponsorship findOne(final int sponsorshipId) {

		final Sponsorship sponsorship;
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		return sponsorship;

	}

	public Collection<Sponsorship> findAll() {

		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Sponsorship save(final Sponsorship sponsorship) {

		final Provider provider = this.providerService.findByPrincipal();
		Assert.notNull(provider);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);
		Assert.isTrue(provider.getUserAccount().getAuthorities().contains(authority));

		Assert.isTrue(sponsorship.getProvider() == provider);

		final Date now = new Date(System.currentTimeMillis() - 1000);

		Assert.isTrue(sponsorship.getCreditCard().getExpYear() - 1900 >= now.getYear());
		Assert.isTrue(sponsorship.getCreditCard().getExpMonth() - 1 >= now.getMonth() || sponsorship.getCreditCard().getExpYear() - 1900 > now.getYear());

		Assert.isTrue(sponsorship.getPosition().getFinalMode());

		final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;

	}

	public Boolean sponsorshipSponsorSecurity(final int sponsorhipId) {
		Boolean res = false;

		final Sponsorship sponsorhip = this.findOne(sponsorhipId);

		final Provider login = this.providerService.findByPrincipal();

		if (login.equals(sponsorhip.getProvider()))
			res = true;

		return res;
	}

	public Sponsorship reconstruct(final SponsorshipForm sponsorship, final BindingResult binding) {

		final Sponsorship result = new Sponsorship();

		if (!sponsorship.getBanner().equals(""))
			result.setBanner(sponsorship.getBanner());

		if (!sponsorship.getTarget().equals(""))
			result.setTarget(sponsorship.getTarget());

		result.setCreditCard(sponsorship.getCreditCard());

		if (sponsorship.getId() == 0) {

			final Position position = this.positionService.findOne(sponsorship.getPositionId());

			Assert.isTrue(position != null);

			result.setProvider(this.providerService.findByPrincipal());
			result.setPosition(position);
			result.setCost(0.0);

		} else {

			final Sponsorship theOldOne = this.findOne(sponsorship.getId());

			result.setId(theOldOne.getId());
			result.setVersion(theOldOne.getVersion());
			result.setProvider(theOldOne.getProvider());
			result.setPosition(theOldOne.getPosition());
			result.setCost(theOldOne.getCost());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {

		Assert.notNull(sponsorship);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.PROVIDER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == sponsorship.getProvider().getId());
		Assert.isTrue(sponsorship.getPosition().getFinalMode());

		this.sponsorshipRepository.delete(sponsorship);
	}

	public void deleteAdmin(final Sponsorship sponsorship) {

		this.sponsorshipRepository.delete(sponsorship);

	}

	public void deleteAll(final int actorId) {

		final Collection<Sponsorship> sponsorships = this.findAllByProviderId(actorId);

		if (!sponsorships.isEmpty())
			for (final Sponsorship s : sponsorships)
				this.sponsorshipRepository.delete(s);
	}

	public Collection<Sponsorship> findAllByProviderId(final int actorId) {

		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAllByProviderId(actorId);

		return sponsorships;
	}

	public SponsorshipForm editForm(final Sponsorship sponsorship) {

		final SponsorshipForm result = new SponsorshipForm();

		result.setBanner(sponsorship.getBanner());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setId(sponsorship.getId());
		result.setPositionId(sponsorship.getPosition().getId());
		result.setTarget(sponsorship.getTarget());
		result.setVersion(sponsorship.getVersion());

		return result;
	}

	public Sponsorship ramdomSponsorship(final int positionId) {

		Sponsorship result = null;
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAllByPositionId(positionId);

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();
		final Double fare = this.configurationService.findConfiguration().getFare();
		if (!sponsorships.isEmpty()) {

			final int M = 0;
			final int N = sponsorships.size() - 1;
			final int limit = (int) (Math.random() * (N - M + 1) + M);

			int i = 0;

			for (final Sponsorship s : sponsorships) {

				if (i == limit) {
					result = s;

					Double cost = result.getCost();

					if (fare != null)
						if (vatTax == null)
							cost = cost + fare;
						else
							cost = cost + ((1 + vatTax) * fare);

					result.setCost(cost);
					break;
				}

				i++;

			}

		}

		return result;
	}

	public void flush() {

		this.sponsorshipRepository.flush();

	}

	public Integer findSponsorshipByPositionAndProviderId(final int positionId, final int id) {

		return this.sponsorshipRepository.findSponsorshipByPositionAndProviderId(positionId, id);

	}

	public Collection<Sponsorship> findAllByPositionId(final int positionId) {

		final Collection<Sponsorship> result = this.sponsorshipRepository.findAllByPositionId(positionId);

		return result;
	}

	public Collection<Sponsorship> findAllCancelledByProviderId(final int providerId) {

		final Collection<Sponsorship> res = this.sponsorshipRepository.findAllCancelledByProviderId(providerId);

		return res;
	}
}
