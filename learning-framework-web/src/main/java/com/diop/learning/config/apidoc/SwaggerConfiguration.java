package com.diop.learning.config.apidoc;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import com.diop.learning.config.LearningProperties;
import com.diop.learning.config.SpringProfileConstants;
import com.google.common.base.Strings;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springfox Swagger configuration.
 *
 * @author djibi
 */
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
@Profile(SpringProfileConstants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(SwaggerConfiguration.class);

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	/**
	 * Swagger Springfox configuration.
	 *
	 * @param learningProperties
	 *            the properties of the application
	 * @return the Swagger Springfox configuration
	 */
	@Bean
	public Docket swaggerSpringfoxDocket(LearningProperties learningProperties) {
		LOG.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		Contact contact = new Contact(learningProperties.getSwagger().getContactName(), learningProperties.getSwagger().getContactUrl(), learningProperties.getSwagger().getContactEmail());

		ApiInfoBuilder builder = new ApiInfoBuilder().title(learningProperties.getSwagger().getTitle()).description(learningProperties.getSwagger().getDescription()).version(learningProperties.getSwagger().getVersion())
				.termsOfServiceUrl(learningProperties.getSwagger().getTermsOfServiceUrl()).license(learningProperties.getSwagger().getLicense()).licenseUrl(learningProperties.getSwagger().getLicenseUrl());

		if (!Strings.isNullOrEmpty(contact.getName())) {
			builder.contact(contact);
		}

		// Add header
		ParameterBuilder aChannelParameterBuilder = new ParameterBuilder();
		ParameterBuilder aUserIdParameterBuilder = new ParameterBuilder();
		aUserIdParameterBuilder.name("userId").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        aChannelParameterBuilder.name("channel").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aChannelParameterBuilder.build());
        aParameters.add(aUserIdParameterBuilder.build());
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(builder.build()).forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class).select().paths(regex(DEFAULT_INCLUDE_PATTERN)).build()
				.globalOperationParameters(aParameters);
		watch.stop();
		LOG.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}
}