
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.Position;
import forms.RegisterAdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository ------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessageService			messageService;


	// Simple CRUD methods -----------------------

	public Administrator create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Administrator result;
		result = new Administrator();

		final UserAccount userAccount = this.userAccountService.createAdmin();
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {

		Assert.notNull(administratorId);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		Administrator result;

		if (administrator.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == administrator.getId());

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(administrator.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(administrator.getCreditCard().getExpMonth() - 1 >= now.getMonth() || administrator.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(administrator.getEmail(), true);
			this.actorService.checkPhone(administrator.getPhone());

			result = this.administratorRepository.save(administrator);

		} else {

			this.actorService.checkEmail(administrator.getEmail(), true);
			this.actorService.checkPhone(administrator.getPhone());

			String pass = administrator.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = administrator.getUserAccount();
			userAccount.setPassword(pass);

			administrator.setUserAccount(userAccount);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(administrator.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(administrator.getCreditCard().getExpMonth() - 1 >= now.getMonth() || administrator.getCreditCard().getExpYear() - 1900 > now.getYear());

			final String phone = this.actorService.checkPhone(administrator.getPhone());

			result = this.administratorRepository.save(administrator);

		}
		return result;
	}

	public Administrator reconstruct(final RegisterAdministratorForm form, final BindingResult binding) {

		final Administrator admin = this.create();

		this.validator.validate(form, binding);

		admin.setName(form.getName());
		admin.setSurnames(form.getSurnames());
		admin.setVat(form.getVat());
		admin.setPhoto(form.getPhoto());
		admin.setEmail(form.getEmail());
		admin.setCreditCard(form.getCreditCard());
		admin.setPhone(form.getPhone());
		admin.setAddress(form.getAddress());
		admin.setSpammer(null);
		admin.getUserAccount().setUsername(form.getUsername());
		admin.getUserAccount().setPassword(form.getPassword());

		return admin;

	}

	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {

		final Administrator result;

		final Administrator adminBBDD = this.findOne(admin.getId());

		if (adminBBDD != null) {

			admin.setUserAccount(adminBBDD.getUserAccount());
			admin.setSpammer(adminBBDD.getSpammer());

			this.validator.validate(admin, binding);

		}
		result = admin;

		return result;

	}

	public void flush() {
		this.administratorRepository.flush();
	}

	// Other business methods -----------------------

	public Administrator findByPrincipal() {
		Administrator admin;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		admin = this.findByUserAccount(userAccount);
		Assert.notNull(admin);

		return admin;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void spammer() {

		final Actor admin = this.actorService.findByPrincipal();

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authAdmin));

		final Collection<Actor> actors = this.actorService.findAll();

		for (final Actor actor : actors) {

			Collection<Message> messages = new HashSet<>();

			Boolean body = false;
			Boolean subject = false;
			Boolean tags = false;

			Integer intMesage = 0;
			Integer intSpam = 0;

			messages = this.messageService.messagePerActor(actor.getId());

			if (!messages.isEmpty())
				for (final Message message : messages) {

					if (message.getBody() != null)
						body = this.configurationService.spamContent(message.getBody());
					if (message.getSubject() != null)
						subject = this.configurationService.spamContent(message.getSubject());
					if (message.getTags() != null)
						tags = this.configurationService.spamContent(message.getTags());

					intMesage++;
					if (body || subject || tags)
						intSpam++;

				}

			if (!messages.isEmpty()) {
				if (intSpam >= 0.1 * intMesage)
					actor.setSpammer(true);
				else
					actor.setSpammer(false);

				this.actorService.save(actor);
			}

		}
	}

	// Dashboard

	public Double avgOfPositionsPerCompany() {
		return this.administratorRepository.avgOfPositionsPerCompany();
	}

	public Integer minOfPositionsPerCompany() {
		return this.administratorRepository.minPositionOfCompany();
	}

	public Integer maxOfPositionsPerCompany() {
		return this.administratorRepository.maxPositionOfCompany();
	}

	public Double stdOfPositionsPerCompany() {
		return this.administratorRepository.stdOfPositionsPerCompany();
	}

	public Double avgOfApplicationsPerRookie() {
		return this.administratorRepository.avgOfApplicationsPerRookie();
	}

	public Integer minOfApplicationsPerRookie() {
		return this.administratorRepository.minApplicationsOfRookie();
	}

	public Integer maxOfApplicationsPerRookie() {
		return this.administratorRepository.maxApplicationsOfRookie();
	}

	public Double stdOfApplicationsPerRookie() {
		return this.administratorRepository.stdOfApplicationsPerRookie();
	}

	public List<String> topCompaniesWithMorePositions() {
		final List<String> result = this.administratorRepository.topCompaniesWithMorePositions();

		if (result.size() == 0)
			result.add(" N/A ");

		return result;
	}

	public List<String> topRookiesWithMoreApplications() {
		final List<String> result = this.administratorRepository.topRookieWithMoreApplications();

		if (result.size() == 0)
			result.add(" N/A ");

		return result;
	}

	public Double avgSalaries() {
		return this.administratorRepository.avgSalaries();
	}

	public Integer minSalary() {
		return this.administratorRepository.minSalary();
	}

	public Integer maxSalary() {
		return this.administratorRepository.maxSalary();
	}

	public Double stdSalaries() {
		return this.administratorRepository.stdSalaries();
	}

	public Position findBestPosition() {

		Position result = null;

		try {
			final int id = this.administratorRepository.findBestPosition();
			result = this.positionService.findOne(id);
		} catch (final Exception e) {
			return null;
		}

		return result;
	}

	public Position findWorstPosition() {
		Position result = null;
		try {
			final int id = this.administratorRepository.findWorstPosition();
			result = this.positionService.findOne(id);
		} catch (final Exception e) {
			return null;
		}
		return result;
	}

	public Integer minNumberOfCurriculaPerRookie() {
		return this.administratorRepository.minNumberOfCurriculaPerRookie();
	}

	public Integer maxNumberOfCurriculaPerRookie() {
		return this.administratorRepository.maxNumberOfCurriculaPerRookie();
	}

	public Double avgNumberOfCurriculaPerRookie() {
		return this.administratorRepository.avgNumberOfCurriculaPerRookie();
	}

	public Double stdNumberOfCurriculaPerRookie() {
		return this.administratorRepository.stdNumberOfCurriculaPerRookie();
	}

	public Integer minNumberOfResultsInFinders() {
		return this.administratorRepository.minNumberOfResultsInFinders();
	}

	public Integer maxNumberOfResultsInFinders() {
		return this.administratorRepository.maxNumberOfResultsInFinders();
	}

	public Double avgNumberOfResultsInFinders() {
		return this.administratorRepository.avgNumberOfResultsInFinders();
	}

	public Double stdNumberOfResultsInFinders() {
		return this.administratorRepository.stdNumberOfCurriculaPerRookie();
	}

	public Double ratioEmptyNotEmptyFinders() {
		return this.administratorRepository.ratioEmptyNotEmptyFinders();
	}

	// --------- Acme-Rookie ----------

	public Double avgScorePositions() {
		return this.administratorRepository.avgScorePositions();
	}

	public Double minScorePositions() {
		return this.administratorRepository.minScorePositions();
	}

	public Double maxScorePositions() {
		return this.administratorRepository.maxScorePositions();
	}

	public Double stdScorePositions() {
		return this.administratorRepository.stdScorePositions();
	}

	public Double avgScoreCompany() {
		return this.administratorRepository.avgScoreCompany();
	}

	public Double minScoreCompany() {
		return this.administratorRepository.minScoreCompany();
	}

	public Double maxScoreCompany() {
		return this.administratorRepository.maxScoreCompany();
	}

	public Double stdScoreCompany() {
		return this.administratorRepository.stdScoreCompany();
	}

	public List<String> topScoreCompany() {
		final List<String> result = this.administratorRepository.topScoreCompany();
		if (result.size() == 0)
			result.add(" N/A ");
		return result;
	}

	public Double avgSalaryTopCompany() {
		return this.administratorRepository.avgSalaryTopCompany();
	}

	public Double minItemPerProvider() {
		return this.administratorRepository.minItemPerProvider();
	}

	public Double maxItemPerProvider() {
		return this.administratorRepository.maxItemPerProvider();
	}

	public Double avgItemPerProvider() {
		return this.administratorRepository.avgItemPerProvider();
	}

	public Double stdItemPerProvider() {
		return this.administratorRepository.stdItemPerProvider();
	}

	public List<String> top5Provider() {
		final List<String> result = this.administratorRepository.top5Provider();
		if (result.size() == 0)
			result.add(" N/A ");
		return result;
	}

	public Double avgSponsorshipPerProvider() {
		return this.administratorRepository.avgSponsorshipPerProvider();
	}

	public Double minSponsorshipPerProvider() {
		return this.administratorRepository.minSponsorshipPerProvider();
	}

	public Double maxSponsorshipPerProvider() {
		return this.administratorRepository.maxSponsorshipPerProvider();
	}

	public Double stdSponsorshipPerProvider() {
		return this.administratorRepository.stdSponsorshipPerProvider();
	}

	public Double avgSponsorshipPerPosition() {
		return this.administratorRepository.avgSponsorshipPerPosition();
	}

	public Double minSponsorshipPerPosition() {
		return this.administratorRepository.minSponsorshipPerPosition();
	}

	public Double maxSponsorshipPerPosition() {
		return this.administratorRepository.maxSponsorshipPerPosition();
	}

	public Double stdSponsorshipPerPosition() {
		return this.administratorRepository.stdSponsorshipPerPosition();
	}

	public List<String> superiorProviders() {
		final List<String> result = this.administratorRepository.superiorProviders();
		if (result.size() == 0)
			result.add(" N/A ");
		return result;
	}

}
