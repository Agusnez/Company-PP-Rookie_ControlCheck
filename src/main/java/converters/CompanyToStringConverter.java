
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Company;

@Component
@Transactional
public class CompanyToStringConverter implements Converter<Company, String> {

	@Override
	public String convert(final Company company) {
		String result;

		if (company == null)
			result = null;
		else
			result = String.valueOf(company.getId());

		return result;
	}

}
