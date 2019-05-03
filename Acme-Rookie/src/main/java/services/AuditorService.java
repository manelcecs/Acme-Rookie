
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AuditorRepository;
import security.UserAccount;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	@Autowired
	AuditorRepository	auditorRepository;


	public Auditor findByPrincipal(final UserAccount userAccount) {
		return this.auditorRepository.findByPrincipal(userAccount.getId());
	}

}
