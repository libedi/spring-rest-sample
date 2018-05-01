/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Jibeom Jung
 */
@Aspect
@Component
public class ServiceAspect {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    //@AfterReturning("execution(* com.skplanet.impay..*Service.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        logger.debug("Completed !!: " + joinPoint);
    }
    
    /**
	 * [Mandatory] Mapper Aspect
	 * 일단은 jointPoint 그대로 출력. 추후에  쿼리 XML ID 출력하도록 수정 예정 
	 * @param    [Optional]설명
	 * @return   void
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	@AfterReturning("execution(* com.skplanet.impay..*Mapper.*(..))")
	public void logMapper(JoinPoint joinPoint) {
		logger.debug("## Mapper Completed !!: " + joinPoint);
	}
	
	/**
	 * [Mandatory] Excel Aspect
	 *
	 * @param    [Optional]설명
	 * @return   void
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	@AfterReturning("execution(* com.skplanet.impay..*ExcelView.*(..))")
	public void logExcel(JoinPoint joinPoint) {
		logger.debug("## Mapper Log Completed !!: " + joinPoint);
	}
	
	/**
	 * [Mandatory] Controller Aspect
	 *
	 * @param    [Optional]설명
	 * @return   void
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	@AfterReturning("execution(* com.skplanet.impay..*Controller.*(..))")
	public void logController(JoinPoint joinPoint) {
		logger.debug("## Controller Log Completed !!: " + joinPoint);
		
//		Object[] args = joinPoint.getArgs();
//		
//		for(int i=0;i<args.length;i++){
//			
//			logger.debug("####");
//			logger.debug(args[i].toString());
//			logger.debug("####");
//			
//		}
		
	}
}
