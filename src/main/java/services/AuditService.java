
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import security.Authority;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditService {

	// Managed Repository ------------------------
	@Autowired
	private AuditRepository	auditRepository;

	// Suporting services ------------------------
	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	// Simple CRUD methods -----------------------

	public Audit create(final Position position) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Audit result = new Audit();

		result.setFinalMode(false);
		result.setAuditor(this.auditorService.findByPrincipal());
		Assert.isTrue(this.auditorService.findByPrincipal().getPositions().contains(position));
		result.setPosition(position);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(currentMoment);

		return result;
	}

	public Collection<Audit> findAll() {
		Collection<Audit> result;
		result = this.auditRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Audit findOne(final int auditId) {
		Audit audit;
		audit = this.auditRepository.findOne(auditId);
		return audit;
	}

	public Audit save(final Audit audit) {

		Assert.notNull(audit);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));
		Assert.isTrue(actor.getId() == audit.getAuditor().getId());

		Assert.isTrue(audit.getAuditor().getPositions().contains(audit.getPosition()));

		Audit result;

		if (audit.getId() != 0) {
			final Audit auditBBDD = this.findOne(audit.getId());
			Assert.isTrue(auditBBDD.getFinalMode() == false);
		}

		result = this.auditRepository.save(audit);

		return result;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority comp = new Authority();
		comp.setAuthority(Authority.AUDITOR);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(comp));
		Assert.isTrue(actor.getId() == audit.getAuditor().getId());
		Assert.isTrue(audit.getAuditor().getPositions().contains(audit.getPosition()));

		Assert.isTrue(audit.getFinalMode() == false);
		this.auditRepository.delete(audit);

	}

	public void deleteAdmin(final Audit audit) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(admin));

		this.auditRepository.delete(audit);
	}

	public void deleteAll(final int auditorId) {

		final Collection<Audit> audits = this.findAuditsByAuditorId(auditorId);
		if (!audits.isEmpty())
			for (final Audit a : audits)
				this.auditRepository.delete(a);
	}

	//Other bussiness methods

	public Audit reconstruct(final Audit audit, final Position position, final BindingResult binding) {

		Audit result = audit;
		final Audit auditNew = this.create(position);

		if (audit.getId() == 0 || audit == null) {

			audit.setPosition(auditNew.getPosition());
			audit.setMoment(auditNew.getMoment());
			audit.setAuditor(auditNew.getAuditor());

			this.validator.validate(audit, binding);

			result = audit;
		} else {

			final Audit auditBBDD = this.findOne(audit.getId());

			audit.setPosition(auditBBDD.getPosition());
			audit.setMoment(auditBBDD.getMoment());
			audit.setAuditor(auditBBDD.getAuditor());

			this.validator.validate(audit, binding);

		}

		return result;

	}

	public Collection<Audit> findAuditsByAuditorId(final int auditorId) {
		final Collection<Audit> audits = this.auditRepository.findAuditsByAuditorId(auditorId);

		return audits;
	}

	public Collection<Audit> findAuditsByPositionId(final int positionId) {
		final Collection<Audit> audits = this.auditRepository.findAuditsByPositionId(positionId);

		return audits;
	}

	public Collection<Audit> findFinalAuditsByPositionId(final int positionId) {
		final Collection<Audit> audits = this.auditRepository.findFinalAuditsByPositionId(positionId);

		return audits;
	}

	public Boolean auditAuditorSecurity(final int auditId) {
		Boolean res = false;
		final Audit audit = this.findOne(auditId);

		final Auditor owner = audit.getAuditor();

		final Auditor login = this.auditorService.findByPrincipal();

		if (login.equals(owner) && login.getPositions().contains(audit.getPosition()))
			res = true;

		return res;
	}

	public void flush() {
		this.auditRepository.flush();
	}

	public Collection<Audit> findAuditsCancelledByAuditorId(final int auditorId) {

		final Collection<Audit> res = this.auditRepository.findAuditsCancelledByAuditorId(auditorId);

		return res;
	}

}
