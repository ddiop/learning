package com.diop.learning.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * @author b51007
 *
 */
public class LocalCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		return !environment.acceptsProfiles(SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT, SpringProfileConstants.SPRING_PROFILE_QUALIFICATION, SpringProfileConstants.SPRING_PROFILE_TEST, SpringProfileConstants.SPRING_PROFILE_PRODUCTION, SpringProfileConstants.SPRING_PROFILE_INTEGRATION);
	}
}