
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

}
