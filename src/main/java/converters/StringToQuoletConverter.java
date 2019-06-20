
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.QuoletRepository;
import domain.Quolet;

@Component
@Transactional
public class StringToQuoletConverter implements Converter<String, Quolet> {

	@Autowired
	private QuoletRepository	quoletRepository;


	@Override
	public Quolet convert(final String text) {
		Quolet result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.quoletRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
