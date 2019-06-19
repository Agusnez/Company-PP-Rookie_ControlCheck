
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class PersonalDataForm {

	private int		id;
	private int		version;
	private String	statement;
	private String	phone;
	private String	linkGitHubProfile;
	private String	linkLinkedInProfile;

	private int		curriculumId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

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

	@NotNull
	public int getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(final int curriculumId) {
		this.curriculumId = curriculumId;
	}

}
