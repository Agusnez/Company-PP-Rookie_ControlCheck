
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curriculum;
import domain.EducationData;
import domain.Rookie;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;
import forms.CreateCurriculumForm;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository ------------------------
	@Autowired
	private CurriculumRepository		curriculumRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private RookieService				rookieService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private CompanyService				companyService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;


	// Simple CRUD methods -----------------------

	public Curriculum create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Curriculum result;
		result = new Curriculum();

		return result;
	}

	public Collection<Curriculum> findAll() {

		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(final int curriculumId) {

		Assert.notNull(curriculumId);
		Curriculum result;
		result = this.curriculumRepository.findOne(curriculumId);
		return result;
	}

	public Curriculum save(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		Curriculum result;

		result = this.curriculumRepository.save(curriculum);

		return result;

	}

	public void delete(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		this.curriculumRepository.delete(curriculum);

	}

	public void deleteAll(final int actorId) {

		final Collection<Curriculum> curricula = this.findAllByRookieId(actorId);

		if (!curricula.isEmpty())
			for (final Curriculum c : curricula)
				this.curriculumRepository.delete(c);
	}

	public Curriculum reconstruct(final CreateCurriculumForm form, final BindingResult binding) {

		final Rookie actor = this.rookieService.findByPrincipal();

		final Date currentMoment = new Date(System.currentTimeMillis());

		final String ticker = this.generateTicker(currentMoment);

		final Curriculum result = this.create();

		result.setNoCopy(true);
		result.setTicker(ticker);
		result.setRookie(actor);

		return result;

	}
	//Other methods

	public Collection<Curriculum> findByRookieId(final int rookieId) {

		final Collection<Curriculum> result = this.curriculumRepository.findByRookieId(rookieId);

		return result;
	}

	public Collection<Curriculum> findAllByRookieId(final int rookieId) {

		final Collection<Curriculum> result = this.curriculumRepository.findAllByRookieId(rookieId);

		return result;
	}

	public Boolean security(final int curriculumId) {

		Boolean res = false;

		if (curriculumId != 0) {
			final Rookie rookie = this.rookieService.findByPrincipal();

			final Curriculum curriculum = this.findOne(curriculumId);

			final Collection<Curriculum> curriculums = this.findAllByRookieId(rookie.getId());

			if (curriculums.contains(curriculum))
				res = true;
		}

		return res;
	}

	public Boolean exist(final int curriculumId) {

		Boolean res = false;

		final Curriculum curriculum = this.curriculumRepository.findOne(curriculumId);

		if (curriculum != null)
			res = true;

		return res;
	}

	public Curriculum copyCurriculum(final Curriculum curriculum) {

		final Curriculum res = new Curriculum();

		final Collection<EducationData> educationData = curriculum.getEducationDatas();
		final Collection<EducationData> educationDataCopy = new HashSet<>();

		for (final EducationData educationData2 : educationData) {

			final EducationData newEducationData = new EducationData();

			newEducationData.setDegree(educationData2.getDegree());
			newEducationData.setEndDate(educationData2.getEndDate());
			newEducationData.setInstitution(educationData2.getInstitution());
			newEducationData.setMark(educationData2.getMark());
			newEducationData.setStartDate(educationData2.getStartDate());

			final EducationData copyEducationData = this.educationDataService.save(newEducationData);

			educationDataCopy.add(copyEducationData);
		}

		final Collection<MiscellaneousData> miscellaneousData = curriculum.getMiscellaneousDatas();
		final Collection<MiscellaneousData> miscellaneousDataCopy = new HashSet<>();

		for (final MiscellaneousData miscellaneousData2 : miscellaneousData) {

			final MiscellaneousData newMiscellaneousData = new MiscellaneousData();

			newMiscellaneousData.setAttachments(miscellaneousData2.getAttachments());
			newMiscellaneousData.setText(miscellaneousData2.getText());

			final MiscellaneousData copyMiscellaneousData = this.miscellaneousDataService.save(newMiscellaneousData);

			miscellaneousDataCopy.add(copyMiscellaneousData);

		}

		final Collection<PositionData> positionData = curriculum.getPositionDatas();
		final Collection<PositionData> positionDataCopy = new HashSet<>();

		for (final PositionData positionData2 : positionData) {

			final PositionData newPositionData = new PositionData();

			newPositionData.setDescription(positionData2.getDescription());
			newPositionData.setEndDate(positionData2.getEndDate());
			newPositionData.setStartDate(positionData2.getStartDate());
			newPositionData.setTitle(positionData2.getTitle());

			final PositionData copyPositionData = this.positionDataService.save(newPositionData);

			positionDataCopy.add(copyPositionData);

		}

		final PersonalData personalData = curriculum.getPersonalData();

		final PersonalData newPersonalData = new PersonalData();

		newPersonalData.setFullName(personalData.getFullName());
		newPersonalData.setLinkGitHubProfile(personalData.getLinkGitHubProfile());
		newPersonalData.setLinkLinkedInProfile(personalData.getLinkLinkedInProfile());
		newPersonalData.setPhone(personalData.getPhone());
		newPersonalData.setStatement(personalData.getStatement());

		final PersonalData personalDataCopy = this.personalDataService.save(newPersonalData);

		res.setEducationDatas(educationDataCopy);
		res.setRookie(curriculum.getRookie());
		res.setMiscellaneousDatas(miscellaneousDataCopy);
		res.setNoCopy(false);
		res.setTicker("C-" + curriculum.getTicker());
		res.setPersonalData(personalDataCopy);
		res.setPositionDatas(positionDataCopy);

		final Curriculum copy = this.save(res);

		return copy;
	}
	public Curriculum findByPersonalDataId(final int personalDataId) {

		final Curriculum result = this.curriculumRepository.findByPersonalDataId(personalDataId);

		return result;
	}

	public Curriculum findByPositionDataId(final int positionDataId) {

		final Curriculum result = this.curriculumRepository.findByPositionDataId(positionDataId);

		return result;
	}

	public Curriculum findByEducationDataId(final int educationDataId) {

		final Curriculum result = this.curriculumRepository.findByEducationDataId(educationDataId);

		return result;
	}

	public Curriculum findByMiscellaneousDataId(final int miscellanousDataId) {

		final Curriculum result = this.curriculumRepository.findByMiscellaneousDataId(miscellanousDataId);

		return result;
	}

	public void flush() {
		this.curriculumRepository.flush();
	}

	public Boolean securityCompany(final int applicationId) {

		Boolean res = false;

		final Application app = this.applicationService.findOne(applicationId);

		final Company owner = app.getPosition().getCompany();
		final Company login = this.companyService.findByPrincipal();

		if (owner.equals(login))
			res = true;

		return res;
	}

	private String generateTicker(final Date moment) {

		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String dateString = dateFormat.format(moment);

		final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = dateString + "-" + randomAlphaNumeric;

		final int SameTicker = this.curriculumRepository.countCurriculumWithTicker(ticker);

		while (SameTicker > 0)
			this.generateTicker(moment);

		return ticker;

	}
}
