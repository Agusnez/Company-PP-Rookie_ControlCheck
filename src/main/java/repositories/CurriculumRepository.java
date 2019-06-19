
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select c from Curriculum c where c.noCopy = true and c.rookie.id = ?1")
	Collection<Curriculum> findByRookieId(int rookieId);

	@Query("select c from Curriculum c where c.rookie.id = ?1")
	Collection<Curriculum> findAllByRookieId(int rookieId);

	@Query("select c from Curriculum c where c.noCopy = true and c.personalData.id = ?1")
	Curriculum findByPersonalDataId(int personalDataId);

	@Query("select c from Curriculum c join c.positionDatas t where c.noCopy = true and t.id = ?1")
	Curriculum findByPositionDataId(int positionDataId);

	@Query("select c from Curriculum c join c.educationDatas t where c.noCopy = true and t.id = ?1")
	Curriculum findByEducationDataId(int educationDataId);

	@Query("select c from Curriculum c join c.miscellaneousDatas t where c.noCopy = true and t.id = ?1")
	Curriculum findByMiscellaneousDataId(int miscellaneousDataId);

	@Query("select count(p) from Curriculum p where p.ticker = ?1")
	int countCurriculumWithTicker(String ticker);

}
