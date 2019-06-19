
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationData extends DomainEntity {

	private String	degree;
	private String	institution;
	private Double	mark;
	private Date	startDate;
	private Date	endDate;


	@NotBlank
	@SafeHtml
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(final String degree) {
		this.degree = degree;
	}

	@NotBlank
	@SafeHtml
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@NotNull
	@DecimalMin(value = "0.0")
	@DecimalMax(value = "10.0")
	public Double getMark() {
		return this.mark;
	}

	public void setMark(final Double mark) {
		this.mark = mark;
	}

	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
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

}
