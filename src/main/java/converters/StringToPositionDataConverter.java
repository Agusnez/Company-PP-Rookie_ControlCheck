
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PositionDataRepository;
import domain.PositionData;

@Component
@Transactional
public class StringToPositionDataConverter implements Converter<String, PositionData> {

	@Autowired
	private PositionDataRepository	positionDataRepository;


	@Override
	public PositionData convert(final String text) {
		PositionData result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.positionDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
