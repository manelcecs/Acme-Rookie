
package repositories;

import java.util.Collection;

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
	//ACME-ROOKIE---------------------------------------------------------

	@Query("select avg(a.score), p from Audit a join a.position p where a.draft = false group by p")
	Collection<Object[]> getAvgOfAuditScoreOfPosition();

	@Query("select min(a.score), p from Audit a join a.position p where a.draft = false group by p")
	Collection<Object[]> getMinimumOfAuditScoreOfPosition();

	@Query("select max(a.score), p from Audit a join a.position p where a.draft = false group by p")
	Collection<Object[]> getMaximumOfAuditScoreOfPosition();

	@Query("select stddev(a.score), p from Audit a join a.position p where a.draft = false group by p")
	Collection<Object[]> getSDOfAuditScoreOfPosition();

	//--------------------------------------------------------------------

	@Query("select avg(a.score), c from Audit a join a.position p join p.company c where a.draft = false group by c")
	Collection<Object[]> getAvgOfAuditScoreOfCompany();

	@Query("select min(a.score), c from Audit a join a.position p join p.company c where a.draft = false group by c")
	Collection<Object[]> getMinimumOfAuditScoreOfCompany();

	@Query("select max(a.score), c from Audit a join a.position p join p.company c where a.draft = false group by c")
	Collection<Object[]> getMaximumOfAuditScoreOfCompany();

	@Query("select stddev(a.score), c from Audit a join a.position p join p.company c where a.draft = false group by c")
	Collection<Object[]> getSDOfAuditScoreOfCompany();

	@Query("select max(a.score) from Audit a")
	Integer getMaximumOfAuditScore();
	//-------------------------------------------------------------------

	@Query("select avg(1*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Double getAvgOfSponsorshipsPerProvider();

	@Query("select min(1*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Integer getMinimumOfSponsorshipsPerProvider();

	@Query("select max(1*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Integer getMaximumOfSponsorshipsPerProvider();

	@Query("select stddev(1*(select count(s) from Sponsorship s where s.provider.id = p.id)) from Provider p")
	Double getSDOfSponsorshipsPerProvider();

	//-------------------------------------------------------------------

	@Query("select avg(1*(select count(s) from Sponsorship s join s.position p where p.id = ps.id)) from Position ps")
	Double getAvgOfSponsorshipsPerPosition();

	@Query("select min(1*(select count(s) from Sponsorship s join s.position p where p.id = ps.id)) from Position ps")
	Integer getMinimumOfSponsorshipsPerPosition();

	@Query("select max(1*(select count(s) from Sponsorship s join s.position p where p.id = ps.id)) from Position ps")
	Integer getMaximumOfSponsorshipsPerPosition();

	@Query("select stddev(1*(select count(s) from Sponsorship s join s.position p where p.id = ps.id)) from Position ps")
	Double getSDOfSponsorshipsPerPosition();

	//-------------------------------------------------------------------

	@Query("select avg(pt.salaryOffered) from Position pt where ((select avg(a.score) from Audit a join a.position p where p.id = pt.id and a.draft = false) = (select max(1*(select avg(a.score) from Audit a join a.position p where p.id = pos.id and a.draft = false)) from Position pos))")
	Double getAvgOfSalaryOfPositionWithTheHighestAvgOfAuditScore();
}
