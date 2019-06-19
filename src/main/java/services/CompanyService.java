
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Audit;
import domain.Company;
import domain.Position;
import forms.RegisterCompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository
	@Autowired
	private CompanyRepository	companyRepository;

	// Suporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Company create() {

		Company result;
		result = new Company();

		final UserAccount userAccount = this.userAccountService.createCompany();
		result.setUserAccount(userAccount);

		result.setSpammer(null);

		return result;

	}

	public Collection<Company> findAll() {

		Collection<Company> result;
		result = this.companyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Company findOne(final int companyId) {

		Assert.notNull(companyId);
		Company result;
		result = this.companyRepository.findOne(companyId);
		return result;
	}

	public Company save(final Company company) {
		Assert.notNull(company);
		Company result;

		if (company.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == company.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(company.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(company.getCreditCard().getExpMonth() - 1 >= now.getMonth() || company.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(company.getEmail(), false);
			this.actorService.checkPhone(company.getPhone());

			final String phone = this.actorService.checkPhone(company.getPhone());
			company.setPhone(phone);

			result = this.companyRepository.save(company);

		} else {

			String pass = company.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = company.getUserAccount();
			userAccount.setPassword(pass);

			company.setUserAccount(userAccount);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(company.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(company.getCreditCard().getExpMonth() - 1 >= now.getMonth() || company.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(company.getEmail(), false);
			this.actorService.checkPhone(company.getPhone());

			final String phone = this.actorService.checkPhone(company.getPhone());
			company.setPhone(phone);

			result = this.companyRepository.save(company);

		}
		return result;

	}
	//Other business methods ----------------------------

	public Company findByPrincipal() {
		Company company;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		company = this.findByUserAccount(userAccount);
		Assert.notNull(company);

		return company;
	}

	public Company findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Company result;

		result = this.companyRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//Recontruct para registrar Company
	public Company reconstruct(final RegisterCompanyForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Company company = this.create();

		company.setCommercialName(form.getCommercialName());
		company.setName(form.getName());
		company.setSurnames(form.getSurnames());
		company.setVat(form.getVat());
		company.setPhoto(form.getPhoto());
		company.setEmail(form.getEmail());
		company.setPhone(form.getPhone());
		company.setAddress(form.getAddress());
		company.setCreditCard(form.getCreditCard());
		company.getUserAccount().setUsername(form.getUsername());
		company.getUserAccount().setPassword(form.getPassword());

		return company;

	}

	//reconstruct para editar company

	public Company reconstruct(final Company company, final BindingResult binding) {

		final Company result;

		final Company companyBBDD = this.findOne(company.getId());

		if (companyBBDD != null) {

			company.setUserAccount(companyBBDD.getUserAccount());
			company.setSpammer(companyBBDD.getSpammer());

			this.validator.validate(company, binding);

		}
		result = company;
		return result;

	}

	public void calculateScore() {

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(admin));

		final Collection<Company> companies = this.findAll();

		if (!companies.isEmpty())
			for (final Company c : companies) {

				final Double score = this.score(c.getId());

				c.setScore(score);
				this.companyRepository.save(c);
			}

	}

	protected Double score(final int companyId) {

		Double res = null;

		final Collection<Position> positions = this.positionService.findPositionsByCompanyId(companyId);

		if (!positions.isEmpty()) {

			Double sumScore = 0.0;
			Double total = 0.0;

			for (final Position p : positions) {

				final Collection<Audit> audits = this.auditService.findFinalAuditsByPositionId(p.getId());

				if (!audits.isEmpty())
					for (final Audit a : audits) {

						sumScore = sumScore + a.getScore();

						total = total + 10;

					}

			}

			if (total == 0.0)
				res = null;
			else if (sumScore == 0.0)
				res = 0.0;
			else
				res = sumScore / total;

		}

		return res;

	}

	public void flush() {

		this.companyRepository.flush();
	}

}
