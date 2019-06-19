package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Provider;


@Component
@Transactional
public class ProviderToStringConverter implements Converter<Provider, String>{
	
	@Override
	public String convert(final Provider provider) {
		String result;

		if (provider == null)
			result = null;
		else
			result = String.valueOf(provider.getId());

		return result;
	}

}
