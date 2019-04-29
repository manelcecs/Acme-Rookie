
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int principalId);

	//DASHBOARD--------------------------------------------------------------
	@Query("select avg(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Double getAvgOfPositionsPerCompany();

	@Query("select min(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Integer getMinimumOfPositionsPerCompany();

	@Query("select max(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Integer getMaximumOfPositionsPerCompany();

	@Query("select stddev(1*(select count(p) from Position p where p.draft = false and p.company.id = c.id)) from Company c")
	Double getSDOfPositionsPerCompany();

	//---------------------------------------------------------------------------------

	@Query("select avg(1*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Double getAvgOfApplicationsPerRookie();

	@Query("select min(1*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Integer getMinimumOfApplicationsPerRookie();

	@Query("select max(1*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Integer getMaximumOfApplicationsPerRookie();

	@Query("select stddev(1*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h")
	Double getSDOfApplicationsPerRookie();

	//----------------------------------------------------------------------------

	@Query("select avg(p.salaryOffered) from Position p where p.draft = false")
	Double getAvgOfSalariesOffered();

	@Query("select min(p.salaryOffered) from Position p where p.draft = false")
	Integer getMinimumOfSalariesOffered();

	@Query("select max(p.salaryOffered) from Position p where p.draft = false")
	Integer getMaximumOfSalariesOffered();

	@Query("select stddev(p.salaryOffered) from Position p where p.draft = false")
	Double getSDOfSalariesOffered();

	//-------------------------------------------------------------------

	@Query("select avg(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.copy = false)) from Rookie h")
	Double getAvgOfCurriculaPerRookie();

	@Query("select min(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.copy = false)) from Rookie h")
	Integer getMinimumOfCurriculaPerRookie();

	@Query("select max(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.copy = false)) from Rookie h")
	Integer getMaximumOfCurriculaPerRookie();

	@Query("select stddev(1*(select count(c) from Curricula c where c.rookie.id = h.id and c.copy = false)) from Rookie h")
	Double getSDOfCurriculaPerRookie();

	//-------------------------------------------------------------------

	@Query("select avg(f.positions.size) from Finder f")
	Double getAvgOfResultsInFinders();

	@Query("select min(f.positions.size) from Finder f")
	Integer getMinimumOfResultsInFinders();

	@Query("select max(f.positions.size) from Finder f")
	Integer getMaximumOfResultsInFinders();

	@Query("select stddev(f.positions.size) from Finder f")
	Double getSDOfResultsInFinders();

	@Query("select 1.0 * count(f) / (select count(fn) from Finder fn where fn.positions.size != 0) from Finder f where f.positions.size = 0")
	Double getRatioOfEmptyVsNotEmptyFinders();

}
