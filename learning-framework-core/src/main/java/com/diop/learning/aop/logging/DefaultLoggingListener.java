package com.diop.learning.aop.logging;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.diop.learning.config.SpringProfileConstants;

/**
 *
 * @author djibi
 *
 */
@Component
public class DefaultLoggingListener implements LoggingListener {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultLoggingListener.class);
	private static final String MARKER = "Marker";

	@Inject
	private Environment env;

	@Override
	public void onError(JoinPoint joinPoint, Throwable e, LayerMarker marker) {
		MDC.put(MARKER, marker.getLabel());
		if (env.acceptsProfiles(SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT, SpringProfileConstants.SPRING_PROFILE_MOCK)) {
			LOG.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
		} else {
			LOG.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
		}
		MDC.remove(MARKER);
	}

	@Override
	public void beforeExecute(JoinPoint joinPoint, LayerMarker marker) {
		if (LOG.isDebugEnabled()) {
			MDC.put(MARKER, marker.getLabel());
			LOG.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
			MDC.remove(MARKER);
		}
	}

	@Override
	public void afterExecute(JoinPoint joinPoint, Object result, LayerMarker marker) {
		if (LOG.isDebugEnabled()) {
			MDC.put(MARKER, marker.getLabel());
			LOG.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result);
			MDC.remove(MARKER);
		}
	}

}