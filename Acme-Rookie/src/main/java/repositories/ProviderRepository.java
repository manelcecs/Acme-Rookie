
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select a from Provider a where a.userAccount.id = ?1")
	Provider findByPrincipal(int principalId);

}
