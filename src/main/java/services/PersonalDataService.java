
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.Rookie;
import domain.PersonalData;
import forms.CreateCurriculumForm;
import forms.PersonalDataForm;

@Service
@Transactional
public class PersonalDataService {

	// Managed Repository ------------------------
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CurriculumService		curriculumService;


	// Simple CRUD methods -----------------------

	public PersonalData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		PersonalData result;
		result = new PersonalData();

		return result;
	}

	public Collection<PersonalData> findAll() {

		Collection<PersonalData> result;

		result = this.personalDataRepository.findAll();

		return result;
	}

	public PersonalData findOne(final int personalId) {

		Assert.notNull(personalId);
		PersonalData result;
		result = this.personalDataRepository.findOne(personalId);
		return result;
	}

	public PersonalData save(final PersonalData personal) {

		Assert.notNull(personal);

		PersonalData result;

		result = this.personalDataRepository.save(personal);

		return result;

	}

	public void delete(final PersonalData personal) {

		Assert.notNull(personal);

		this.personalDataRepository.delete(personal);

	}

	public PersonalData reconstruct(final CreateCurriculumForm form, final BindingResult binding) {

		final Actor actor = this.actorService.findByPrincipal();

		final String fullName = actor.getName() + " " + actor.getSurnames();

		final PersonalData result = this.create();

		this.validator.validate(form, binding);

		result.setFullName(fullName);
		result.setStatement(form.getStatement());
		result.setPhone(form.getPhone());
		result.setLinkGitHubProfile(form.getLinkGitHubProfile());
		result.setLinkLinkedInProfile(form.getLinkLinkedInProfile());

		return result;

	}

	public PersonalData reconstruct(final PersonalDataForm form, final BindingResult binding) {

		final Actor actor = this.actorService.findByPrincipal();

		final String fullName = actor.getName() + " " + actor.getSurnames();

		final PersonalData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setFullName(fullName);
		result.setStatement(form.getStatement());
		result.setPhone(form.getPhone());
		result.setLinkGitHubProfile(form.getLinkGitHubProfile());
		result.setLinkLinkedInProfile(form.getLinkLinkedInProfile());

		return result;

	}

	public Boolean exist(final int personalDataId) {
		Boolean res = false;

		final PersonalData find = this.personalDataRepository.findOne(personalDataId);

		if (find != null)
			res = true;

		return res;
	}

	public PersonalDataForm createForm(final int personalDataId) {

		final PersonalData personal = this.findOne(personalDataId);

		final PersonalDataForm result = new PersonalDataForm();

		result.setId(personal.getId());
		result.setVersion(personal.getVersion());
		result.setStatement(personal.getStatement());
		result.setPhone(personal.getPhone());
		result.setLinkGitHubProfile(personal.getLinkGitHubProfile());
		result.setLinkLinkedInProfile(personal.getLinkLinkedInProfile());

		final Curriculum c = this.curriculumService.findByPersonalDataId(personalDataId);

		if (c != null)
			result.setCurriculumId(c.getId());

		return result;
	}

	public Boolean security(final int personalDataId) {

		Boolean result = false;

		final Rookie rookie = this.rookieService.findByPrincipal();

		final Curriculum c = this.curriculumService.findByPersonalDataId(personalDataId);

		final Collection<Curriculum> curricula = this.curriculumService.findByRookieId(rookie.getId());

		if (curricula.contains(c))
			result = true;

		return result;
	}

	public void flush() {
		this.personalDataRepository.flush();
	}
}
