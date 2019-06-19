
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Problem;

@Component
@Transactional
public class ProblemToStringConverter implements Converter<Problem, String> {

	@Override
	public String convert(final Problem problem) {
		String result;

		if (problem == null)
			result = null;
		else
			result = String.valueOf(problem.getId());

		return result;
	}

}
