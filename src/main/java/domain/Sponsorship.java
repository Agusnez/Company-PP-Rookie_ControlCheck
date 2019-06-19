
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private CreditCard	creditCard;
	private String		banner;
	private String		target;
	private Double		cost;
	//relationships
	private Provider	provider;
	private Position	position;


	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@URL
	@SafeHtml
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotNull
	@URL
	@SafeHtml
	public String getTarget() {
		return this.target;
	}
	public void setTarget(final String target) {
		this.target = target;
	}

	@Valid
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}
	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(final Double cost) {
		this.cost = cost;
	}

}
