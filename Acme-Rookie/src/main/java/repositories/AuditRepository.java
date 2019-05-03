
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//	@Query("select max(avg(sum(au.score)) from Audit join au.position p join p.company")
	//	Integer maxScoreOfCompanies();

	@Query("select a from Audit a where a.position.auditor.id = ?1")
	Collection<Audit> getAuditsOfAnAuditor(int idAuditor);

	@Query("select a from Audit a where a.position.id = ?1")
	Collection<Audit> getAuditsOfPosition(int idPosition);

}
