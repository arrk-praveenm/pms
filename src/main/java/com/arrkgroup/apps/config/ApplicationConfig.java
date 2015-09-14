package com.arrkgroup.apps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import com.arrkgroup.apps.Application;
import com.arrkgroup.apps.form.validator.AddObjectiveValidator;
import com.arrkgroup.apps.form.validator.CreateSectionValidator;

@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {
	//@Value("${notBlank.message}")
	//String validation;
	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("/persistence.properties"));
		return ppc;
	}
	
  /*  @Bean
    public CreateSectionValidator createSectionValidator()
    {
    	
    	return new CreateSectionValidator(validation);
    }
    
    @Bean
    public AddObjectiveValidator addObjectiveValidator()
    {
    	return new AddObjectiveValidator();
    }*/
	
}