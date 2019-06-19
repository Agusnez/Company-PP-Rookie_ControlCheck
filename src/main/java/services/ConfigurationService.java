
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import security.Authority;
import domain.Administrator;
import domain.Configuration;
import domain.Message;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private Validator				validator;


	//Methods

	public Configuration save(final Configuration c) {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.save(c);

		Assert.notNull(configuration);

		return configuration;
	}

	public Configuration findOne(final int configurationId) {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.findOne(configurationId);

		Assert.notNull(configuration);

		return configuration;
	}

	public Collection<Configuration> findAll() {

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Collection<Configuration> configurations = this.configurationRepository.findAll();

		Assert.notNull(configurations);

		return configurations;
	}

	public Configuration findConfiguration() {

		Configuration config;
		config = this.configurationRepository.findAll().get(0);
		Assert.notNull(config);
		return config;
	}

	public void spammerDetector(final int actorId) {

		final Collection<Message> messages = this.messageService.messagePerActor(actorId);

		int total = messages.size();

		if (total == 0)
			total = 1;

		int ac = 0;

		for (final Message m : messages)
			if (m.getSpam() == true)
				ac++;

		final Double r = (ac / total) * 1.0;
		if (r >= 0.1)
			this.actorService.convertToSpammerActor();

	}

	public Boolean spamContent(final String text) {

		Boolean result = false;
		if (!text.isEmpty() && text != null) {
			final Configuration config = this.findConfiguration();

			final Collection<String> spamWords = config.getSpamWords();

			if (!spamWords.isEmpty())
				for (final String word : spamWords)
					if (text.toLowerCase().contains(word.toLowerCase())) {
						result = true;
						break;
					}

		}

		return result;
	}

	public Boolean exist(final int id) {

		Boolean result = false;

		final Configuration c = this.configurationRepository.findOne(id);

		if (c != null)
			result = true;

		return result;
	}

	public Configuration reconstruct(final Configuration config, final BindingResult binding) {

		final Configuration bbdd = this.findOne(config.getId());

		final Configuration result = config;
		config.setId(bbdd.getId());
		config.setVersion(config.getVersion());
		config.setRebrandingNotification(bbdd.getRebrandingNotification());

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.configurationRepository.flush();
	}
}
