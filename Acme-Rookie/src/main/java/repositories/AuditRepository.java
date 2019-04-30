
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//	@Query("select max(avg(sum(au.score)) from Audit join au.position p join p.company")
	//	Integer maxScoreOfCompanies();

}
