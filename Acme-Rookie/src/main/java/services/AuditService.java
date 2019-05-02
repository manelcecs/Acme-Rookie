
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


	public Integer getNumberOfACompany(final int idCompany) {
		return this.auditRepository.getNumberOfACompany(idCompany);
	}

	public Double averageScoreOfCompany(final int idCompany) {
		return this.auditRepository.averageScoreOfCompany(idCompany);
	}

	public Double maxScoreOfCompanies() {
		return this.auditRepository.maxScoreOfCompanies().get(0);
	}

	public Double minScoreOfCompanies() {
		return this.auditRepository.minScoreOfCompanies().get(0);
	}

}
