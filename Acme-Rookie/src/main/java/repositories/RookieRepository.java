
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	@Query("select a from Rookie a where a.userAccount.id = ?1")
	Rookie findByPrincipal(int principalId);

	@Query("select h from Rookie h where (1*(Select count(a) from Application a where a.rookie.id =h.id) = (select max(1*(select count(a) from Application a where a.rookie.id = h.id)) from Rookie h))")
	Collection<Rookie> getRookiesWithMoreApplications();
}
