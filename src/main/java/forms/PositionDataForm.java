
package forms;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

public class PositionDataForm {

	private int		id;
	private int		version;
	private String	title;
	private String	description;
	private Date	startDate;
	private Date	endDate;
	private Date	cancellation;

	private int		curriculumId;


	@Past
	public Date getCancellation() {
		return this.cancellation;
	}

	public void setCancellation(final Date cancellation) {
		this.cancellation = cancellation;
	}

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
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
	public int getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(final int curriculumId) {
		this.curriculumId = curriculumId;
	}

}
