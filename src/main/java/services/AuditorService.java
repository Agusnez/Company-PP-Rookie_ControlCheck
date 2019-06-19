
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Auditor;
import forms.RegisterAuditorForm;

@Service
@Transactional
public class AuditorService {

	// Managed Repository ------------------------
	@Autowired
	private AuditorRepository	auditorRepository;

	// Suporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	public Auditor create() {

		Auditor result;
		result = new Auditor();

		final UserAccount userAccount = this.userAccountService.createAuditor();
		result.setUserAccount(userAccount);

		result.setSpammer(null);
		result.setPositions(null);

		return result;

	}

	public Collection<Auditor> findAll() {

		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Auditor findOne(final int auditorId) {

		Assert.notNull(auditorId);
		Auditor result;
		result = this.auditorRepository.findOne(auditorId);
		return result;
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);
		Auditor result;
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		if (auditor.getId() != 0) {

			Assert.isTrue(actor.getId() == auditor.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(auditor.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(auditor.getCreditCard().getExpMonth() - 1 >= now.getMonth() || auditor.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(auditor.getEmail(), false);
			this.actorService.checkPhone(auditor.getPhone());

			final String phone = this.actorService.checkPhone(auditor.getPhone());
			auditor.setPhone(phone);

			result = this.auditorRepository.save(auditor);

		} else {

			Assert.isTrue(actor.getUserAccount().getAuthorities().contains(admin));

			String pass = auditor.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = auditor.getUserAccount();
			userAccount.setPassword(pass);

			auditor.setUserAccount(userAccount);

			final Date now = new Date(System.currentTimeMillis() - 1000);

			Assert.isTrue(auditor.getCreditCard().getExpYear() - 1900 >= now.getYear());
			Assert.isTrue(auditor.getCreditCard().getExpMonth() - 1 >= now.getMonth() || auditor.getCreditCard().getExpYear() - 1900 > now.getYear());

			this.actorService.checkEmail(auditor.getEmail(), false);
			this.actorService.checkPhone(auditor.getPhone());

			final String phone = this.actorService.checkPhone(auditor.getPhone());
			auditor.setPhone(phone);

			result = this.auditorRepository.save(auditor);

		}
		return result;

	}

	public Auditor saveAdmin(final Auditor auditor) {

		final Auditor result = this.auditorRepository.save(auditor);

		return result;

	}

	public Auditor findByPrincipal() {
		Auditor auditor;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		auditor = this.findByUserAccount(userAccount);
		Assert.notNull(auditor);

		return auditor;
	}

	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Auditor result;

		result = this.auditorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//Recontruct para registrar Auditor
	public Auditor reconstruct(final RegisterAuditorForm form, final BindingResult binding) {

		this.validator.validate(form, binding);

		final Auditor auditor = this.create();

		auditor.setName(form.getName());
		auditor.setSurnames(form.getSurnames());
		auditor.setVat(form.getVat());
		auditor.setPhoto(form.getPhoto());
		auditor.setEmail(form.getEmail());
		auditor.setPhone(form.getPhone());
		auditor.setAddress(form.getAddress());
		auditor.setCreditCard(form.getCreditCard());
		auditor.getUserAccount().setUsername(form.getUsername());
		auditor.getUserAccount().setPassword(form.getPassword());

		return auditor;

	}

	//Reconstruct para editar Auditor

	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {

		final Auditor result;

		final Auditor auditorBBDD = this.findOne(auditor.getId());

		if (auditorBBDD != null) {

			auditor.setUserAccount(auditorBBDD.getUserAccount());
			auditor.setSpammer(auditorBBDD.getSpammer());
			auditor.setPositions(auditorBBDD.getPositions());

			this.validator.validate(auditor, binding);

		}
		result = auditor;
		return result;

	}

	public Collection<Auditor> findAuditorByPositionId(final int positionId) {
		final Collection<Auditor> result = this.auditorRepository.findAuditorByPositionId(positionId);

		return result;
	}

	public void flush() {
		this.auditorRepository.flush();
	}
}
