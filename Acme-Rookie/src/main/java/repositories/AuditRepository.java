
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select count(au) from Audit au where au.draft = false and au.position.draft = false and au.position.cancelled = false and au.position.company.banned = false and au.position.company.userAccount.authorities.size > 0 and au.position.company.id = ?1")
	Integer getNumberOfACompany(int idCompany);

	@Query("select avg(au.score) from Audit au where au.draft = false and au.position.draft = false and au.position.cancelled = false and au.position.company.banned = false and au.position.company.userAccount.authorities.size > 0 and au.position.company.id = ?1")
	Double averageScoreOfCompany(int idCompany);

	@Query("select avg(au.score) from Audit au where au.draft = false and au.position.draft = false and au.position.cancelled = false and au.position.company.banned = false and au.position.company.userAccount.authorities.size > 0  group by au.position.company order by avg(au.score) DESC")
	List<Double> maxScoreOfCompanies();

	@Query("select avg(au.score) from Audit au where au.draft = false and au.position.draft = false and au.position.cancelled = false and au.position.company.banned = false and au.position.company.userAccount.authorities.size > 0  group by au.position.company order by avg(au.score) ASC")
	List<Double> minScoreOfCompanies();

}
