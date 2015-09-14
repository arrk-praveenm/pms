package com.arrkgroup.apps.assessor.assignobjectives;

import java.security.Principal;
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
import com.arrkgroup.apps.hr.managesections.SectionService;
import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.Cycle;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.support.web.Messages;

@Controller
public class AssignObjectivesController {
	private final Logger log = LoggerFactory
			.getLogger(AssignObjectivesController.class);
	private final String SUCCESS="success";
	private final String DANGER="danger";
	private final String ASSIGNONJECTIVESTOASSESSE="/assessor/AssignObjectivesToAssesse";
	private final String LOGIN="login/login";
	
	@Autowired
	AssignObjectivesService assignObjectivesService;
	
	@Autowired
	SectionService sectionService;
	

	@Autowired
	AddObjectiveValidator addObjectiveValidator;
	
	
	@RequestMapping(value = "/assessor/assignobjectives", method = RequestMethod.GET)
	public String showAssignObjectives(Map<String, Object> model,Principal principal) {
		List<Cycle> assessmentCycles = assignObjectivesService.getAllAssessmentCycles();
		List<Employee> assesses = assignObjectivesService.getAllAssesses();
		List<Role> roles = assignObjectivesService.getAllRoles();
		//refactor
		setModelWithDefaultLoad(model,0);
		model.put("addObjectiveBean", new AddObjectiveBean());
		model.put("assessmentcycles", assessmentCycles);
		model.put("assesses", assesses);
		model.put("roles", roles);
		model.put("copyObjectivesBean", new CopyObjectivesBean());
		
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
		
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;		
	}

	// Adding New Objective to section specified
	@RequestMapping(value = "/assessor/addObjective", method = RequestMethod.POST)
	public String addObjective(
			@ModelAttribute("copyObjectivesBean") CopyObjectivesBean copyObjectivesBean,
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			@RequestParam("addObjectiveSectionID") String addObjectiveSectionID,
			@RequestParam("addObjectiveSectionName") String addObjectiveSectionName,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal, Errors errors) {

		log.info("Request to add Objective");
		addObjectiveValidator.validate(addObjectiveBean, errors);

		if (errors.hasErrors()) {
			setModelWithDefaultLoad(model,
					Integer.parseInt(addObjectiveSectionID));

			return ASSIGNONJECTIVESTOASSESSE;
		}

		Objective objectives = new Objective(
				addObjectiveBean.getObjectiveDesc(), new Section(
						Integer.parseInt(addObjectiveSectionID),
						addObjectiveSectionName, new Date()), new Date());
		boolean addobjectiveStatus = sectionService.addNewObjective(objectives);
		
		System.out.println("copyObjectivesBean "+copyObjectivesBean.getAssessmentFromDate());
		loadAssesseObjectives(copyObjectivesBean,model,Integer.parseInt(addObjectiveSectionID));
		
		model.put("createSectionBean", new CreateSectionBean());
		model.put("addObjectiveBean", new AddObjectiveBean());

		log.info("Request to add Objective for the Section "
				+ addObjectiveSectionID);

		setModelWithDefaultLoad(model, Integer.parseInt(addObjectiveSectionID));

		
		
		if (addobjectiveStatus) {
			
			model.put("objectiveMessage",setMessageStatus(SUCCESS,"addobjective.success",String.valueOf(objectives.getSection().getId())));
		} else {
				
			model.put("objectiveMessage", setMessageStatus(DANGER,"addobjective.failure",String.valueOf(objectives.getSection().getId())));	
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
			Principal principal,
			BindingResult result, Map<String, Object> model) {

		log.info("edited objective for section id  " + updateSectionid);

		Objective objectives = new Objective();
		objectives.setObjectiveDesc(saveObjectDesc);
		objectives.setId(Integer.parseInt(saveObjectiveId));
		sectionService.saveObjectivebySection(objectives);

		setModelWithDefaultLoad(model, Integer.parseInt(updateSectionid));
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
		try {

			deleteObjectiveStatus = sectionService
					.deleteObjectivebySection(Integer
							.parseInt(deleteObjectiveId));
		} catch (Exception e) {
			log.error("You delete the record " + e);
		}
		log.info("objective delete status " + deleteObjectiveStatus);
		request.setAttribute("deleteObjectiveStatus", deleteObjectiveStatus);
	
		if (deleteObjectiveStatus) {
			model.put("objectiveMessage",setMessageStatus(SUCCESS,"deleteObjectiveStatus.success",""));
			} else {
				model.put("objectiveMessage",setMessageStatus(DANGER,"deleteObjectiveStatus.failure",""));
		}
	
		// Load Sections and Objectives
		setModelWithDefaultLoad(model, Integer.parseInt(sectionIdSelected));
		return principal != null ? ASSIGNONJECTIVESTOASSESSE : LOGIN;

	}


	@RequestMapping(value = "/assessor/ajax/sectionLoad", method = RequestMethod.GET)
	public @ResponseBody
	List<Objective> Load(@RequestParam("sectionLoad") String sectionId) {

		List<Objective> allsectionObjectives = sectionService
				.getObjectivesBySection(Integer.parseInt(sectionId));
		log.info(" request for objectives to load for section id is "
				+ sectionId);

		return allsectionObjectives;

	}

	private Section setCreateSectionModel(CreateSectionBean createSectionBean) {
		Section section = new Section();

		section.setSection(createSectionBean.getSectionName());
		section.setLastModifiedDate(new Date());
		return section;
	}

	private void setModelWithDefaultLoad(Map model, int sectionIDtoLoad) {
		List<Objective> allObjectives = null;

		List<Section> allSections = sectionService.getAllSections();
		model.put("allSections", allSections);
		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
		}

		if (sectionIDtoLoad != 0) {
			allObjectives = sectionService
					.getObjectivesBySection(sectionIDtoLoad);
		}
		model.put("sectionToLoad", sectionIDtoLoad);
		model.put("allObjectives", allObjectives);

	}
	
	private Messages setMessageStatus(String type,String text, String name)
	{
		
		Messages msg=new Messages();
		msg.setType(type);
		msg.setText(text);
		msg.setName(name);
		
		return msg;
	}

	
	private void loadAssesseObjectives(CopyObjectivesBean copyObjectivesBean,Map model,int sectionId)
	{
		log.info("loadAssessee Objective " );
		
	List<AssesseeObjectives> assesseeObjectives	=assignObjectivesService.getAssesseObjectives(copyObjectivesBean, sectionId);
	
	model.put("assesseeObjectives", assesseeObjectives);
		
	}

}
