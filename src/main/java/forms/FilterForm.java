
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.SafeHtml;

@Access(AccessType.PROPERTY)
public class FilterForm {

	public FilterForm() {
		super();
	}


	private String	keyword;
	private String	companyName;


	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@SafeHtml
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

}
