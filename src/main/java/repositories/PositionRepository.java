
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select count(p) from Position p where p.ticker = ?1")
	int countPositionWithTicker(String ticker);

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findPositionsByCompanyId(int companyId);

	@Query("select p from Position p where p.company.id = ?1 and p.cancellation=null")
	Collection<Position> findPositionsByCompanyIdToAdd(int companyId);

	@Query("select p from Position p where p.finalMode = true and p.deadline > ?1 and p.cancellation=null")
	Collection<Position> findPositionsFinalModeTrue(Date localDate);

	@Query("select p from Position p where p.finalMode = true")
	Collection<Position> findPositionsFinalModeTrueWithoutDeadline();

	@Query("select p from Position p where p.company.id = ?1 and p.finalMode = true and p.deadline > ?2 and p.cancellation=null")
	Collection<Position> findPositionsByCompanyIdAndFinalModeTrue(int companyId, Date localDate);

	@Query("select p from Position p where (p.title like ?1 or p.description like ?1 or p.profile like ?1 or p.skills like ?1 or p.technologies like ?1) and p.company.commercialName like ?2 and p.finalMode = true and p.deadline>CURRENT_TIMESTAMP and p.cancellation=null")
	Collection<Position> findPositionsByFilter(String keyword, String companyName);

	@Query("select p from Position p where (p.ticker like ?1 or p.title like ?1 or p.description like ?1 or p.skills like ?1 or p.technologies like ?1 or p.profile like ?1) and (p.deadline <= ?2) and (p.offeredSalary <= ?3) and (p.offeredSalary >= ?4) and (p.finalMode = true) and (p.deadline>CURRENT_TIMESTAMP) and (p.cancellation=null)")
	Collection<Position> findPositionByFinder(String keyword, Date deadline, Double maximumSalary, Double minimumSalary);

	@Query("select p from Position p where (p.ticker like ?1 or p.title like ?1 or p.description like ?1 or p.skills like ?1 or p.technologies like ?1 or p.profile like ?1) and (p.offeredSalary <= ?2) and (p.offeredSalary >= ?3) and (p.finalMode = true) and (p.deadline>CURRENT_TIMESTAMP) and (p.cancellation=null)")
	Collection<Position> findPositionByFinderWithoutDeadline(String keyword, Double maximumSalary, Double minimumSalary);

	@Query("select max(p.offeredSalary) from Position p")
	Double maxOfferedSalaryPosition();

	//	@Query("select p from Position p where (select count(a) from Auditor a where a.position.id=p.id)=0 and (p.finalMode=true)")
	//	Collection<Position> positionsNotAssignedAnyAuditor();

	@Query("select p from Position p where (select count(a) from Auditor a join a.positions t where (a.id = ?1) and (t.id=p.id) and (t.cancellation!=null))!=0")
	Collection<Position> findPositionsCancelledByAuditorId(int auditorId);

	@Query("select p from Position p where (select count(a) from Auditor a join a.positions t where (a.id = ?1) and (t.id=p.id) and (t.cancellation=null))!=0")
	Collection<Position> findPositionsNotCancelledByAuditorId(int auditorId);

	@Query("select p from Position p where p.cancellation!=null")
	Collection<Position> findPositionsCancelled();

}
