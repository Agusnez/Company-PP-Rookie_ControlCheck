
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select p from Audit p where p.position.id = ?1")
	Collection<Audit> findAuditsByPositionId(int positionId);

	@Query("select p from Audit p where p.finalMode = true and p.position.id = ?1")
	Collection<Audit> findFinalAuditsByPositionId(int positionId);

	@Query("select a from Audit a where a.auditor.id = ?1 and a.position.cancellation=null")
	Collection<Audit> findAuditsByAuditorId(int auditorId);

	@Query("select a from Audit a where a.auditor.id = ?1 and a.position.cancellation!=null")
	Collection<Audit> findAuditsCancelledByAuditorId(int auditorId);

}
