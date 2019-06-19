package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor{
	

	private String providerMake;

	@NotBlank
	@SafeHtml
	public String getProviderMake() {
		return providerMake;
	}

	public void setProviderMake(String providerMake) {
		this.providerMake = providerMake;
	}
	
	
	

}
