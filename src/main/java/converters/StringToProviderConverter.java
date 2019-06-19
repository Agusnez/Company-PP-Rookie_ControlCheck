package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ProviderRepository;
import domain.Provider;

@Component
@Transactional
public class StringToProviderConverter implements Converter<String, Provider>{
	
	@Autowired
	private ProviderRepository	providerRepository;
	
	@Override
	public Provider convert(final String text) {
		Provider result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.providerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
