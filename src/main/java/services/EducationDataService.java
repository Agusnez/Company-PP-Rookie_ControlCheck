
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationData;
import domain.Rookie;
import forms.EducationDataForm;

@Service
@Transactional
public class EducationDataService {

	// Managed Repository ------------------------
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods -----------------------

	public EducationData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		EducationData result;
		result = new EducationData();

		return result;
	}

	public Collection<EducationData> findAll() {

		Collection<EducationData> result;

		result = this.educationDataRepository.findAll();

		return result;
	}

	public EducationData findOne(final int educationId) {

		Assert.notNull(educationId);
		EducationData result;
		result = this.educationDataRepository.findOne(educationId);
		return result;
	}

	public EducationData save(final EducationData education) {

		Assert.notNull(education);

		EducationData result;

		final Date startDate = education.getStartDate();
		final Date endDate = education.getEndDate();

		if (endDate != null)
			Assert.isTrue(startDate.before(endDate));

		result = this.educationDataRepository.save(education);

		return result;

	}

	public void delete(final EducationData education) {

		Assert.notNull(education);

		Assert.isTrue(education.getId() != 0);

		this.educationDataRepository.delete(education);

	}

	public EducationData reconstruct(final EducationDataForm form, final BindingResult binding) {

		final EducationData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setDegree(form.getDegree());
		result.setInstitution(form.getInstitution());
		result.setMark(form.getMark());
		result.setStartDate(form.getStartDate());
		result.setEndDate(form.getEndDate());

		return result;

	}

	public Boolean exist(final int educationId) {

		Boolean res = false;

		final EducationData education = this.educationDataRepository.findOne(educationId);

		if (education != null)
			res = true;

		return res;
	}

	public Boolean security(final int educationId, final int curriculumId) {

		Boolean res = false;

		if (educationId != 0 && this.curriculumService.security(curriculumId)) {
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			final EducationData education = this.findOne(educationId);

			if (curriculum.getEducationDatas().contains(education))
				res = true;
		} else
			res = true;

		return res;
	}

	public EducationDataForm creteForm(final int educationRecordId) {

		final EducationData educationData = this.findOne(educationRecordId);

		final EducationDataForm result = new EducationDataForm();

		result.setId(educationData.getId());
		result.setVersion(educationData.getVersion());
		result.setDegree(educationData.getDegree());
		result.setInstitution(educationData.getInstitution());
		result.setMark(educationData.getMark());
		result.setStartDate(educationData.getStartDate());
		result.setEndDate(educationData.getEndDate());

		final Curriculum c = this.curriculumService.findByEducationDataId(educationRecordId);

		if (c != null)
			result.setCurriculumId(c.getId());

		return result;

	}

	public Boolean security(final int educationRecordId) {

		Boolean result = false;

		final Rookie rookie = this.rookieService.findByPrincipal();

		final Collection<Curriculum> curricula = this.curriculumService.findByRookieId(rookie.getId());

		final EducationData education = this.findOne(educationRecordId);

		for (final Curriculum c : curricula)
			if (!c.getEducationDatas().isEmpty())
				if (c.getEducationDatas().contains(education)) {
					result = true;
					break;
				}

		return result;
	}

	public void flush() {
		this.educationDataRepository.flush();
	}
}
