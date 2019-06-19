
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = @Index(columnList = "deadline"))
public class Position extends DomainEntity {

	private String	ticker;
	private String	title;
	private String	description;
	private Date	deadline;
	private String	profile;
	private String	skills;
	private String	technologies;
	private Double	offeredSalary;
	private Boolean	finalMode;
	private Date	cancellation;

	private Company	company;


	@Past
	public Date getCancellation() {
		return this.cancellation;
	}

	public void setCancellation(final Date cancellation) {
		this.cancellation = cancellation;
	}

	@SafeHtml
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
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

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotBlank
	@SafeHtml
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	@NotBlank
	@SafeHtml
	public String getSkills() {
		return this.skills;
	}

	public void setSkills(final String skills) {
		this.skills = skills;
	}

	@NotBlank
	@SafeHtml
	public String getTechnologies() {
		return this.technologies;
	}

	public void setTechnologies(final String technologies) {
		this.technologies = technologies;
	}

	@NotNull
	@Min(0)
	public Double getOfferedSalary() {
		return this.offeredSalary;
	}

	public void setOfferedSalary(final Double offeredSalary) {
		this.offeredSalary = offeredSalary;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@ManyToOne(optional = false)
	@Valid
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

}
