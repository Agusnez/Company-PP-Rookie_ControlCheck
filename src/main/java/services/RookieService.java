
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

import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Finder;
import domain.Rookie;
import forms.RegisterRookieForm;

@Service
@Transactional
public class RookieService {

	// Managed repository
	@Autowired
	private RookieRepository	rookieRepository;

	// Suporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Rookie create() {

		Rookie result;
		result = new Rookie();

		final UserAccount userAccount = this.userAccountService.createRookie();
		result.setUserAccount(userAccount);

		result.setSpammer(null);

		return result;

	}

	public Collection<Rookie> findAll() {

		Collection<Rookie> result;
		result = this.rookieRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Rookie findOne(final int rookieId) {

		Assert.notNull(rookieId);
		Rookie result;
		result = this.rookieRepository.findOne(rookieId);
		return result;
	}

	public Rookie save(final Rookie rookie) {
		Assert.notNull(rookie);
		Rookie result;

		if (rookie.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == rookie.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(rookie.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(rookie.getCreditCard().getExpMonth() - 1 >= now.getMonth() || rookie.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(rookie.getEmail(), false);
			this.actorService.checkPhone(rookie.getPhone());

			final String phone = this.actorService.checkPhone(rookie.getPhone());
			rookie.setPhone(phone);

			result = this.rookieRepository.save(rookie);

		} else {

			String pass = rookie.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = rookie.getUserAccount();
			userAccount.setPassword(pass);

			rookie.setUserAccount(userAccount);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(rookie.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(rookie.getCreditCard().getExpMonth() - 1 >= now.getMonth() || rookie.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(rookie.getEmail(), false);
			this.actorService.checkPhone(rookie.getPhone());

			final String phone = this.actorService.checkPhone(rookie.getPhone());
			rookie.setPhone(phone);

			result = this.rookieRepository.save(rookie);

			final Finder finder = this.finderService.create();
			finder.setRookie(result);
			this.finderService.save(finder);

		}
		return result;

	}
	//Other business methods ----------------------------

	public Rookie findByPrincipal() {
		Rookie rookie;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		rookie = this.findByUserAccount(userAccount);
		Assert.notNull(rookie);

		return rookie;
	}

	public Rookie findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Rookie result;

		result = this.rookieRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//Recontruct para registrar Rookie
	public Rookie reconstruct(final RegisterRookieForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Rookie rookie = this.create();

		rookie.setName(form.getName());
		rookie.setSurnames(form.getSurnames());
		rookie.setVat(form.getVat());
		rookie.setPhoto(form.getPhoto());
		rookie.setEmail(form.getEmail());
		rookie.setPhone(form.getPhone());
		rookie.setAddress(form.getAddress());
		rookie.setCreditCard(form.getCreditCard());
		rookie.getUserAccount().setUsername(form.getUsername());
		rookie.getUserAccount().setPassword(form.getPassword());

		return rookie;

	}

	//Reconstruct para editar Rookie

	public Rookie reconstruct(final Rookie rookie, final BindingResult binding) {

		final Rookie result;

		final Rookie rookieBBDD = this.findOne(rookie.getId());

		if (rookieBBDD != null) {

			rookie.setUserAccount(rookieBBDD.getUserAccount());
			rookie.setSpammer(rookieBBDD.getSpammer());

			this.validator.validate(rookie, binding);

		}
		result = rookie;
		return result;

	}

	public void flush() {
		this.rookieRepository.flush();
	}

}
