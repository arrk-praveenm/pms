package com.arrkgroup.apps.assessor.assessorassessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;

@Component
public class AssessorAssessmentValidator implements Validator {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	

	public boolean supports(Class clazz) {
	
		return AssessorAssessmentBean.class.equals(clazz);
	}

	public void validate(Object arg0, Errors errors) {
		log.info("in validate method");
		AssessorAssessmentBean bean = (AssessorAssessmentBean) arg0;

		// check for valid roles selection

 System.out.println("rating is "+bean.getManager_rating());		
		
		if (bean.getManager_rating() == 0) {
			log.info("enter the rating ");
			errors.rejectValue("manager_rating", "role.select");
			return;

		}
		

	}

}