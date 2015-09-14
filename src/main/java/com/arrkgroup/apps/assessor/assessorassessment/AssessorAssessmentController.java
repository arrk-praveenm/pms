package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.hr.managesections.SectionService;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Section;

@Controller
public class AssessorAssessmentController {
	private final String ASSESSORASSESSMENT="assessor/AssessorAssessment";
	private final String LOGIN="login/login";


	@Autowired
	SectionService sectionService;
	
	@Autowired
	AssessorAssessmentService assessorAssessmentService;
	
private int sectionToLoad=0;

	@RequestMapping(value = "/assessor/assessorAssessment", method = RequestMethod.GET)
	public String loadCreateSectionPage(Principal principal,
			Map<String, Object> model, HttpSession session ) {

		System.out.println(session.getAttribute("userEmailId"));
		
		
		setDefaultLoad(model,"jeevankumar.reddy@arrkgroup.com", sectionToLoad);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
		}
	
	@RequestMapping(value = "/assessor/ajax/assesseeObjectives", method = RequestMethod.GET)
	public @ResponseBody List<Objective> loadAssignedObjectivesAssessee(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("sectionToLoad") String sectionToLoad)
	{
		int selectedAsseesseeID=Integer.parseInt(selectedAsseesseID);
		int sectionToLoadInt=Integer.parseInt(sectionToLoad);
		List<Objective> allsectionObjectives=null;
		if(selectedAsseesseeID==0)
		{
			//Load Section sepcific objectives
			
			allsectionObjectives = sectionService
					.getObjectivesBySection(Integer.parseInt(sectionToLoad));
			
		}else
		{
			//Load section based and Assessee specific objectives
			allsectionObjectives = sectionService
					.getObjectivesBySection(Integer.parseInt(sectionToLoad));
			
		}
		
		
		
		System.out.println(" request for objectives to load for section id is "
				+ sectionToLoad);
		
		return allsectionObjectives;
	}
	

	@RequestMapping(value = "/assessor/ajax/assignedObjectives", method = RequestMethod.GET)
	private void setDefaultLoad(Map model, Object object, int sectionIDtoLoad)
	{
		List<AssesseesAssessor> employeeAssessees=assessorAssessmentService.getMyAssessees(object.toString());
		Iterator<AssesseesAssessor> itr=employeeAssessees.iterator();
		List<Employee> assessorAssessees=new ArrayList<Employee>();
		System.out.println("1");
		while(itr.hasNext())
		{
			
			assessorAssessees.add(assessorAssessmentService.getAssessee(itr.next().getAssesseeId().getId()));
		}
		List<Section> allSections = assessorAssessmentService.getAllSections();
		if(sectionIDtoLoad==0)
		{
		sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}
		
		List<Objective> allObjectives =	sectionService.getObjectivesBySection(sectionIDtoLoad);
		model.put("selectedAsseses", 0);
		model.put("sectionToLoad", sectionIDtoLoad);
		model.put("allSections", allSections);
		model.put("allObjectives", allObjectives);
		model.put("assessorAssessees", assessorAssessees);
		
	}
	
}
