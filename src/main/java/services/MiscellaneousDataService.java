
package services;

import java.net.URL;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.MiscellaneousData;
import domain.Rookie;
import forms.MiscellaneousDataForm;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private Validator					validator;


	// Simple CRUD methods -----------------------

	public MiscellaneousData create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		MiscellaneousData result;
		result = new MiscellaneousData();

		return result;
	}

	public Collection<MiscellaneousData> findAll() {

		Collection<MiscellaneousData> result;

		result = this.miscellaneousDataRepository.findAll();

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousId) {

		Assert.notNull(miscellaneousId);
		MiscellaneousData result;
		result = this.miscellaneousDataRepository.findOne(miscellaneousId);
		return result;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneous) {

		Assert.notNull(miscellaneous);

		MiscellaneousData result;

		this.checkPictures(miscellaneous.getAttachments());

		result = this.miscellaneousDataRepository.save(miscellaneous);

		return result;

	}

	public void delete(final MiscellaneousData miscellaneous) {

		Assert.notNull(miscellaneous);

		Assert.isTrue(miscellaneous.getId() != 0);

		this.miscellaneousDataRepository.delete(miscellaneous);

	}

	public MiscellaneousData reconstruct(final MiscellaneousDataForm form, final BindingResult binding) {

		final MiscellaneousData result = this.create();

		this.validator.validate(form, binding);

		result.setId(form.getId());
		result.setVersion(form.getVersion());
		result.setText(form.getText());
		result.setAttachments(form.getAttachments());

		return result;

	}

	public void checkPictures(final Collection<String> attachments) {

		for (final String url : attachments)
			try {
				new URL(url);
			} catch (final Exception e) {
				throw new DataIntegrityViolationException("Invalid URL");
			}
	}

	public Boolean exist(final int miscellaneousId) {

		Boolean res = false;

		if (miscellaneousId != 0) {
			final MiscellaneousData miscellaneous = this.miscellaneousDataRepository.findOne(miscellaneousId);

			if (miscellaneous != null)
				res = true;
		} else
			res = true;

		return res;
	}

	public Boolean security(final int miscellaneousId, final int curriculumId) {

		Boolean res = false;

		if (miscellaneousId != 0 && this.curriculumService.security(curriculumId)) {
			final Curriculum c = this.curriculumService.findOne(curriculumId);

			final MiscellaneousData miscellaneous = this.findOne(miscellaneousId);

			if (c.getMiscellaneousDatas().contains(miscellaneous))
				res = true;

		} else
			res = true;

		return res;
	}

	public Boolean security(final int miscellaneousRecordId) {

		Boolean result = false;

		final Rookie rookie = this.rookieService.findByPrincipal();

		final Collection<Curriculum> curricula = this.curriculumService.findByRookieId(rookie.getId());

		final MiscellaneousData miscellaneous = this.findOne(miscellaneousRecordId);

		for (final Curriculum c : curricula)
			if (!c.getMiscellaneousDatas().isEmpty())
				if (c.getMiscellaneousDatas().contains(miscellaneous)) {
					result = true;
					break;
				}

		return result;
	}

	public MiscellaneousDataForm creteForm(final int miscellaneousRecordId) {

		final MiscellaneousData miscellaneousData = this.findOne(miscellaneousRecordId);

		final MiscellaneousDataForm result = new MiscellaneousDataForm();

		result.setId(miscellaneousData.getId());
		result.setVersion(miscellaneousData.getVersion());
		result.setText(miscellaneousData.getText());
		result.setAttachments(miscellaneousData.getAttachments());

		final Curriculum c = this.curriculumService.findByMiscellaneousDataId(miscellaneousRecordId);

		if (c != null)
			result.setCurriculumId(c.getId());

		return result;

	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}
}
