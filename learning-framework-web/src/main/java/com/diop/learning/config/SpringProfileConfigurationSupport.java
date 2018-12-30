package com.diop.learning.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

/**
 * Utility class to load a Spring profile to be used as default when there is no
 * <code>spring.profiles.active</code> set in the environment or as command line
 * argument. If the value is not available in <code>application.yml</code> then
 * <code>dev</code> profile will be used as default.
 * 
 * @author JAU
 */
public final class SpringProfileConfigurationSupport {

	private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

	private SpringProfileConfigurationSupport() {
	}

	/**
	 * Set a default to use when no profile is configured.
	 *
	 * @param app
	 *            the Spring application
	 */
	public static void addDefaultProfile(SpringApplication app) {
		Map<String, Object> defProperties = new HashMap<>();
		defProperties.put(SPRING_PROFILE_DEFAULT, SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT);
		app.setDefaultProperties(defProperties);
	}

	/**
	 * Get the profiles that are applied else get default profiles.
	 * 
	 * @param env
	 * TODO fill parameter description
	 * 
	 * @return
	 * TODO fill return description
	 */
	public static String[] getActiveProfiles(Environment env) {
		String[] profiles = env.getActiveProfiles();
		if (profiles.length == 0) {
			return env.getDefaultProfiles();
		}
		return profiles;
	}
}
