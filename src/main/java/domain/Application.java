
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = @Index(columnList = "status"))
public class Application extends DomainEntity {

	private Date		moment;
	private String		status;
	private String		answer;
	private Date		submitMoment;

	private Position	position;
	private Curriculum	curriculum;
	private Rookie		rookie;
	private Problem		problem;


	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "\\ASUBMITTED\\z|\\AREJECTED\\z|\\AACCEPTED\\z|\\APENDING\\z")
	@SafeHtml
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@SafeHtml
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getSubmitMoment() {
		return this.submitMoment;
	}

	public void setSubmitMoment(final Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	@ManyToOne(optional = false)
	@Valid
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@ManyToOne(optional = false)
	@Valid
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

}
