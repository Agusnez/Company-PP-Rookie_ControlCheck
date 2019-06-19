package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Audit;

public class AuditToStringConverter implements Converter<Audit, String>{

	@Override
	public String convert(final Audit audit) {
		String result;

		if (audit == null)
			result = null;
		else
			result = String.valueOf(audit.getId());

		return result;
	}
}
