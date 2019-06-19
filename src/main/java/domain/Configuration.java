
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Atributos-----------------------------------------------------------
	private Collection<String>	spamWords;
	private String				banner;
	private String				countryCode;
	private int					finderTime;
	private int					finderResult;
	private String				welcomeMessage;
	private String				welcomeMessageEs;
	private Double				vatTax;
	private Double				fare;
	private Boolean				rebrandingNotification;


	//Getters y Setters-----------------------------------------------------

	@ElementCollection
	@NotNull
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@SafeHtml
	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@SafeHtml
	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{2}$")
	@SafeHtml
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Min(1)
	@Max(24)
	public int getFinderTime() {
		return this.finderTime;
	}

	public void setFinderTime(final int finderTime) {
		this.finderTime = finderTime;
	}

	@Min(1)
	@Max(100)
	public int getFinderResult() {
		return this.finderResult;
	}

	public void setFinderResult(final int finderResult) {
		this.finderResult = finderResult;
	}

	@DecimalMax(value = "1.0")
	@DecimalMin(value = "0.0")
	public Double getVatTax() {
		return this.vatTax;
	}

	public void setVatTax(final Double vatTax) {
		this.vatTax = vatTax;
	}

	@DecimalMin(value = "0.0")
	public Double getFare() {
		return this.fare;
	}

	public void setFare(final Double fare) {
		this.fare = fare;
	}

	@NotNull
	public Boolean getRebrandingNotification() {
		return this.rebrandingNotification;
	}

	public void setRebrandingNotification(final Boolean rebrandingNotification) {
		this.rebrandingNotification = rebrandingNotification;
	}

	// Relationships ----------------------------------------------------------
}
