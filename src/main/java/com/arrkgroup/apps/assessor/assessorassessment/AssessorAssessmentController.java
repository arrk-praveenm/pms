package com.arrkgroup.apps.assessor.assessorassessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.EmployeeBean;
import com.arrkgroup.apps.form.SectionDataBean;
import com.arrkgroup.apps.hr.managesections.SectionService;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class AssessorAssessmentController {
	private final String ASSESSORASSESSMENT="assessor/AssessorAssessment";
	private final String LOGIN="login/login";
	


	@Autowired
	SectionService sectionService;
	
	@Autowired
	AssessorAssessmentService assessorAssessmentService;
	
	@Autowired
	ModelObjectService modelObjectService;
	
	InetOrgPerson userDetails = null;
	boolean isAssessorOREmployee=false;
	
	
	
	

//For Manager or Assessor tab initial load  
	@RequestMapping(value = "/assessor/assessorAssessment", method = RequestMethod.GET)
	public String loadCreateSectionPage(Principal principal,
			Model model, HttpSession session ) {
		
	
		userDetails=(InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		model.addAttribute("SectionDataBean", new SectionDataBean());
		
		
		int sectionToLoad=0;
		
		isAssessorOREmployee=true;
		
		setDefaultLoad(model, sectionToLoad, userDetails, isAssessorOREmployee);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
		}
	
	
	//For Employee tab initial load  
	@RequestMapping(value = "/assessor/assesseeRating", method = RequestMethod.GET)
	public String loadAssesseeSelfRating(Principal principal,
			Model model, HttpSession session ) {

		 userDetails = (InetOrgPerson)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		model.addAttribute("SectionDataBean", new SectionDataBean());
	
		isAssessorOREmployee=false;
	
		
	
		int sectionToLoad=0;
		
		setDefaultLoad(model, sectionToLoad, userDetails, isAssessorOREmployee);

		return principal != null ? ASSESSORASSESSMENT : LOGIN;
		}
	
	@RequestMapping(value = "/assessor/ajax/assesseeObjectives", method = RequestMethod.GET)
	public @ResponseBody List<AssesseeObjectives> loadAssignedObjectivesAssessee(
			@RequestParam("selectedAsseesseID") String selectedAsseesseID,
			@RequestParam("sectionToLoad") String sectionToLoad,
			@RequestParam("role_id") String role_id,Model model)
	{
		
		
		System.out.println("data  is "+selectedAsseesseID  +"  "+sectionToLoad+"  "+role_id);
		
		
		
		int selectedAsseesseeID=Integer.parseInt(selectedAsseesseID);
		int sectionToLoadInt=Integer.parseInt(sectionToLoad);
		
		System.out.println(" request for objectives to load for section id is "
				+ sectionToLoad);
		System.out.println(" before  employee assessor id is ");
		 
		
		return assessorAssessmentService.getAssesseeObjectives(sectionToLoadInt, selectedAsseesseeID,Integer.parseInt(role_id));
		
	
		
		
	}
	

	
	private void setDefaultLoad(Model model,  int sectionIDtoLoad, InetOrgPerson userDetails,boolean isAssessorOREmployee)
	{
		
		List<EmployeeBean> assessorAssessees=null;
		List<Role> allRolesofCurrentUser=null;
		
		List<AssesseeObjectives> allObjectives =	null;

		List<Section> allSections = assessorAssessmentService.getAllSections();
		if(sectionIDtoLoad==0)
		{
		sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}
	
		if(isAssessorOREmployee)
		{
		List<AssesseesAssessor> employeeAssessees=assessorAssessmentService.getMyAssessees(userDetails.getMail().toString());
		assessorAssessees=new ArrayList<EmployeeBean>();
		for (AssesseesAssessor assessees : employeeAssessees) {
			
			EmployeeBean employee=new EmployeeBean();
			Role role=new Role();
			 role=modelObjectService.findRoleById(assessees.getRoleId().getId());
			 employee=assessorAssessmentService.getAssesseeBean(assessees.getAssesseeId().getId());
			
			 String Name=employee.getFullname();
				employee.setFullname(Name+"    "+ "  "+role.getTitle());
			   employee.setRole_id(role.getId());
				assessorAssessees.add(employee);
				
			//	allObjectives =null;//	modelObjectService.getObjectiveBySectionId(sectionIDtoLoad);
		}
		}else{
			
			 allRolesofCurrentUser=assessorAssessmentService.getRoleOfCurrentUser(userDetails.getMail());
			 
			 if(allRolesofCurrentUser.size()==1)
			 {
			 allObjectives =assessorAssessmentService.getAssesseeObjectives(sectionIDtoLoad, modelObjectService.findEmployeeByEmail(userDetails.getMail()).getId(), allRolesofCurrentUser.get(0).getId());
			 }
					 //modelObjectService.getObjectiveBySectionId(sectionIDtoLoad);
			System.out.println("User Roles Size"+allRolesofCurrentUser.size());
		}
	
		
		
		model.addAttribute("selectedAsseses", 0);
		model.addAttribute("sectionToLoad", sectionIDtoLoad);
		model.addAttribute("allSections", allSections);
		model.addAttribute("allObjectives", allObjectives);
		model.addAttribute("allRolesofCurrentUser", allRolesofCurrentUser);
		model.addAttribute("assessorAssessees", assessorAssessees);
	
		System.out.println("in setdefault method  ends");
	}
	
	
	

	@RequestMapping(value = "/assessor/SaveSectionData", method = RequestMethod.POST)
	private String SaveSectionData(@ModelAttribute("SectionDataBean") SectionDataBean bean,Principal principal,Model model)
	{
		
		System.out.println("manager rating is  "+bean.getManager_rating());
		System.out.println("manager rating is  "+bean.getManager_comments());
	
		
		
		boolean flag= assessorAssessmentService.saveSectionData(bean);
		
		System.out.println("flag is "+flag);
		
		
		return principal != null ? ASSESSORASSESSMENT : LOGIN;
	}
	

	
	
}
