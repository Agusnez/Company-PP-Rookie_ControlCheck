
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f join f.positions t where t.id = ?1")
	Collection<Finder> findFindersByPositionId(int positionId);

	@Query("select f from Finder f where f.rookie.id = ?1")
	Finder findFinderByRookie(int rookieId);

}
