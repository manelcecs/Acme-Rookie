
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select a from Provider a where a.userAccount.id = ?1")
	Provider findByPrincipal(int principalId);


	@Query("select p from Sponsorship s join s.provider p where (select count(s) from Sponsorship s join s.provider pr where pr.id = p.id)>=(select avg(1*(select count(s) from Sponsorship s where s.provider.id = pro.id)) from Provider pro)")
	Collection<Provider> getProviders10RateOfAvgOfSponsorships();
}