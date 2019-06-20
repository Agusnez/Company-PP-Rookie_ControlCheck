
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "ticker")
})
public class Quolet extends DomainEntity {

	private String		ticker;
	private Date		publicationMoment;
	private String		body;
	private String		picture;
	private Boolean		finalMode;

	//relationships
	private Company		company;
	private Application	application;


	@NotBlank
	@SafeHtml
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	@SafeHtml
	@Size(max = 100)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@SafeHtml
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
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

	@ManyToOne(optional = false)
	@Valid
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

}
