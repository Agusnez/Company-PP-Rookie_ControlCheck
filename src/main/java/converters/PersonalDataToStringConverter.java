
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PersonalData;

@Component
@Transactional
public class PersonalDataToStringConverter implements Converter<PersonalData, String> {

	@Override
	public String convert(final PersonalData personalData) {
		String result;

		if (personalData == null)
			result = null;
		else
			result = String.valueOf(personalData.getId());

		return result;
	}

}
