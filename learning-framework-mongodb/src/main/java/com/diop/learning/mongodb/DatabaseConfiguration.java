package com.diop.learning.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


import com.mongodb.MongoClient;



@Configuration

//@Profile("!" + SpringProfileConstants.SPRING_PROFILE_CLOUD)
//@Import(value = MongoAutoConfiguration.class)
//@EnableMongoRepositories(basePackages = "com.diop.learning")
//
//@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration  {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Value("${spring.data.mongodb.host:127.0.0.1}")    
    private String host;
    @Value("${spring.data.mongodb.port:27017}")    
    private int port;
    @Value("${spring.data.mongodb.database}")    
    private String database;
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

//    @Bean
//    public CustomConversions customConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(DateToZonedDateTimeConverter.INSTANCE);
//        converters.add(ZonedDateTimeToDateConverter.INSTANCE);
//        return new CustomConversions(converters);
//    }

     @Bean
	public MongoTemplate mongoTemplate() throws Exception {
		
		MongoTemplate mongoTemplate = 
			new MongoTemplate(new MongoClient(host),database);
		return mongoTemplate;
		
	}
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		return new MongoClient(host, port);
	}

	
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return database;
	}
	
    public String getMappingBasePackage() {
        return "com.diop.learning";
    }
	
	

//    @Override
//    protected String getDatabaseName() {
//        return "test";
//    }
//  
//    @Override
//    public Mongo mongo() throws Exception {
//        return new MongoClient("127.0.0.1", 27017);
//    }
//  
//    @Override
//    protected String getMappingBasePackage() {
//        return "org.baeldung";
//    }
}
