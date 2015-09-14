package com.arrkgroup.apps.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.arrkgroup.apps.form.AddObjectiveBean;
@Component
public class AddObjectiveValidator implements Validator {

	@Override
	   public boolean supports(Class clazz) {
     return AddObjectiveBean.class.isAssignableFrom(clazz);
 }

	@Override
	public void validate(Object target, Errors errors)
 {
		
			
		if(target.getClass().getName().equalsIgnoreCase("com.arrkgroup.apps.form.AddObjectiveBean"))
		{
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "objectiveDesc", "notBlank.message", "{notBlank.message}");
		}
   
 }


}
