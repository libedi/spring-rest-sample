package kr.co.poscoict.push.common.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import kr.co.poscoict.push.common.util.ListPatternValidator;

/**
 * ListPattern annotation.
 * The regular expression follows the Java regular expression conventions
 * see {@link java.util.regex.Pattern}.
 * @author Sangjun, Park
 *
 */
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ListPatternValidator.class})
public @interface ListPattern {
	
	/**
	 * @return the regular expression to match
	 */
	String regexp();
	
	/**
	 * @return the error message template
	 */
	String message() default "Some items are not valid";
	
	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default { };

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default { };
}
