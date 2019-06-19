
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData> {

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;


	@Override
	public MiscellaneousData convert(final String text) {
		MiscellaneousData result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.miscellaneousDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
