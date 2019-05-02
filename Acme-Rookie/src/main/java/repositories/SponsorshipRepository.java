
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select sp from Sponsorship sp where sp.provider.id = ?1")
	Collection<Sponsorship> findAllByProvider(int idProvider);

	@Query("select sp from Sponsorship sp join sp.positions p where p.id = ?1")
	List<Sponsorship> findAllByPosition(int idPosition);

}
