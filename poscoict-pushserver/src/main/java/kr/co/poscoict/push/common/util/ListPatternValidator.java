package kr.co.poscoict.push.common.util;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kr.co.poscoict.push.common.model.ListPattern;

/**
 * ListPattern annotation validator
 * @author Sangjun, Park
 *
 */
public class ListPatternValidator implements ConstraintValidator<ListPattern, List<String>> {

	private String regexp;
	
	@Override
	public void initialize(ListPattern constraintAnnotation) {
		this.regexp = constraintAnnotation.regexp();
	}

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		return value == null ? true : value.stream().allMatch(item -> Pattern.matches(regexp, item));
	}

}
