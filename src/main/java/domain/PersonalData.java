
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private String	fullName;
	private String	statement;
	private String	phone;
	private String	linkGitHubProfile;
	private String	linkLinkedInProfile;


	@NotBlank
	@SafeHtml
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
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

}
