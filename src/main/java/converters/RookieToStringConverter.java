
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Rookie;

@Component
@Transactional
public class RookieToStringConverter implements Converter<Rookie, String> {

	@Override
	public String convert(final Rookie rookie) {
		String result;

		if (rookie == null)
			result = null;
		else
			result = String.valueOf(rookie.getId());

		return result;
	}

}
