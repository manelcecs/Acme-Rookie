
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ProviderForm extends ActorForm {

	private String	email;
	private String	providerMake;


	@NotBlank
	@SafeHtml
	public String getProviderMake() {
		return this.providerMake;
	}

	public void setProviderMake(final String make) {
		this.providerMake = make;
	}

	@NotBlank
	@Pattern(regexp = "^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,}[>]{1}|[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,})$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
