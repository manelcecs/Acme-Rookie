
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.UserAccount;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	@Autowired
	private ProviderRepository	providerRepository;


	public Provider findByPrincipal(final UserAccount principal) {
		return this.providerRepository.findByPrincipal(principal.getId());
	}

	public Collection<Provider> findAll() {
		return this.providerRepository.findAll();
	}
}

	public Provider findByPrincipal(final int idPrincipal) {
		return this.providerRepository.findByPrincipal(idPrincipal);
	}

	public Collection<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	public Collection<Provider> getProviders10RateOfAvgOfSponsorships() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.providerRepository.getProviders10RateOfAvgOfSponsorships();

	}

}