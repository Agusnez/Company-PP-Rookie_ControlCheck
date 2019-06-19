
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.EducationData;

@Component
@Transactional
public class EducationDataToStringConverter implements Converter<EducationData, String> {

	@Override
	public String convert(final EducationData educationData) {
		String result;

		if (educationData == null)
			result = null;
		else
			result = String.valueOf(educationData.getId());

		return result;
	}

}
