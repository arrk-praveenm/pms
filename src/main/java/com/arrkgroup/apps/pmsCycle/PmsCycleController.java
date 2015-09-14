package com.arrkgroup.apps.pmsCycle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arrkgroup.apps.model.Cycle;

@Controller
public class PmsCycleController {

	private static final String PMS_CYCLE_VIEW = "home/CreatePmsCycle";

	@Autowired
	PmsCycleService pmsCycleService;

	@Autowired  // custom validatior 
	private CycleValidator cycleValidator;

	@InitBinder// registering the validator
	public void initValidator(WebDataBinder binder) {
		binder.setValidator(cycleValidator);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));

	}

	@RequestMapping(value = "/hr", method = RequestMethod.GET)
	public String getCycle(Model model) {
		System.out.println("test");
		model.addAttribute("cycleList", pmsCycleService.showList());
		model.addAttribute(new Cycle());

		return PMS_CYCLE_VIEW;
	}

	@RequestMapping(value = "/hr/addCycle", method = RequestMethod.POST)
	public String addCycle(@ModelAttribute("cycle") @Valid Cycle cycle,
			BindingResult result, Model model) throws ParseException {
		System.out.println("id is " + cycle.getId());
		if (result.hasErrors()) {

			model.addAttribute("cycleList", pmsCycleService.showList());
			return "home/CreatePmsCycle";
		}

		if (pmsCycleService.addCycle(cycle)) {

			model.addAttribute("successMessage", "Cycle added successfully");
		} else {

			model.addAttribute("errorMessage", "Please try again");
		}

		model.addAttribute("cycleList", pmsCycleService.showList());

		return PMS_CYCLE_VIEW;
	}

	@RequestMapping(value = "/hr/doDelete", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute Cycle cycle, Model model) {


		if (pmsCycleService.deleteCycle(cycle.getId())) {

			System.out.println("cycle deleted");
			model.addAttribute("successMessage", "Cycle deleted successfully");
		} else {

			model.addAttribute("errorMessage", "Please try again");
		}
		model.addAttribute("cycleList", pmsCycleService.showList());
		return PMS_CYCLE_VIEW;
	}

}
