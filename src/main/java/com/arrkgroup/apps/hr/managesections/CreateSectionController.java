package com.arrkgroup.apps.hr.managesections;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.arrkgroup.apps.form.CreateSectionBean;
import com.arrkgroup.apps.form.validator.AddObjectiveValidator;
import com.arrkgroup.apps.form.validator.CreateSectionValidator;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.support.web.Messages;

@Controller
public class CreateSectionController {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	private final String SUCCESS="success";
	private final String DANGER="danger";
	private final String CREATESECTION="hr/createSection/CreateSections";
	private final String LOGIN="login/login";

	@Autowired
	SectionService sectionService;

	@Autowired
	CreateSectionValidator createSectionValidator;

	@Autowired
	AddObjectiveValidator addObjectiveValidator;
	

	@Autowired
	HttpServletRequest request;


	@RequestMapping(value = "/hr/createSections", method = RequestMethod.GET)
	public String loadCreateSectionPage(Principal principal,
			Map<String, Object> model) {

		model.put("createSectionBean", new CreateSectionBean());
		model.put("addObjectiveBean", new AddObjectiveBean());
		int sectionIDtoLoad = 0;
		setModelWithDefaultLoad(model, sectionIDtoLoad);

		return principal != null ? CREATESECTION : LOGIN;
		}

	// Add Section

	@RequestMapping(value = "/hr/addSection", method = RequestMethod.POST)
	public String addSection(
			@ModelAttribute("addObjectiveBean") AddObjectiveBean addObjectiveBean,
			@ModelAttribute("createSectionBean") CreateSectionBean createSectionBean,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal, Errors errors) {

		log.info("Request to add Section");
		createSectionValidator.validate(createSectionBean, errors);

		if (errors.hasErrors()) {

			int sectionIDtoLoad = 0;
			setModelWithDefaultLoad(model, sectionIDtoLoad);

			return CREATESECTION;
		}

		Section section = setCreateSectionModel(createSectionBean);
		boolean addsectionStatus = sectionService.addNewSection(section);
		model.put("createSectionBean", new CreateSectionBean());
		model.put("addObjectiveBean", new AddObjectiveBean());

		int sectionIDtoLoad = 0;
		setModelWithDefaultLoad(model, sectionIDtoLoad);

		
		if (addsectionStatus) {
			model.put("sectionMessage",setMessageStatus(SUCCESS,"addSection.success",section.getSection()));
		} else {
			model.put("sectionMessage",setMessageStatus(DANGER,"addSection.failure",section.getSection()));
		}
		

		return principal != null ? CREATESECTION : LOGIN;
	}

	// Adding New Objective to section specified
	@RequestMapping(value = "/hr/addObjective", method = RequestMethod.POST)
	public String addObjective(
			@ModelAttribute("createSectionBean") CreateSectionBean createSectionBean,
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

			return CREATESECTION;
		}

		Objective objectives = new Objective(
				addObjectiveBean.getObjectiveDesc(), new Section(
						Integer.parseInt(addObjectiveSectionID),
						addObjectiveSectionName, new Date()), new Date());
		boolean addobjectiveStatus = sectionService.addNewObjective(objectives);
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
		
		return principal != null ? CREATESECTION : LOGIN;
	}

	@RequestMapping(value = "/hr/saveObjective", method = RequestMethod.POST)
	public String saveObjective(
			HttpServletRequest request,
			@ModelAttribute("createSectionBean") CreateSectionBean createSectionBean,
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
		return principal != null ? CREATESECTION : LOGIN;

	}

	@RequestMapping(value = "/hr/deleteObjective", method = RequestMethod.POST)
	public String deleteObjective(
			HttpServletRequest request,
			@ModelAttribute("createSectionBean") CreateSectionBean createSectionBean,
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
		return principal != null ? CREATESECTION : LOGIN;

	}


	@RequestMapping(value = "/hr/ajax/sectionLoad", method = RequestMethod.GET)
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
		request.setAttribute("sectionToLoad", sectionIDtoLoad);
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

}
