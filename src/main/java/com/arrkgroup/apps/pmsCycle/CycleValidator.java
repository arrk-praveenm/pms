package com.arrkgroup.apps.pmsCycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.pmsCycle.PmsCycleDao;

@Component
public class CycleValidator implements Validator {

	@Autowired
	private PmsCycleDao pmsCycleDao;

	public boolean supports(Class<?> arg0) {
		return Cycle.class.isAssignableFrom(arg0);
	}

	public void validate(Object arg0, Errors errors) {

		Cycle cycle = (Cycle) arg0;
		Cycle curentCycle = pmsCycleDao.currentCycle();

		// check empty start date and end date
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start_date",
				"NotEmpty.start_date", "enter start date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "end_date",
				"NotEmpty.end_date", "enter end date");

		if (errors.hasErrors()) {

			return;
		}

		// check start date should be before end date
		if (cycle.getStart_date().after(cycle.getEnd_date())) {
			System.out.println("start date should be before the end date");
			errors.rejectValue("start_date", "check.Date",
					"start date should be before the end date");

			return;

		}

		// check for cycle with 'current' status
		if (pmsCycleDao.checkCycleStatus()) {
			System.out.println(" current cycle is opened  ");

			errors.rejectValue("end_date", "cycle.status");
			return;
		}

		// check for overlapping dates
		if (curentCycle.getEnd_date().after(cycle.getStart_date())) {
			System.out.println("current cycle overlapping previous cycle");

			errors.rejectValue("end_date", "check.OverlapDate");
			return;
		}

	}

}
