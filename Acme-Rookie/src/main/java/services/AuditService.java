
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AuditRepository;

@Service
@Transactional
public class AuditService {

	@Autowired
	AuditRepository	auditRepository;


//	public Integer maxScoreOfCompanies() {
//		return this.auditRepository.maxScoreOfCompanies();
//	}

}
