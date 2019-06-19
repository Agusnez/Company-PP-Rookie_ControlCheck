
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.rookie.id = ?1 and a.status = 'ACCEPTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllAcceptedByRookie(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status = 'REJECTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllRejectedByRookie(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status = 'PENDING' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllPendingByRookie(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.status = 'SUBMITTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllSubmittedByRookie(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.position.deadline < CURRENT_DATE")
	Collection<Application> findAllDeadLinePastByRookie(int rookieId);

	@Query("select a from Application a where a.rookie.id = ?1 and a.position.cancellation != null")
	Collection<Application> findAllCancelledByRookie(int rookieId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'ACCEPTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllAcceptedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'REJECTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllRejectedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'PENDING' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllPendingByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.status = 'SUBMITTED' and a.position.deadline > CURRENT_DATE and a.position.cancellation=null")
	Collection<Application> findAllSubmittedByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.position.deadline < CURRENT_DATE")
	Collection<Application> findAllDeadLinePastByCompany(int companyId);

	@Query("select a from Application a where a.position.company.id = ?1 and a.position.cancellation != null")
	Collection<Application> findAllCancelledByCompany(int rookieId);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPositionId(final int positionId);

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblemId(final int problemId);

	@Query("select a from Application a where a.rookie.id = ?1")
	Collection<Application> findByRookieId(final int rookieId);

}
