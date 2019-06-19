
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	//Atributos--------------------------------------------------------------
	private Date	moment;
	private String	subject;
	private String	body;
	private String	tags;
	private Boolean	spam;


	//Getters y Setters---------------------------------------------------------
	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@SafeHtml
	public String getTags() {
		return this.tags;
	}
	public void setTags(final String tags) {
		this.tags = tags;
	}

	@NotNull
	public Boolean getSpam() {
		return this.spam;
	}

	public void setSpam(final Boolean spam) {
		this.spam = spam;
	}


	// Relationships ----------------------------------------------------------
	private Actor	sender;
	private Actor	recipient;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
