package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Company;
import domain.Rookie;
import domain.Position;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int userAccountId);
	
	@Query(nativeQuery= true, value= "select avg(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company) "
									+ "group by company) as counts")
	Double avgOfPositionsPerCompany();
	
	@Query(nativeQuery=true, value= "select max(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company) "
									+ "group by company) as counts")
	Integer maxPositionOfCompany();
	
	@Query(nativeQuery=true, value= "select min(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company)"
									+ " group by company) as counts")
	Integer minPositionOfCompany();
	
	@Query(nativeQuery=true, value= "select std(count) from (select count(*) as Count "
									+ "from `position` p join company c on (c.id = p.company)"
									+ " group by company) as counts")
	Double stdOfPositionsPerCompany();
	
	@Query(nativeQuery= true, value= "select avg(count) from (select count(*) as Count"
									+ " from application a join rookie h on (h.id = a.rookie)"
									+ " group by rookie) as counts")
	Double avgOfApplicationsPerRookie();
	
	@Query(nativeQuery= true, value= "select max(count) from (select count(*) as Count"
									+ " from application a join rookie h on (h.id = a.rookie)"
									+ " group by rookie) as counts")
	Integer maxApplicationsOfRookie();
	
	@Query(nativeQuery= true, value= "select min(count) from (select count(*) as Count"
									+ " from application a join rookie h on (h.id = a.rookie)"
									+ " group by rookie) as counts")
	Integer minApplicationsOfRookie();
	
	@Query(nativeQuery= true, value= "select std(count) from (select count(*) as Count"
									+ " from application a join rookie h on (h.id = a.rookie)"
									+ " group by rookie) as counts")
	Double stdOfApplicationsPerRookie();
	
	@Query(nativeQuery = true, value= "select c.name  as Count from `position` p "
										+ "join company c on (c.id = p.company) group by company "
										+ "ORDER BY COUNT(*) DESC limit 3")
	List<String> topCompaniesWithMorePositions();
	
	@Query(nativeQuery = true, value= "select h.name  as Count from application a "
										+ "join rookie h on (h.id = a.rookie) group by rookie "
										+ "ORDER BY COUNT(*) DESC limit 3")
	List<String> topRookieWithMoreApplications();
	
	@Query(nativeQuery = true, value= "select avg(p.offered_salary) from `position` p")
	Double avgSalaries();
	
	@Query(nativeQuery = true, value= "select min(p.offered_salary) from `position` p")
	Integer minSalary();
	
	@Query(nativeQuery = true, value= "select max(p.offered_salary) from `position` p")
	Integer maxSalary();
	
	@Query(nativeQuery = true, value= "select std(p.offered_salary) from `position` p")
	Double stdSalaries();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary asc limit 1")
	int findWorstPosition();
	
	@Query(nativeQuery = true, value= "select p.id from `position` p order by offered_salary desc limit 1")
	int findBestPosition();
	
	@Query(nativeQuery = true, value= "select min(count) from (select count(*) as Count " + 
									"from curriculum c join rookie h on (h.id = c.rookie) " + 
			 						"group by rookie) as counts")
	Integer minNumberOfCurriculaPerRookie();
	
	@Query(nativeQuery = true, value= "select max(count) from (select count(*) as Count " + 
				"from curriculum c join rookie h on (h.id = c.rookie) " + 
				"group by rookie) as counts")
	Integer maxNumberOfCurriculaPerRookie();
	
	@Query(nativeQuery = true, value= "select avg(count) from (select count(*) as Count " + 
				"from curriculum c join rookie h on (h.id = c.rookie)" + 
					"group by rookie) as counts")
	Double avgNumberOfCurriculaPerRookie();
	
	@Query(nativeQuery = true, value= "select std(count) from (select count(*) as Count " + 
				"from curriculum c join rookie h on (h.id = c.rookie) " + 
				"group by rookie) as counts")
	Double stdNumberOfCurriculaPerRookie();
	
	@Query(nativeQuery = true, value= "select min(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Integer minNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select max(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Integer maxNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select avg(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Double avgNumberOfResultsInFinders();
	
	@Query(nativeQuery = true, value= "select std(count) from (" + 
				" select f.id, count(fp.finder) as Count from finder f" + 
				" left join finder_positions fp on (f.id = fp.finder)" + 
				" group by f.id" + 
				" ) as counts")	
	Double stdNumberOfResultsInFinders();
	
	
	@Query(nativeQuery = true, value = "select sum(case when Count > 0 then 1 else 0 end)/sum(case when Count < 1 then 1 else 0 end) n from ( " + 
				"select f.id, count(fp.finder) as Count from finder f " + 
				"left join finder_positions fp on (f.id = fp.finder) " + 
				"group by f.id " + 
				") as counts")
	Double ratioEmptyNotEmptyFinders();
	
	// --------- Acme-Rookie ----------
	
	@Query(nativeQuery = true, value = "select avg(score) from audit")
	Double avgScorePositions();
	
	@Query(nativeQuery = true, value = "select min(score) from audit")
	Double minScorePositions();
	
	@Query(nativeQuery = true, value = "select max(score) from audit")
	Double maxScorePositions();
	
	@Query(nativeQuery = true, value = "select std(score) from audit")
	Double stdScorePositions();
	
	@Query(nativeQuery = true, value = "select avg(score) from company")
	Double avgScoreCompany();
	
	@Query(nativeQuery = true, value = "select min(score) from company")
	Double minScoreCompany();
	
	@Query(nativeQuery = true, value = "select max(score) from company")
	Double maxScoreCompany();
	
	@Query(nativeQuery = true, value = "select std(score) from company")
	Double stdScoreCompany();
		
	@Query(nativeQuery = true, value = "select commercial_name from company where score >= 0 order by score desc limit 3")
	List<String> topScoreCompany();
	
	@Query(nativeQuery = true, value = "select avg(p.offered_salary) from `position` p join (select commercial_name, id from company where score >= 0 order by score desc limit 3) c on (c.id = p.company)")
	Double avgSalaryTopCompany();
	
	@Query(nativeQuery = true, value = "select min(count) from (select p.id, count(i.id) as Count from item i right join provider p on (p.id = i.provider) group by p.id) as counts")
	Double minItemPerProvider();
	
	@Query(nativeQuery = true, value = "select max(count) from (select p.id, count(i.id) as Count from item i right join provider p on (p.id = i.provider) group by p.id) as counts")
	Double maxItemPerProvider();
	
	@Query(nativeQuery = true, value = "select avg(count) from (select p.id, count(i.id) as Count from item i right join provider p on (p.id = i.provider) group by p.id) as counts")
	Double avgItemPerProvider();
	
	@Query(nativeQuery = true, value = "select std(count) from (select p.id, count(i.id) as Count from item i right join provider p on (p.id = i.provider) group by p.id) as counts")
	Double stdItemPerProvider();
	
	@Query(nativeQuery = true, value = "select p.provider_make from (select p.provider_make, count(i.id) as Counting from item i right join provider p on (p.id = i.provider) group by p.id) p order by Counting desc limit 5")
	List<String> top5Provider();
	
	@Query(nativeQuery = true, value = "select avg(count) from (select p.id, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts")
	Double avgSponsorshipPerProvider();
	
	@Query(nativeQuery = true, value = "select min(count) from (select p.id, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts")
	Double minSponsorshipPerProvider();
	
	@Query(nativeQuery = true, value = "select max(count) from (select p.id, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts")
	Double maxSponsorshipPerProvider();
	
	@Query(nativeQuery = true, value = "select std(count) from (select p.id, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts")
	Double stdSponsorshipPerProvider();
	
	@Query(nativeQuery = true, value = "select avg(count) from (select p.id, count(s.id) as Count from sponsorship s right join position p on (p.id = s.`position`) group by p.id) as counts")
	Double avgSponsorshipPerPosition();
	
	@Query(nativeQuery = true, value = "select min(count) from (select p.id, count(s.id) as Count from sponsorship s right join position p on (p.id = s.`position`) group by p.id) as counts")
	Double minSponsorshipPerPosition();
	
	@Query(nativeQuery = true, value = "select max(count) from (select p.id, count(s.id) as Count from sponsorship s right join position p on (p.id = s.`position`) group by p.id) as counts")
	Double maxSponsorshipPerPosition();
	
	@Query(nativeQuery = true, value = "select std(count) from (select p.id, count(s.id) as Count from sponsorship s right join position p on (p.id = s.`position`) group by p.id) as counts")
	Double stdSponsorshipPerPosition();
	
	@Query(nativeQuery = true, value = "select name from (select p.holder_name as name, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts where Count > (select avg(count) * 0.1 + avg(count) from (select p.id, count(s.id) as Count from sponsorship s right join provider p on (p.id = s.provider) group by p.id) as counts)")
	List<String> superiorProviders();
	
	
}
