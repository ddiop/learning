package com.diop.learning.aop.logging;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 *
 * @author djibi
 */
@Aspect
public class LoggingExpositionInterceptor {
	@Inject
	private LoggingListener listener;

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(com.diop.learning..exposition..*)")
	public void loggingResourcePointcut() {
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint
	 *            TODO fill parameter description
	 *
	 * @param e
	 *            TODO fill parameter description
	 */
	@AfterThrowing(pointcut = "loggingResourcePointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		listener.onError(joinPoint, e, LayerMarker.RESOURCE);
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint
	 *            TODO fill parameter description
	 *
	 * @return TODO fill return description
	 *
	 * @throws Throwable
	 *             TODO fill exception description
	 */
	@Around("loggingResourcePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		listener.beforeExecute(joinPoint, LayerMarker.RESOURCE);
		Object result = joinPoint.proceed();
		listener.afterExecute(joinPoint, result, LayerMarker.RESOURCE);
		return result;
	}
}
