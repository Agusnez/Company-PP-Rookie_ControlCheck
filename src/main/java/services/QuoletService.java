
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	// Managed Repository ------------------------
	@Autowired
	private QuoletRepository	quoletRepository;

	// Supporting Services -----------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods

	public Quolet create(final int applicationId) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Application app = this.applicationService.findOne(applicationId);

		//		final Date currentMoment = new Date(System.currentTimeMillis());
		//		final String ticker = this.generateTicker(currentMoment);

		Quolet result;

		result = new Quolet();

		result.setApplication(app);
		//		result.setTicker(ticker);

		return result;

	}

	public Quolet findOne(final int quoletId) {

		final Quolet quolet = this.quoletRepository.findOne(quoletId);

		return quolet;
	}

	public Collection<Quolet> findAll() {

		final Collection<Quolet> quolets = this.quoletRepository.findAll();

		return quolets;
	}

	public Quolet save(final Quolet quolet) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Quolet result;

		if (quolet.getId() != 0) {

			final Quolet quoletBBDD = this.findOne(quolet.getId());

			Assert.isTrue(!quoletBBDD.getFinalMode());

			if (quolet.getFinalMode()) {
				final Date moment = new Date(System.currentTimeMillis() - 1000);
				quolet.setPublicationMoment(moment);
			} else
				Assert.isNull(quolet.getPublicationMoment());

			result = this.quoletRepository.save(quolet);
		} else {

			if (quolet.getFinalMode()) {
				final Date moment = new Date(System.currentTimeMillis() - 1000);
				quolet.setPublicationMoment(moment);
			} else
				Assert.isNull(quolet.getPublicationMoment());

			final Date currentMoment = new Date(System.currentTimeMillis());
			final String ticker = this.generateTicker(currentMoment);
			quolet.setTicker(ticker);

			result = this.quoletRepository.save(quolet);
		}

		Assert.notNull(result);

		return result;

	}

	public void delete(final Quolet quolet) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.notNull(quolet);
		Assert.isTrue(quolet.getId() != 0);
		Assert.isTrue(quolet.getFinalMode() == false);

		this.quoletRepository.delete(quolet);

	}

	//Other Business Methods

	private String generateTicker(final Date currentMoment) {

		final DateFormat dateFormatYear = new SimpleDateFormat("yy");
		final String dateStringYear = dateFormatYear.format(currentMoment);

		final DateFormat dateFormatMonth = new SimpleDateFormat("MM");
		final String dateStringMonth = dateFormatYear.format(currentMoment);

		final DateFormat dateFormatDay = new SimpleDateFormat("dd");
		final String dateStringDay = dateFormatYear.format(currentMoment);

		final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String numeric = "1234567890";

		final StringBuilder salt1 = new StringBuilder();
		final StringBuilder salt2 = new StringBuilder();
		final Random rnd = new Random();
		while (salt1.length() < 2) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alpha.length());
			salt1.append(alpha.charAt(index));
		}

		while (salt2.length() < 5) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * numeric.length());
			salt2.append(numeric.charAt(index));
		}

		final String randomAlphaNumeric1 = salt1.toString();
		final String randomAlphaNumeric2 = salt2.toString();

		final String ticker = dateStringYear + ":" + randomAlphaNumeric1 + ":" + dateStringMonth + ":" + randomAlphaNumeric2 + ":" + dateStringDay;

		return ticker;

	}

	//TODO CAMBIO CONTROL: cambiar nombre nueva clase
	public Boolean quoletSecurityCompany(final int quoletId) {
		Boolean res = false;

		final Quolet quolet = this.findOne(quoletId);

		final Company company = this.companyService.findByPrincipal();

		final Application applicationForQuolet = quolet.getApplication();

		if (applicationForQuolet.getProblem().getCompany().getId() == company.getId())
			res = true;

		return res;
	}

	public Collection<Quolet> quoletsPerApplicationId(final int applicationId) {

		final Collection<Quolet> res = this.quoletRepository.quoletsPerApplicationId(applicationId);

		return res;
	}

	public Boolean existQuolet(final int quoletId) {
		Boolean res = false;

		final Quolet quolet = this.findOne(quoletId);

		if (quolet != null)
			res = true;

		return res;

	}

	public Boolean existQuoletPost(final Quolet quolet) {
		Boolean res = false;

		if ((quolet.getId() != 0 && this.findOne(quolet.getId()) != null) || quolet.getApplication() != null || (quolet.getApplication() != null && this.applicationService.existApplication(quolet.getApplication().getId())))
			res = true;

		return res;
	}

	public Quolet reconstruct(final Quolet quolet, final BindingResult binding) {

		Quolet result = quolet;
		Assert.notNull(quolet);
		final Quolet quoletNew = this.create(quolet.getApplication().getId());

		if (quolet.getId() == 0) {

			quolet.setCompany(quoletNew.getCompany());
			quolet.setFinalMode(false);

			this.validator.validate(quolet, binding);

			result = quolet;
		} else {

			final Quolet quoletBBDD = this.findOne(quolet.getId());

			quolet.setCompany(quoletBBDD.getCompany());

			this.validator.validate(quolet, binding);

			result = quolet;

		}

		return result;

	}

}
