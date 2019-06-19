
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	private Date		moment;
	private String		text;
	private Double		score;
	private Boolean		finalMode;
	//relationships
	private Auditor		auditor;
	private Position	position;


	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@DecimalMax(value = "10.0")
	@DecimalMin(value = "0.0")
	@NotNull
	public Double getScore() {
		return this.score;
	}
	public void setScore(final Double score) {
		this.score = score;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}
	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}
	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

}
