package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.form.AssessorAssessmentBean;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;

import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.SectionConsolidated;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class AssessorAssessmentController {
	private final String ASSESSORASSESSMENT = "assessor/AssessorAssessment";
	private final String LOGIN = "login/login";

	private final Logger log = LoggerFactory
			.getLogger(AssessorAssessmentController.class);

	@Autowired
	AssessorAssessmentService assessorAssessmentService;

	@Autowired
	ModelObjectService modelObjectService;

	@Autowired
	AssessorAssessmentDao assessorAssessmentDao;

	@RequestMapping(value = "/assessor/assessorAssessment", method = RequestMethod.GET)
	public String loadCreateSectionPage(Principal principal, Model model,
			HttpSession session) {

		model.addAttribute("AssessorAssessmentBean",
				new AssessorAssessmentBean());

		int sectionToLoad = 0;

		setDefaultLoad(model, "mahesh.mohite@arrkgroup.com", sectionToLoad);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}

	@RequestMapping(value = "/assessor/ajax/assesseeObjectives", method = RequestMethod.GET)
	public @ResponseBody
	List<AssesseeObjectives> loadAssignedObjectivesAssessee(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("sectionToLoad") String sectionToLoad,
			@RequestParam("role_id") String role_id, Model model) {

		log.info("data  is " + selectedAsseesseID + "  " + sectionToLoad + "  "
				+ role_id);

		int selectedAsseesseeID = Integer.parseInt(selectedAsseesseID);
		int sectionToLoadInt = Integer.parseInt(sectionToLoad);

		if (sectionToLoadInt == 0) {
			sectionToLoadInt = ((Section) assessorAssessmentService
					.getAllSections().get(0)).getId();
		}

		log.info(" request for objectives to load for section id is "
				+ sectionToLoad);

		return assessorAssessmentService.getAssesseeObjectives(
				sectionToLoadInt, selectedAsseesseeID,
				Integer.parseInt(role_id));

	}

	@RequestMapping(value = "/assessor/ajax/GetSummaryRating", method = RequestMethod.GET)
	public @ResponseBody
	List<SectionConsolidatedBean> getsummarydata(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("role_id") String role_id, Model model) {

		log.info("in GetSummaryRating method" + selectedAsseesseID + ""
				+ role_id);

		List<SectionConsolidatedBean> list = assessorAssessmentService
				.findById(selectedAsseesseID, role_id);

		log.info("in GetSummaryRating method  list size is " + list.size());

		return list;
	}

	@RequestMapping(value = "/assessor/ajax/assignedObjectives", method = RequestMethod.GET)
	private void setDefaultLoad(Model model, Object object, int sectionIDtoLoad) {

		List<AssesseesAssessor> employeeAssessees = assessorAssessmentService
				.getMyAssessees(object.toString());
		List<EmployeeBean> assessorAssessees = new ArrayList<EmployeeBean>();

		for (AssesseesAssessor assessees : employeeAssessees) {

			EmployeeBean employee = new EmployeeBean();
			Role role = new Role();
			role = modelObjectService.findRoleById(assessees.getRoleId()
					.getId());
			employee = assessorAssessmentService.getAssesseeBean(assessees
					.getAssesseeId().getId());

			String Name = employee.getFullname();
			employee.setFullname(Name + "    " + "  " + role.getTitle());
			employee.setRole_id(role.getId());

			assessorAssessees.add(employee);

		}

		List<Section> allSections = assessorAssessmentService.getAllSections();

		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}

		model.addAttribute("sectionToLoad", sectionIDtoLoad);
		log.info("section to load is " + sectionIDtoLoad);

		model.addAttribute("allSections", allSections);
		model.addAttribute("allSectionsBeans",
				assessorAssessmentService.getAllSectionsBean());
		/* model.put("allObjectives", allObjectives); */
		model.addAttribute("assessorAssessees", assessorAssessees);

		model.addAttribute("managerRating", modelObjectService.getAllRatings());
		model.addAttribute("weightageList",
				modelObjectService.getAllWeightages());

	}

	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST)
	private String SaveSectionData(
			@ModelAttribute("AssessorAssessmentBean") AssessorAssessmentBean bean,
			BindingResult error, Model model, Principal principal) {

		log.info("manager rating is  " + bean.getManager_rating());
		log.info("manager rating is  " + bean.getManager_comments());
		log.info("weightafe is  " + bean.getWeightage());

		model.addAttribute("roleid", bean.getRoleid());
		log.info("role id " + bean.getRoleid());

		boolean flag = assessorAssessmentService.saveSectionData(bean);

		setDefaultLoad(model, "mahesh.mohite@arrkgroup.com",
				bean.getSectionid());

		assessorAssessmentService.saveSectionSummary(bean.getSectionid(),
				bean.getEmployee_id(), bean.getRoleid());

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}

}
