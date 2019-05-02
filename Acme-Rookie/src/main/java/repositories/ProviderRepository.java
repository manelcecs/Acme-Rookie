
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select p from Provider p where p.userAccount.id = ?1")
	Provider findByPrincipal(int principalId);

	//ACME-ROOKIE-----------------------------------------------------------------
	@Query("select p from Sponsorship s join s.provider p where (select count(s) from Sponsorship s join s.provider pr where pr.id = p.id)>=(select avg(1*(select count(s) from Sponsorship s where s.provider.id = pro.id)) from Provider pro)")
	Collection<Provider> getProviders10RateOfAvgOfSponsorships();
}
