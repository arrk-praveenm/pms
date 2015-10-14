package com.arrkgroup.apps.hr.showReports;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.ShowReportBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class ShowReportController {


	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	private static final String SHOW_REPORT__VIEW = "hr/showReport/showAssessmentReport";


	@Autowired
	ModelObjectService modelObjectService;

	@Autowired
	ShowReportService showReportService;


	@RequestMapping(value = "/hr/showReport", method = RequestMethod.GET)
	public String assign_Objectives_to_Role(Model model) {

		System.out.println(" show report");

		model.addAttribute("ShowReportBean",new ShowReportBean() );
		System.out.println(" reports size is  "+modelObjectService.getAllCycles().size());
		model.addAttribute("cyclelist", modelObjectService.getAllCycles());
System.out.println("1");
		model.addAttribute("managerlist", showReportService.showAllManager());
		System.out.println("2");


		return SHOW_REPORT__VIEW;
	}

	@RequestMapping(value = "/hr/showReport", method = RequestMethod.POST)
	public String assign_Objectives_to_Role_POST(
			@ModelAttribute("ShowReportBean") ShowReportBean bean,
			BindingResult result, Model model) {
		System.out.println(" cycle id is   "+ bean.getCycle_id());
		System.out.println(" manager  id is   "+ bean.getManager_id());


		model.addAttribute("cyclelist", modelObjectService.getAllCycles());
		model.addAttribute("managerlist", showReportService.showAllManager());

/*		model.addAttribute("allassessor", showReportService.showEmployeeByCycle(bean.getCycle_id()));*/

		model.addAttribute("allassessor", showReportService.showAssessorByCycleManager(bean.getCycle_id(),bean.getManager_id()));

		/*model.addAttribute("allassesses", showReportService.showAssessesByAssessor(bean.getAssessor_id()));*/


		return SHOW_REPORT__VIEW;
	}


	@RequestMapping(value = "/hr/showAssesses", method = RequestMethod.GET)
	public @ResponseBody
	java.util.List<ShowReportBean> getObjective_Section(
			@RequestParam(value = "assessor_id") String assessor_id,
			@RequestParam(value = "cycle_id") String cycle_id,
			Model model) {

		System.out.println(" assessor  id is   "+ assessor_id);

		List<ShowReportBean> beans=showReportService.showAssessesByAssessor(Integer.parseInt(assessor_id),Integer.parseInt(cycle_id));

		/*model.addAttribute("allassesses",beans);*/


		return beans;
	}


	@RequestMapping(value = "/hr/downloadpdfDetail", method = RequestMethod.GET)
	public @ResponseBody
	ShowReportBean downloadpdf(
			@RequestParam(value = "AsseesseID") String assessor_id,
						Model model) {

		System.out.println(" /hr/downloadpdfDetail  action  - assessor  id is   "+ assessor_id);
ShowReportBean bean =new ShowReportBean();


		return bean;
	}



	@RequestMapping(value = "/hr/downloadpdfRating", method = RequestMethod.GET)
	public @ResponseBody
	ShowReportBean downloadpdfrating(
			@RequestParam(value = "AsseesseID") String assessor_id,
						Model model) {

		System.out.println(" /hr/downloadpdfRating  action  - assessor  id is   "+ assessor_id);
ShowReportBean bean =new ShowReportBean();


		return bean;
	}







	}







