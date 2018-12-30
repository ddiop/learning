package com.diop.learning.aop.logging;

import org.aspectj.lang.JoinPoint;

/**
 *
 * @author djibi
 *
 */
public interface LoggingListener {
	void onError(JoinPoint joinPoint, Throwable e, LayerMarker marker);

	void beforeExecute(JoinPoint joinPoint, LayerMarker marker);

	void afterExecute(JoinPoint joinPoint, Object result, LayerMarker marker);
}
