
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Configuration;

@Component
@Transactional
public class ConfigurationToStringConverter implements Converter<Configuration, String> {

	@Override
	public String convert(final Configuration configuration) {
		String result;

		if (configuration == null)
			result = null;
		else
			result = String.valueOf(configuration.getId());

		return result;
	}

}
