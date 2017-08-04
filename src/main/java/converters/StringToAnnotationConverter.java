package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AnnotationRepository;
import domain.Annotation;



@Component
@Transactional
public class StringToAnnotationConverter  implements Converter<String, Annotation> {
	
	@Autowired
	AnnotationRepository annotationRepository;
	
	@Override
	public Annotation convert(String text) {
		Annotation result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =annotationRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}