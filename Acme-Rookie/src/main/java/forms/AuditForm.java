
package forms;

import domain.DomainEntity;
import domain.Position;

public class AuditForm extends DomainEntity {

	private boolean		draft;
	private Integer		score;
	private String		text;

	private Position	position;


	public boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
