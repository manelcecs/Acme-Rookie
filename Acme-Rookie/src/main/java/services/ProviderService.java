
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProviderRepository;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	@Autowired
	ProviderRepository	providerRepository;


	public Provider findByPrincipal(final int idPrincipal) {
		return this.providerRepository.findByPrincipal(idPrincipal);
	}
}
