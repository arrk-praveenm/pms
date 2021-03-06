package com.arrkgroup.apps.hr.showReports;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.arrkgroup.apps.assessor.assessorassessment.AssessorAssessmentService;
import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.form.ShowReportBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class ShowReportController {

	private final Logger log = LoggerFactory.getLogger(CreateSectionController.class);
	private static final String SHOW_REPORT__VIEW = "hr/showReport/showAssessmentReport";

	@Autowired
	ModelObjectService modelObjectService;

	@Autowired
	ShowReportService showReportService;



	@Autowired
	AssessorAssessmentService assessorAssessmentService;

	InetOrgPerson userDetails = null;

	@RequestMapping(value = "/hr/showReport", method = RequestMethod.GET)
	public String hrShowReport(Model model) {

		log.info(" show report");

		model.addAttribute("ShowReportBean", new ShowReportBean());
		log.info(" reports size is  " + modelObjectService.getAllCycles().size());
		model.addAttribute("cyclelist", modelObjectService.getAllCycles());

		model.addAttribute("isHr", true);

		return SHOW_REPORT__VIEW;
	}

	@RequestMapping(value = "/assessor/showReport", method = RequestMethod.GET)
	public String assessorShowReport(Model model) {

		log.info("assessor show report method");
		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal());

		log.info(" show report");

		model.addAttribute("ShowReportBean", new ShowReportBean());
		log.info(" reports size is  " + modelObjectService.getAllCycles().size());

		model.addAttribute("cyclelist", modelObjectService.getAllCycles());

		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal());

		Employee manager = modelObjectService.findEmployeeByEmail(userDetails.getMail());

		log.info("manager id is " + manager.getId());
		model.addAttribute("isManager", true);
		model.addAttribute("managerid", manager.getId());

		return SHOW_REPORT__VIEW;
	}

	@RequestMapping(value = "/hr/showReport", method = RequestMethod.POST)
	public String assign_Objectives_to_Role_POST(@ModelAttribute("ShowReportBean") ShowReportBean bean,
	BindingResult result, Model model) {
		log.info(" cycle id is   " + bean.getCycle_id());
		log.info(" manager  id is   " + bean.getManager_id());

		model.addAttribute("cyclelist", modelObjectService.getAllCycles());

		model.addAttribute("allEmployee",
		showReportService.showEmployeeByCycle(bean.getCycle_id()));

		return SHOW_REPORT__VIEW;
	}

	@RequestMapping(value = "/assessor/showReport", method = RequestMethod.POST)
	public String assesssorshowreport(@ModelAttribute("ShowReportBean") ShowReportBean bean,
	BindingResult result, Model model) {

		Employee manager = modelObjectService.findEmployeeByEmail(userDetails.getMail());

		log.info(" cycle id is   " + bean.getCycle_id());
		log.info(" manager  id is   " + bean.getManager_id_assessor());

		model.addAttribute("cyclelist", modelObjectService.getAllCycles());

		model.addAttribute("allassessor",
		showReportService.showAssessorByCycleManager(
		bean.getCycle_id(), manager.getId()));

		log.info("manager id is " + manager.getId());
		model.addAttribute("isManager", true);
		model.addAttribute("managerid", manager.getId());

		return SHOW_REPORT__VIEW;
	}

	@RequestMapping(value = "/hr/showAssesses", method = RequestMethod.GET)
	public@ResponseBody
	java.util.List < ShowReportBean > getObjective_Section(@RequestParam(value = "assessor_id") String assessor_id, @RequestParam(value = "cycle_id") String cycle_id, Model model) {

		log.info(" assessor  id is   " + assessor_id);

		List < ShowReportBean > beans = showReportService.showAssessesByAssessor(
		Integer.parseInt(assessor_id), Integer.parseInt(cycle_id));

		return beans;
	}

	@RequestMapping(value = "/hr/downloadpdfDetail", method = RequestMethod.GET)
	public ModelAndView downloadpdf(@RequestParam(value = "AsseesseID") String assessor_id, @RequestParam(value = "cycleID") String cycleID, Model model) {

		System.out.println(" /hr/downloadpdfDetail  action  - assessor  id is   " + assessor_id + " cycle id is " + cycleID);

		List < Section > allSections = showReportService.getAllSections();
		List allSectionAssessmentScore = new ArrayList();
		for (AssesseesAssessor assesseesAssessor: (List < AssesseesAssessor > ) showReportService.getAssesseesAssessorByCycle(Integer.parseInt(assessor_id),
		Integer.parseInt(cycleID))) {
			List < SectionConsolidatedBean > list = assessorAssessmentService.findById(String.valueOf(assesseesAssessor.getAssessorId()
				.getId()), String.valueOf(assesseesAssessor.getRoleId().getId()), assesseesAssessor.getProjectId().getId(), assesseesAssessor.getId());
			allSectionAssessmentScore.add(list);
		}
		System.out.println("downloadpdfDetail  allSectionAssessmentScore size is " + allSectionAssessmentScore.size());
		model.addAttribute("allSectionAssessmentScore",
		allSectionAssessmentScore);

		String manager = modelObjectService.findEmployeeById(
		modelObjectService.findEmployeeById(
		Integer.parseInt(assessor_id)).getReporting_manager())
			.getFullname();

		model.addAttribute("MAX_RATING", modelObjectService.getMaxRating());
		model.addAttribute("manager", manager);
		model.addAttribute("allSectionAssessmentScore",
		allSectionAssessmentScore);
		model.addAttribute("type", "detail");
		model.addAttribute("empid", Integer.parseInt(assessor_id));
		model.addAttribute(
			"empname",
		modelObjectService.findEmployeeById(
		Integer.parseInt(assessor_id)).getFullname());
		model.addAttribute("cycle",
		modelObjectService.findCycleById(Integer.parseInt(cycleID))
			.getDescription());

		System.out.println("1");
		model.addAttribute("assesseinfo", showReportService.getAssesseesAssessorByCycle(Integer.parseInt(assessor_id),Integer.parseInt(cycleID)));
		System.out.println("2");


		model.addAttribute("pdfdetailsview", showReportService.showAssesseDetailsByEmplyee(Integer.parseInt(assessor_id)));
		System.out.println("3");

		// return a view which will be resolved by a pdf view resolver
		return new ModelAndView("pdfView", "allSections", allSections);
	}

	@RequestMapping(value = "/hr/downloadpdfRating", method = RequestMethod.GET)
	public ModelAndView downloadpdfrating(@RequestParam(value = "AsseesseID") String assessor_id, @RequestParam(value = "cycleID") String cycleID, Model model) {

		System.out.println(" /hr/downloadpdfRating  action  - assessor  id is   " + assessor_id + " cycle id is " + cycleID);

		List < Section > allSections = showReportService.getAllSections();
		List allSectionAssessmentScore = new ArrayList();
		for (AssesseesAssessor assesseesAssessor: (List < AssesseesAssessor > ) showReportService.getAssesseesAssessorByCycle(Integer.parseInt(assessor_id),
		Integer.parseInt(cycleID))) {

			log.info("assessor id is  " + assesseesAssessor.getAssessorId());

			List < SectionConsolidatedBean > list = assessorAssessmentService.findById(String.valueOf(assesseesAssessor.getAssessorId()
				.getId()), String.valueOf(assesseesAssessor.getRoleId().getId()), assesseesAssessor.getProjectId().getId(), assesseesAssessor.getId());
			allSectionAssessmentScore.add(list);

			log.info("records are " + list.size());

		}

		String manager = modelObjectService.findEmployeeById(
		modelObjectService.findEmployeeById(
		Integer.parseInt(assessor_id)).getReporting_manager())
			.getFullname();

		model.addAttribute("MAX_RATING", modelObjectService.getMaxRating());
		model.addAttribute("manager", manager);
		model.addAttribute("allSectionAssessmentScore",
		allSectionAssessmentScore);
		model.addAttribute("type", "rating");
		model.addAttribute("empid", Integer.parseInt(assessor_id));
		model.addAttribute(
			"empname",
		modelObjectService.findEmployeeById(
		Integer.parseInt(assessor_id)).getFullname());
		model.addAttribute("cycle",
		modelObjectService.findCycleById(Integer.parseInt(cycleID))
			.getDescription());

		model.addAttribute("assesseinfo", showReportService.getAssesseesAssessorByCycle(Integer.parseInt(assessor_id),Integer.parseInt(cycleID)));

		// return a view which will be resolved by a pdf view resolver
		return new ModelAndView("pdfView", "allSections", allSections);
	}

	@RequestMapping(value = "/assessor/assesseeshowReport", method = RequestMethod.GET)
	public String assesseeShowReport(Model model) {

		log.info("assessee show report method");
		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal());

		model.addAttribute("ShowReportBean", new ShowReportBean());

		model.addAttribute("cyclelist", modelObjectService.getAllCycles());

		userDetails = (InetOrgPerson)(SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal());

		Employee manager = modelObjectService.findEmployeeByEmail(userDetails.getMail());

		log.info("manager id is " + manager.getId());
		model.addAttribute("isManager", true);
		model.addAttribute("isEmployee", true);
		model.addAttribute("managerid", manager.getId());

		return SHOW_REPORT__VIEW;
	}

}