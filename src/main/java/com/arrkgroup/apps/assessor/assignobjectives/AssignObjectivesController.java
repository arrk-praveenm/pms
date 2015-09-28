package com.arrkgroup.apps.assessor.assignobjectives;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.form.AddObjectiveBean;
import com.arrkgroup.apps.form.CopyObjectivesBean;
import com.arrkgroup.apps.form.CreateSectionBean;
import com.arrkgroup.apps.form.validator.AddObjectiveValidator;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Project;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;
import com.arrkgroup.apps.support.web.Messages;

@Controller
public class AssignObjectivesController {
	private final Logger log = LoggerFactory
			.getLogger(AssignObjectivesController.class);
	private final String SUCCESS="success";
	private final String DANGER="danger";
	private final String ASSIGNONJECTIVESTOASSESSE="/assessor/AssignObjectivesToAssesse";
	private final String LOGIN="login/login";
	private static final int DEFAULT_WEIGHTAGE_ID = 1;
	
	@Autowired
	AssignObjectivesService assignObjectivesService;
	
	@Autowired
	ModelObjectService modelObjectService;
	

	@Autowired
	AddObjectiveValidator addObjectiveValidator;
	
	
	@RequestMapping(value = "/assessor/assignobjectives", method = RequestMethod.GET)
	public String showAssignObjectives(Map<String, Object> model,Principal principal) {
		List<Cycle> assessmentCycles = assignObjectivesService.getAllAssessmentCycles();
		List<Employee> assesses = assignObjectivesService.getAllAssesses();
		List<Role> roles = assignObjectivesService.getAllRoles();
		
		List<Section> allSections = modelObjectService.getAllSections();
		model.put("allSections", allSections);
		int sectionIDtoLoad=0;
		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}
		
		model.put("allObjectives",null);
		
		model.put("addObjectiveBean", new AddObjectiveBean());
		model.put("assessmentcycles", assessmentCycles);
		model.put("assesses", assesses);
		model.put("roles", roles);
		model.put("copyObjectivesBean", new CopyObjectivesBean());
		model.put("sectionToLoad", sectionIDtoLoad);
		List<Project> projectList=modelObjectService.getAllProjects();
		model.put("projectList", projectList);
		model.put("allWeightages", modelObjectService.getAllWeightages());
		
		
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;
	}
	
	@RequestMapping(value = "/assessor/copyobjectives", method = RequestMethod.POST)
	public String copyObjectives(
			@ModelAttribute("copyObjectivesBean") CopyObjectivesBean copyObjectivesBean,
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal, Errors errors, 
			@RequestParam(value="type", required=true) String type) {
		log.info("action performed...........:"+ type);
		
		log.info("inside copy role objectives........");
		log.info("Assessor: "+copyObjectivesBean.getAssessor());
		log.info("Assessee: "+copyObjectivesBean.getAssessee());
		log.info("Role: "+copyObjectivesBean.getAssesseeRole());
		log.info("Project: "+copyObjectivesBean.getProjectName());
		log.info("from_date is: "+copyObjectivesBean.getAssessmentFromDate());
		log.info("end_date is: "+copyObjectivesBean.getAssessmentToDate());
		
		if(type.equals("role")){
			assignObjectivesService.copyRoleObjectives(copyObjectivesBean);
		}
		if(type.equals("assessee")){
			
			log.info("Assessee: "+copyObjectivesBean.getAssessee());
			log.info("OtherAssessee: "+copyObjectivesBean.getOtherAssessee());
			//copyObjectivesBean.setAssessee(copyObjectivesBean.getOtherAssessee());
			assignObjectivesService.copyAssesseObjectives(copyObjectivesBean);
		}
		
		List<Cycle> assessmentCycles = assignObjectivesService.getAllAssessmentCycles();
		List<Employee> assesses = assignObjectivesService.getAllAssesses();
		List<Role> roles = assignObjectivesService.getAllRoles();
		model.put("assessmentcycles", assessmentCycles);
		model.put("assesses", assesses);
		model.put("roles", roles);
		model.put("copyObjectivesBean", copyObjectivesBean);
		log.info("after copy role objectives........");
		
		setModelWithDefaultLoad(model,0,copyObjectivesBean);
		
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;
		
	}
	
	

	// Adding New Objective to section specified
	@RequestMapping(value = "/assessor/addObjective", method = RequestMethod.POST)
	public String addObjective(
			@ModelAttribute("copyObjectivesBean") CopyObjectivesBean copyObjectivesBean,
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			@RequestParam("addObjectiveSectionID") String addObjectiveSectionID,
			@RequestParam("addObjectiveSectionName") String addObjectiveSectionName,
			@RequestParam("assessmentCycle") String assessmentCycle,
			@RequestParam("assesseeRole") String assesseeRole,
			@RequestParam("assessor") String assessor,
			@RequestParam("projectName") String projectName,
			@RequestParam("assessmentToDate") String assessmentToDate,
			@RequestParam("assessmentFromDate") String assessmentFromDate,
			@RequestParam("assessee") String assessee,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal, Errors errors) {

		System.out.println("Testing");
		copyObjectivesBean.setAssesseeRole(Integer.parseInt(assesseeRole));
		copyObjectivesBean.setAssessmentCycle(Integer.parseInt(assessmentCycle));
		copyObjectivesBean.setAssessmentFromDate(assessmentFromDate);
		copyObjectivesBean.setAssessmentToDate(assessmentToDate);
		copyObjectivesBean.setProjectName(Integer.parseInt(projectName));
		copyObjectivesBean.setAssessor(Integer.parseInt(assessor));
		copyObjectivesBean.setAssessee(Integer.parseInt(assessee));
		log.info("Request to add Objective");
		System.out.println(assesseeRole);
		System.out.println(assessmentCycle);
		System.out.println(assessmentFromDate);
		System.out.println(assessmentToDate);
		System.out.println(projectName);
		System.out.println(assessor);
		System.out.println(assessee);
		addObjectiveValidator.validate(addObjectiveBean, errors);

		if (errors.hasErrors()) {
			setModelWithDefaultLoad(model, Integer.parseInt(addObjectiveSectionID), copyObjectivesBean);
			

			return ASSIGNONJECTIVESTOASSESSE;
		}
		log.info("Request to add Objective 2");
	
		
		boolean addobjectiveStatus = assignObjectivesService.addAssesseeObjective(copyObjectivesBean, addObjectiveSectionID,addObjectiveBean.getObjectiveDesc());
		System.out.println("copyObjectivesBean "+copyObjectivesBean.getAssessmentFromDate());
		loadAssesseObjectives(copyObjectivesBean,model,Integer.parseInt(addObjectiveSectionID));
		
		model.put("createSectionBean", new CreateSectionBean());
		model.put("addObjectiveBean", new AddObjectiveBean());

		log.info("Request to add Objective for the Section "
				+ addObjectiveSectionID);

		setModelWithDefaultLoad(model, Integer.parseInt(addObjectiveSectionID),copyObjectivesBean);
		

		
		
		if (addobjectiveStatus) {
			
			model.put("objectiveMessage",setMessageStatus(SUCCESS,"addobjective.success",""));
		} else {
				
			model.put("objectiveMessage", setMessageStatus(DANGER,"addobjective.failure",""));	
		}
		
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;
	}

	@RequestMapping(value = "/assessor/saveObjective", method = RequestMethod.POST)
	public String saveObjective(
			HttpServletRequest request,
			@ModelAttribute("copyObjectivesBean") CopyObjectivesBean copyObjectivesBean,
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			@ModelAttribute("textAreas") String saveObjectDesc,
			@ModelAttribute("updateobjectiveId") String saveObjectiveId,
			@ModelAttribute("updateSectionid") String updateSectionid,
			@RequestParam("weightage") String weightage,
			Principal principal,
			BindingResult result, Map<String, Object> model) {
		AssesseeObjectives assesseeObjectives=new AssesseeObjectives();
		assesseeObjectives.setDescription(saveObjectDesc);
		if(Integer.parseInt(weightage)!=0)
		{
		assesseeObjectives.setWeightage(modelObjectService.findWeightageById(Integer.parseInt(weightage)));
		}else{
			assesseeObjectives.setWeightage(modelObjectService.findWeightageById(DEFAULT_WEIGHTAGE_ID));
		}
		assesseeObjectives.setId(Integer.parseInt(saveObjectiveId));
		
		log.info("edited objective for section id  " + updateSectionid);
		log.info("save objective for objective id  " + saveObjectiveId);
		assignObjectivesService.saveAssesseeObjectivebySection(assesseeObjectives);
	 assesseeObjectives = 	assignObjectivesService.getAssesseeAssessorId(Integer.parseInt(saveObjectiveId));
	
	 model.put("copyObjectivesBean", setcopyObjectivesBean(copyObjectivesBean,assesseeObjectives));
		

		setModelLoad(model, Integer.parseInt(updateSectionid),assesseeObjectives);
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;

	}

	@RequestMapping(value = "/assessor/deleteObjective", method = RequestMethod.POST)
	public String deleteObjective(
			HttpServletRequest request,
			@ModelAttribute("copyObjectivesBean") CopyObjectivesBean copyObjectivesBean,
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			@ModelAttribute("deleteObjectiveId") String deleteObjectiveId,
			@ModelAttribute("sectionIdSelected") String sectionIdSelected,
			Principal principal,
			BindingResult result, Map<String, Object> model) {
	

		log.info("Delete objective for  section ID " + deleteObjectiveId);
		boolean deleteObjectiveStatus = false;
		AssesseeObjectives assesseeObjectives = 	assignObjectivesService.getAssesseeAssessorId(Integer.parseInt(deleteObjectiveId));
		try {

			
			
			
			
			model.put("copyObjectivesBean", setcopyObjectivesBean(copyObjectivesBean,assesseeObjectives));
			
			deleteObjectiveStatus = assignObjectivesService.deleteAssesseeObjectivebySection(Integer
							.parseInt(deleteObjectiveId));
			
		} catch (Exception e) {
			log.error("You delete the record " + e);
		}
		log.info("objective delete status " + deleteObjectiveStatus);
		request.setAttribute("deleteObjectiveStatus", deleteObjectiveStatus);
		setModelLoad(model, Integer.parseInt(sectionIdSelected),assesseeObjectives);
		if (deleteObjectiveStatus) {
			model.put("objectiveMessage",setMessageStatus(SUCCESS,"deleteObjectiveStatus.success",""));
			} else {
				model.put("objectiveMessage",setMessageStatus(DANGER,"deleteObjectiveStatus.failure",""));
		}
	
		// Load Sections and Objectives
	//	setModelWithDefaultLoad(model, Integer.parseInt(sectionIdSelected));
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;

	}


	
	@RequestMapping(value = "/assessor/ajax/sectionLoad", method = RequestMethod.GET)
	public @ResponseBody
	List<AssesseeObjectives> Load(@RequestParam("sectionLoad") String sectionId,
			@RequestParam("assesseeid") String assesseeid,
			@RequestParam("assessmentFromDate") String assessmentFromDate,
			@RequestParam("projectName") String projectName,
			@RequestParam("assessmentCycle") String assessmentCycle,
			@RequestParam("assessmentToDate") String assessmentToDate,
			@RequestParam("assessor") String assessor,
			@RequestParam("assesseeRole") String assesseeRole) {
		
		
		System.out.println(assesseeid);
		System.out.println(assessmentFromDate);
		System.out.println(projectName);
		System.out.println(assessmentCycle);
		System.out.println(assessmentToDate);
		System.out.println(assessor);
		System.out.println(sectionId);
		System.out.println(assesseeRole);
		
		CopyObjectivesBean copyObjectivesBean =new CopyObjectivesBean();
		copyObjectivesBean.setAssessee(Integer.parseInt(assesseeid));
		copyObjectivesBean.setAssessmentCycle(Integer.parseInt(assessmentCycle));
		copyObjectivesBean.setProjectName(Integer.parseInt(projectName));
		copyObjectivesBean.setAssessmentFromDate(assessmentFromDate);
		copyObjectivesBean.setAssesseeRole(Integer.parseInt(assesseeRole));
		copyObjectivesBean.setAssessmentToDate(assessmentToDate);
		copyObjectivesBean.setAssessor(Integer.parseInt(assessor));
		
		List<Section> allSections = modelObjectService.getAllSections();
		int sectionToLoad=Integer.parseInt(sectionId);
		if ( sectionToLoad== 0) {
			sectionToLoad = ((Section) allSections.get(0)).getId();
		}
		System.out.println("sectionToLoad "+sectionToLoad);
		List<AssesseeObjectives> allsectionObjectives = assignObjectivesService.getAssesseObjectives(copyObjectivesBean, sectionToLoad);
		log.info(" request for objectives to load for section id is "
				+ allsectionObjectives.size());
		
		

		return allsectionObjectives;

	}
	
	
	@RequestMapping(value = "/assessor/ajax/saveWeightage", method = RequestMethod.GET)
	public @ResponseBody
	boolean saveWeightage(@RequestParam("weightage") String weightageId,
			@RequestParam("objectiveId") String objectiveId)
			{
		log.info("Weightage "+weightageId);
		log.info("objectiveId "+objectiveId);
		
		if(Integer.parseInt(weightageId)!=0)
		{
			assignObjectivesService.saveWeightage(Integer.parseInt(objectiveId), Integer.parseInt(weightageId));
	
		}else{
			assignObjectivesService.saveWeightage(Integer.parseInt(objectiveId), DEFAULT_WEIGHTAGE_ID);
	
		}
		
		return true;
			}
//	convertStringToDate(assessmentFromDate.substring(0, 10))
	private Date convertStringToDate(String datestring)
	{
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date=null ;
		try {
			 date = format.parse(datestring);
			System.out.println("date "+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	private CopyObjectivesBean setcopyObjectivesBean(CopyObjectivesBean copyObjectivesBean, AssesseeObjectives assesseeObjectives)
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		copyObjectivesBean.setAssessee(assesseeObjectives.getAssesseeAssessor().getAssesseeId().getId());
		copyObjectivesBean.setAssesseeRole(assesseeObjectives.getAssesseeAssessor().getRoleId().getId());
		copyObjectivesBean.setAssessmentCycle(assesseeObjectives.getAssesseeAssessor().getCycleId().getId());
		copyObjectivesBean.setAssessmentFromDate(df.format(assesseeObjectives.getAssesseeAssessor().getStart_date()));
		copyObjectivesBean.setAssessmentToDate(df.format(assesseeObjectives.getAssesseeAssessor().getEnd_date()));
		copyObjectivesBean.setAssessor(assesseeObjectives.getAssesseeAssessor().getAssessorId().getId());
		copyObjectivesBean.setProjectName(assesseeObjectives.getAssesseeAssessor().getProjectId().getId());
		
		return copyObjectivesBean;
		}

	@SuppressWarnings("unchecked")
	private void setModelWithDefaultLoad(Map model, int sectionIDtoLoad, CopyObjectivesBean copyObjectivesBean) {
		List<AssesseeObjectives> allAssesseeObjectives = null;

		List<Section> allSections = modelObjectService.getAllSections();
		model.put("allSections", allSections);
		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}

		if (sectionIDtoLoad != 0) {
			allAssesseeObjectives=assignObjectivesService.getAssesseObjectives(copyObjectivesBean, sectionIDtoLoad);
		}
		model.put("sectionToLoad", sectionIDtoLoad);
		
		model.put("copyObjectivesBean", copyObjectivesBean);
		model.put("allObjectives", allAssesseeObjectives);
		
		List<Cycle> assessmentCycles = assignObjectivesService.getAllAssessmentCycles();
		List<Employee> assesses = assignObjectivesService.getAllAssesses();
		List<Role> roles = assignObjectivesService.getAllRoles();
		model.put("addObjectiveBean", new AddObjectiveBean());
		model.put("assessmentcycles", assessmentCycles);
		model.put("assesses", assesses);
		model.put("roles", roles);
		//projectList
		List<Project> projectList=modelObjectService.getAllProjects();
		model.put("projectList", projectList);
		model.put("allWeightages", modelObjectService.getAllWeightages());

	}
	
	
	@SuppressWarnings("unchecked")
	private void setModelLoad(Map model, int sectionIDtoLoad, AssesseeObjectives assesseeObjectives ) {
		List<AssesseeObjectives> allAssesseeObjectives = null;

		List<Section> allSections = modelObjectService.getAllSections();
		model.put("allSections", allSections);
		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}

		if (sectionIDtoLoad != 0) {
		 allAssesseeObjectives =assignObjectivesService.getALLAssesseObjectivesBySectionId(assesseeObjectives);
		}
		
		List<Cycle> assessmentCycles = assignObjectivesService.getAllAssessmentCycles();
		List<Employee> assesses = assignObjectivesService.getAllAssesses();
		List<Role> roles = assignObjectivesService.getAllRoles();
		model.put("addObjectiveBean", new AddObjectiveBean());
		model.put("assessmentcycles", assessmentCycles);
		model.put("assesses", assesses);
		model.put("roles", roles);
		
		model.put("sectionToLoad", sectionIDtoLoad);
		model.put("allObjectives", allAssesseeObjectives);
		List<Project> projectList=modelObjectService.getAllProjects();
		model.put("projectList", projectList);
		model.put("allWeightages", modelObjectService.getAllWeightages());

	}
	
	private Messages setMessageStatus(String type,String text, String name)
	{
		
		Messages msg=new Messages();
		msg.setType(type);
		msg.setText(text);
		msg.setName(name);
		
		return msg;
	}

	@SuppressWarnings("unchecked")
	private void loadAssesseObjectives(CopyObjectivesBean copyObjectivesBean,Map model,int sectionId)
	{
		log.info("loadAssessee Objective " );
		
	List<AssesseeObjectives> assesseeObjectives	=assignObjectivesService.getAssesseObjectives(copyObjectivesBean, sectionId);
	
	model.put("assesseeObjectives", assesseeObjectives);
		
	}

}
