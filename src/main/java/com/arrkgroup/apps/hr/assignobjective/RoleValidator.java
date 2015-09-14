package com.arrkgroup.apps.hr.assignobjective;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;

@Component
public class RoleValidator implements Validator {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	
	@Autowired
	RoleDao roleDao;

	@Autowired
	RoleService roleService;

	public boolean supports(Class clazz) {
		log.info("in supports method");
		return RoleObjectivesBean.class.equals(clazz);
	}

	public void validate(Object arg0, Errors errors) {
		log.info("in validate method");
		RoleObjectivesBean bean = (RoleObjectivesBean) arg0;

		// check for valid roles selection

		if (bean.getRole_id() == 0) {
			log.info("enter the role ");
			errors.rejectValue("multiselect_to", "role.select");
			return;

		}
		// check for empty selected objective list
		// if there is no objectives present then it gives validation message
		if (bean.getMultiselect_to() == null) {

			if (roleService.checkRoleObjectives(bean)) {
				log.info("no   objectives for  role in db");

				errors.rejectValue("multiselect_to", "objectives.empty");

				return;

			} else {
				

				return;
			}

		}

	}

}