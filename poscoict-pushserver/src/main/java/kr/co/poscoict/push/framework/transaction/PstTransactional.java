package kr.co.poscoict.push.framework.transaction;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Transactional(transactionManager = "pstTransactionManager")
public @interface PstTransactional {

}
