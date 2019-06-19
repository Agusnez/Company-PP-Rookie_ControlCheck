
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.sender.id = ?1")
	Collection<Message> messagePerActor(int actorId);

	@Query("select m from Message m where m.sender.id = ?1 or m.recipient.id = ?1")
	Collection<Message> AllmessagePerActor(int actorId);

	@Query("select m from Message m where (m.sender.id = ?1 or m.recipient.id = ?1) and m.tags='DELETE' ")
	Collection<Message> AllmessageDELETEPerActor(int actorId);

	@Query("select m from Message m where m.recipient.id = null) and m.tags='SYSTEM'")
	Collection<Message> AllmessageSYSTEM();

}
