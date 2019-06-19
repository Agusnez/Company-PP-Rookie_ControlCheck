
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class CreateCurriculumForm {

	private String	statement;
	private String	phone;
	private String	linkGitHubProfile;
	private String	linkLinkedInProfile;


	@NotBlank
	@SafeHtml
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getLinkGitHubProfile() {
		return this.linkGitHubProfile;
	}

	public void setLinkGitHubProfile(final String linkGitHubProfile) {
		this.linkGitHubProfile = linkGitHubProfile;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getLinkLinkedInProfile() {
		return this.linkLinkedInProfile;
	}

	public void setLinkLinkedInProfile(final String linkLinkedInProfile) {
		this.linkLinkedInProfile = linkLinkedInProfile;
	}

}
