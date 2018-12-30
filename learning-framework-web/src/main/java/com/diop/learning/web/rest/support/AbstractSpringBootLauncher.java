package com.diop.learning.web.rest.support;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import com.diop.learning.config.SpringProfileConfigurationSupport;
import com.diop.learning.config.SpringProfileConstants;

/**
 * Abstract class to launch a Spring boot application
 * 
 * @author djibi
 *
 */
public abstract class AbstractSpringBootLauncher extends SpringBootServletInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringBootLauncher.class);

	// Annotation based on JSR-330 (CDI)
	@Inject
	private Environment env;

	@PostConstruct
	public void initApplication() {
		LOG.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(SpringProfileConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SpringProfileConstants.SPRING_PROFILE_PRODUCTION)) {
			LOG.error("Change the configuration of your application, it sould not run with both the 'dev' and 'prod' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param appClass
	 * TODO fill parameter description
	 *
	 * @param args
	 *            the command line arguments
	 * @throws UnknownHostException
	 *             if the local host name could not be resolved into an address
	 */
	public static void launch(Class<?> appClass, String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(appClass);
		SpringProfileConfigurationSupport.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		LOG.info(
				"\n----------------------------------------------------------\n\t" + "Application {} is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));

	}
}
