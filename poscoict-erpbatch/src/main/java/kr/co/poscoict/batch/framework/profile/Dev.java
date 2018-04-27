package kr.co.poscoict.batch.framework.profile;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile - Development
 * @author Sangjun, Park
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Profile("dev")
public @interface Dev {

}
