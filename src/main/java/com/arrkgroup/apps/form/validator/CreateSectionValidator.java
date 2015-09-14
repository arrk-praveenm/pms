package com.arrkgroup.apps.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.arrkgroup.apps.form.CreateSectionBean;

@Component
public class CreateSectionValidator implements Validator {
	

	
	
	
	

	@Override
	   public boolean supports(Class clazz) {
        return CreateSectionBean.class.isAssignableFrom(clazz);
    }
 
	@Override
	public void validate(Object target, Errors errors)
    {
		
			
		if(target.getClass().getName().equalsIgnoreCase("com.arrkgroup.apps.form.CreateSectionBean"))
		{
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sectionName", "notBlank.message", "{notBlank.message}");
		}
     
    }

}
