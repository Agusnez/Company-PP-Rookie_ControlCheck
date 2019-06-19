
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class MiscellaneousDataForm {

	private int					id;
	private int					version;
	private String				text;
	private Collection<String>	attachments;

	private int					curriculumId;


	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	public int getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(final int curriculumId) {
		this.curriculumId = curriculumId;
	}

}
