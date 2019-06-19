
package services;

import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	// Managed repository

	@Autowired
	private ProblemRepository	problemRepository;

	// Suporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods

	public Problem create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Problem result = new Problem();

		final Collection<String> attachments = new HashSet<>();
		result.setAttachments(attachments);
		result.setCompany(this.companyService.findByPrincipal());

		result.setFinalMode(false);
		//prueba
		result.setPositions(null);

		return result;

	}

	public Collection<Problem> findAll() {

		final Collection<Problem> problems = this.problemRepository.findAll();

		Assert.notNull(problems);

		return problems;
	}

	public Problem findOne(final int problemId) {

		final Problem problem = this.problemRepository.findOne(problemId);

		return problem;

	}

	public Problem save(final Problem problem) {
		Assert.notNull(problem);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority comp = new Authority();
		comp.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == problem.getCompany().getId());

		Problem result;

		if (problem.getId() != 0) {
			final Problem problemBBDD = this.findOne(problem.getId());
			Assert.isTrue(problemBBDD.getFinalMode() == false);
		}

		//		final Problem problemBBDD = this.findOne(problem.getId());
		//
		//		Assert.isTrue(problemBBDD.getFinalMode() == false || problem.getId() == 0);

		this.checkPictures(problem.getAttachments());
		result = this.problemRepository.save(problem);

		return result;
	}

	public void delete(final Problem problem) {
		Assert.notNull(problem);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == problem.getCompany().getId());

		Assert.isTrue(problem.getFinalMode() == false);
		this.problemRepository.delete(problem);

	}

	public void deleteAll(final int actorId) {

		final Collection<Problem> problems = this.findProblemByCompanyId(actorId);

		if (!problems.isEmpty())
			for (final Problem p : problems) {

				final Collection<Application> apps = this.applicationService.findByProblemId(p.getId());
				if (!apps.isEmpty())
					for (final Application a : apps)
						this.applicationService.delete(a);

				this.problemRepository.delete(p);
			}

	}

	public void checkPictures(final Collection<String> attachments) {

		for (final String url : attachments)
			try {
				new URL(url);
			} catch (final Exception e) {
				throw new DataIntegrityViolationException("Invalid URL");
			}
	}
	//Añadir una position a un problem
	public void addPositionToProblem(final Position position, final Problem problem) {

		Assert.notNull(problem);
		Assert.notNull(position);

		//un problem no puede añadirse a una position cancelada
		Assert.isTrue(position.getCancellation() == null);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(position.getDeadline().after(currentMoment));
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.COMPANY);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == problem.getCompany().getId());
		Assert.isTrue(actor.getId() == position.getCompany().getId());

		Assert.isTrue(problem.getFinalMode() == true);
		final Collection<Position> positions = problem.getPositions();
		Assert.isTrue(!positions.contains(position));
		positions.add(position);

		problem.setPositions(positions);

		final Problem saved = this.problemRepository.save(problem);

		Assert.isTrue(saved.getPositions().contains(position));

	}
	//Other Business methods----------------------------------------------------
	public Collection<Problem> findProblemByCompanyId(final int companyId) {
		final Collection<Problem> problems = this.problemRepository.findProblemsByCompanyId(companyId);

		return problems;
	}

	public Collection<Problem> findProblemsByPositionId(final int positionId) {
		final Collection<Problem> problems = this.problemRepository.findProblemsByPositionId(positionId);

		return problems;
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {

		Problem result = problem;
		final Problem problemNew = this.create();

		if (problem.getId() == 0 || problem == null) {

			problem.setCompany(problemNew.getCompany());

			this.validator.validate(problem, binding);

			result = problem;
		} else {

			final Problem problemBBDD = this.findOne(problem.getId());

			problem.setCompany(problemBBDD.getCompany());

			this.validator.validate(problem, binding);

		}

		return result;

	}

	public Boolean problemCompanySecurity(final int problemId) {
		Boolean res = false;
		final Problem problem = this.findOne(problemId);

		final Company owner = problem.getCompany();

		final Company login = this.companyService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}

	public void flush() {
		this.problemRepository.flush();
	}

	public Boolean exist(final int problemId) {

		Boolean res = false;

		final Problem problem = this.problemRepository.findOne(problemId);

		if (problem != null)
			res = true;

		return res;

	}
}
