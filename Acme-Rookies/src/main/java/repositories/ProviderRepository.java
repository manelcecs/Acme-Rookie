
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select a from Provider a where a.userAccount.id = ?1")
	Provider findByPrincipal(int principalId);

	@Query("select p from Provider p where (1.0*(select count(s) from Sponsorship s where s.provider.id = p.id)>= (1.1*(select avg(1*(select count(sp) from Sponsorship sp where sp.provider.id = pr.id)) from Provider pr)))")
	Collection<Provider> getProviders10RateOfAvgOfSponsorships();

	@Query("select p, (select count(i) from Item i where i.provider.id = p.id) as c from Provider p order by c desc")
	List<Object[]> getTop5OfProvidersByItems();
}
