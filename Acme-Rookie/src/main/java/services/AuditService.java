
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import security.LoginService;
import domain.Audit;
import domain.Auditor;
import forms.AuditForm;

@Service
@Transactional
public class AuditService {

	@Autowired
	private AuditRepository	auditRepository;

	@Autowired
	private AuditorService	auditorService;

	public Integer getNumberOfACompany(final int idCompany) {
		return this.auditRepository.getNumberOfACompany(idCompany);
	}
	@Autowired
	private Validator		validator;

	public Double averageScoreOfCompany(final int idCompany) {
		return this.auditRepository.averageScoreOfCompany(idCompany);
	}

	public Double maxScoreOfCompanies() {
		return this.auditRepository.maxScoreOfCompanies().get(0);
	}

	public Double minScoreOfCompanies() {
		return this.auditRepository.minScoreOfCompanies().get(0);
	}


	//	public Integer maxScoreOfCompanies() {
	//		return this.auditRepository.maxScoreOfCompanies();
	//	}

	public Audit findOne(final int idAudit) {
		return this.auditRepository.findOne(idAudit);
	}

	public Audit create() {
		Assert.isTrue(utiles.AuthorityMethods.chechAuthorityLogged("AUDITOR"));
		return new Audit();
	}

	public Audit save(final Audit audit) {

		Assert.isTrue(utiles.AuthorityMethods.chechAuthorityLogged("AUDITOR"));

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(audit.getPosition().getAuditor().equals(auditorLogged));

		if (audit.getId() != 0) {
			this.flush();
			final Audit oldAudit = this.findOne(audit.getId());
			Assert.isTrue(oldAudit.getDraft());
		}

		return this.auditRepository.save(audit);

	}

	//Metodo de borrar la auditoria
	public void delete(final int idAudit) {
		Assert.isTrue(utiles.AuthorityMethods.chechAuthorityLogged("AUDITOR"));

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());
		final Audit audit = this.findOne(idAudit);

		Assert.isTrue(audit.getPosition().getAuditor().equals(auditorLogged));
		Assert.isTrue(audit.getDraft());

		this.auditRepository.delete(audit);
	}

	public Collection<Audit> getAuditsOfAnAuditor(final int idAuditor) {
		return this.auditRepository.getAuditsOfAnAuditor(idAuditor);
	}

	public AuditForm castToForm(final Audit audit) {
		final AuditForm auditForm = new AuditForm();
		auditForm.setId(audit.getId());
		auditForm.setVersion(audit.getVersion());
		auditForm.setText(audit.getText());
		auditForm.setScore(audit.getScore());
		auditForm.setDraft(audit.getDraft());
		auditForm.setPosition(audit.getPosition());

		return auditForm;

	}

	public Audit changeDraft(final int idAudit) {
		Assert.isTrue(utiles.AuthorityMethods.chechAuthorityLogged("AUDITOR"));

		final Audit audit = this.findOne(idAudit);

		final Auditor auditorLogged = this.auditorService.findByPrincipal(LoginService.getPrincipal());

		Assert.isTrue(audit.getPosition().getAuditor().equals(auditorLogged));

		audit.setDraft(false);

		return this.auditRepository.save(audit);

	}

	public Audit reconstruct(final AuditForm auditForm, final BindingResult bindingResult) {
		final Audit audit = new Audit();

		audit.setId(auditForm.getId());
		audit.setVersion(auditForm.getVersion());

		audit.setDraft(auditForm.getDraft());
		audit.setScore(auditForm.getScore());
		audit.setPosition(auditForm.getPosition());
		audit.setText(auditForm.getText());
		audit.setMoment(DateTime.now().toDate());

		this.validator.validate(audit, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();
		return audit;

	}

	public void flush() {
		this.auditRepository.flush();
	}

	public Collection<Audit> getAuditsOfPosition(final int idPosition) {
		return this.auditRepository.getAuditsOfPosition(idPosition);
	}

}
