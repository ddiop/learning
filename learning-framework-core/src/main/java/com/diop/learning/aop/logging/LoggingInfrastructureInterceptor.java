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
public class LoggingInfrastructureInterceptor {
	@Inject
	private LoggingListener listener;

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(com.diop.learning..infrastructure..*)")
	public void loggingInfrastructurePointcut() {
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
	@AfterThrowing(pointcut = "loggingInfrastructurePointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		listener.onError(joinPoint, e, LayerMarker.INFRASTRUCTURE);
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
	@Around("loggingInfrastructurePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		listener.beforeExecute(joinPoint, LayerMarker.INFRASTRUCTURE);
		Object result = joinPoint.proceed();
		listener.afterExecute(joinPoint, result, LayerMarker.INFRASTRUCTURE);
		return result;
	}
}
