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

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Finder;
import domain.Provider;
import forms.RegisterProviderForm;

@Service
@Transactional
public class ProviderService {

	// Managed Repository ------------------------
		@Autowired
		private ProviderRepository	providerRepository;
		
	// Suporting services
		@Autowired
		private ActorService		actorService;

		@Autowired
		private UserAccountService	userAccountService;
		
		@Autowired
		private Validator			validator;
		
		
	// Simple CRUD methods

		public Provider create() {

			Provider result;
			result = new Provider();

			final UserAccount userAccount = this.userAccountService.createProvider();
			result.setUserAccount(userAccount);

			result.setSpammer(null);

			return result;

		}
		
		public Collection<Provider> findAll() {

			Collection<Provider> result;
			result = this.providerRepository.findAll();
			Assert.notNull(result);
			return result;
		}

		public Provider findOne(final int providerId) {

			Assert.notNull(providerId);
			Provider result;
			result = this.providerRepository.findOne(providerId);
			return result;
		}

		public Provider save(final Provider provider) {
			Assert.notNull(provider);
			Provider result;

			if (provider.getId() != 0) {
				final Authority admin = new Authority();
				admin.setAuthority(Authority.ADMIN);

				final Actor actor = this.actorService.findByPrincipal();
				Assert.notNull(actor);

				Assert.isTrue(actor.getId() == provider.getId() || actor.getUserAccount().getAuthorities().contains(admin));

				final Date now = new Date(System.currentTimeMillis() - 1000);

				Assert.isTrue(provider.getCreditCard().getExpYear() - 1900 >= now.getYear());
				Assert.isTrue(provider.getCreditCard().getExpMonth() - 1 >= now.getMonth() || provider.getCreditCard().getExpYear() - 1900 > now.getYear());

				this.actorService.checkEmail(provider.getEmail(), false);
				this.actorService.checkPhone(provider.getPhone());

				final String phone = this.actorService.checkPhone(provider.getPhone());
				provider.setPhone(phone);

				result = this.providerRepository.save(provider);

			} else {

				String pass = provider.getUserAccount().getPassword();

				final Md5PasswordEncoder code = new Md5PasswordEncoder();

				pass = code.encodePassword(pass, null);

				final UserAccount userAccount = provider.getUserAccount();
				userAccount.setPassword(pass);

				provider.setUserAccount(userAccount);

				final Date now = new Date(System.currentTimeMillis() - 1000);

				Assert.isTrue(provider.getCreditCard().getExpYear() - 1900 >= now.getYear());
				Assert.isTrue(provider.getCreditCard().getExpMonth() - 1 >= now.getMonth() || provider.getCreditCard().getExpYear() - 1900 > now.getYear());

				this.actorService.checkEmail(provider.getEmail(), false);
				this.actorService.checkPhone(provider.getPhone());

				final String phone = this.actorService.checkPhone(provider.getPhone());
				provider.setPhone(phone);

				result = this.providerRepository.save(provider);


			}
			return result;

		}
		//Other business methods ----------------------------

		public Provider findByPrincipal() {
			Provider provider;
			UserAccount userAccount;

			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			provider = this.findByUserAccount(userAccount);
			Assert.notNull(provider);

			return provider;
		}

		public Provider findByUserAccount(final UserAccount userAccount) {
			Assert.notNull(userAccount);

			Provider result;

			result = this.providerRepository.findByUserAccountId(userAccount.getId());

			return result;
		}

		//Recontruct para registrar Provider
		public Provider reconstruct(final RegisterProviderForm form, final BindingResult binding) {

			this.validator.validate(form, binding);

			final Provider provider = this.create();

			provider.setProviderMake(form.getProviderMake());
			provider.setName(form.getName());
			provider.setSurnames(form.getSurnames());
			provider.setVat(form.getVat());
			provider.setPhoto(form.getPhoto());
			provider.setEmail(form.getEmail());
			provider.setPhone(form.getPhone());
			provider.setAddress(form.getAddress());
			provider.setCreditCard(form.getCreditCard());
			provider.getUserAccount().setUsername(form.getUsername());
			provider.getUserAccount().setPassword(form.getPassword());

			return provider;

		}

		//Reconstruct para editar Provider

		public Provider reconstruct(final Provider provider, final BindingResult binding) {

			final Provider result;

			final Provider providerBBDD = this.findOne(provider.getId());

			if (providerBBDD != null) {

				provider.setUserAccount(providerBBDD.getUserAccount());
				provider.setSpammer(providerBBDD.getSpammer());

				this.validator.validate(provider, binding);

			}
			result = provider;
			return result;

		}

		public void flush() {
			this.providerRepository.flush();
		}
}
