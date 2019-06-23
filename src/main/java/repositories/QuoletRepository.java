
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.application.id = ?1")
	Collection<Quolet> quoletsPerApplicationId(int applicationId);

	@Query("select q from Quolet q where q.application.id = ?1 and q.finalMode = true")
	Collection<Quolet> quoletsPublishedPerApplicationId(int applicationId);
	
	@Query("select q from Quolet q where q.company.id = ?1")
	Collection<Quolet> quoletsByCompanyId(int companyId);
}
