
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "vat")
})
public abstract class Actor extends DomainEntity {

	//Atributos-----------------------------------------------------------
	private String		name;
	private String		surnames;
	private Integer		vat;
	private String		photo;
	private String		email;
	private CreditCard	creditCard;
	private String		phone;
	private String		address;
	private Boolean		spammer;


	//Getters y Setters-----------------------------------------------------
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Integer getVat() {
		return this.vat;
	}
	public void setVat(final Integer vat) {
		this.vat = vat;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	@SafeHtml
	public String getSurnames() {
		return this.surnames;
	}
	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@URL
	@SafeHtml
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@SafeHtml
	@NotBlank
	@Pattern(regexp = "^[\\w]+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]+){0,1}|(([\\w]\\s)*[\\w])+<\\w+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]+){0,1}>")
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public Boolean getSpammer() {
		return this.spammer;
	}
	public void setSpammer(final Boolean spammer) {
		this.spammer = spammer;
	}


	// Relationships ----------------------------------------------------------
	private UserAccount	userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
