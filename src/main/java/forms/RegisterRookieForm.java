
package forms;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.CreditCard;

public class RegisterRookieForm {

	// Constructors -----------------------------------------------------------

	public RegisterRookieForm() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String		name;
	private String		surnames;
	private Integer		vat;
	private String		photo;
	private String		email;
	private String		phone;
	private String		address;
	private String		username;
	private String		password;
	private String		confirmPassword;
	private Boolean		checkbox;
	//Credit card
	private CreditCard	creditCard;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurnames() {
		return this.surnames;
	}
	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@NotNull
	@Column(unique = true)
	public Integer getVat() {
		return this.vat;
	}
	public void setVat(final Integer vat) {
		this.vat = vat;
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
	@Pattern(regexp = "^[\\w]+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+>$")
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

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotNull
	public Boolean getCheckbox() {
		return this.checkbox;
	}

	public void setCheckbox(final Boolean checkbox) {
		this.checkbox = checkbox;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	//Business metohds--------------------------------------------
	public Boolean checkPassword() {
		Boolean res;

		if (this.getPassword().equals(this.getConfirmPassword()))
			res = true;
		else
			res = false;

		return res;
	}
}
