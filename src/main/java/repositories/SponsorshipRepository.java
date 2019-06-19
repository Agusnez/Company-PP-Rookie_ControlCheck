
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s.id from Sponsorship s where s.position.id = ?1 and s.provider.id = ?2")
	Integer findSponsorshipByPositionAndProviderId(int positionId, int id);

	@Query("select s from Sponsorship s where s.position.id = ?1")
	Collection<Sponsorship> findAllByPositionId(int positionId);

	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findAllByProviderId(int actorId);

	@Query("select s from Sponsorship s where s.provider.id = ?1 and s.position.cancellation!=null")
	Collection<Sponsorship> findAllCancelledByProviderId(int actorId);
}
