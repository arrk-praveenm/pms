package com.arrkgroup.apps.hr.assignobjective;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;
import com.arrkgroup.apps.hr.managesections.SectionService;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.ObjectivesModel;
import com.arrkgroup.apps.model.Section;

@Controller
public class RoleController {

	
	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);
	private static final String objectives_role__VIEW = "hr/assignObjective/assignObjectivesToRole";

	@Autowired
	RoleService roleService;

	@Autowired
	SectionService sectionService;

	@Autowired
	RoleDao roleDao;

	@Autowired
	RoleValidator roleValidator;

	@InitBinder
	// registering the validator
	public void initValidator(WebDataBinder binder) {
		binder.setValidator(roleValidator);

	}

	@RequestMapping(value = "/hr/obj_to_role", method = RequestMethod.GET)
	public String assign_Objectives_to_Role(Model model) {

		int sectionid = 0;
		model.addAttribute("RoleObjectivesBean", new RoleObjectivesBean());
		loadDefaultModel(model, sectionid);
		
		return objectives_role__VIEW;
	}

	@RequestMapping(value = "/hr/obj_to_role", method = RequestMethod.POST)
	public String assign_Objectives_to_Role_POST(
			@ModelAttribute("RoleObjectivesBean") @Valid RoleObjectivesBean bean,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			loadDefaultModel(model, bean.getSection_id());

			return objectives_role__VIEW;
		}

		// deleting objectives for selected roles

		if (bean.getMultiselect_to() == null) {
			if (roleService.delete_record(bean)) {
				
				model.addAttribute("errorMessage",
						"All Objectives Deleted for selected Role and Section");
				model.addAttribute("sectionToLoad", bean.getSection_id());
				model.addAttribute(
						"objectiveList",
						roleDao.showObjectives_By_Notin_Section_Role(
								bean.getSection_id(), bean.getRole_id()));
				loadDefaultModel(model, bean.getSection_id());

			}

		} else {

			// inserting objectives for selected roles and section
			boolean flag = roleService.insert_record(bean);

			if (flag) {
				// populate selected objective list by role and section
				model.addAttribute(
						"objectiveList",
						roleDao.showObjectives_By_Notin_Section_Role(
								bean.getSection_id(), bean.getRole_id()));

				model.addAttribute("sectionToLoad", bean.getSection_id());

				// print success message
				model.addAttribute("successMessage",
						"Objectives Modiefied for selected Role and Section");

			} else {

				model.addAttribute("errorMessage",
						"Objectives insertion unsuccessfull, Please Try Again ");

			}

		}

		List<Section> allSections = sectionService.getAllSections();
		model.addAttribute("allSections", allSections);
		model.addAttribute("roleList", roleService.showRole());
		model.addAttribute("objectiveSelectedList", roleService
				.showObjectives_By_Section_Role(bean.getSection_id(),
						bean.getRole_id()));

		return objectives_role__VIEW;
	}

	// Ajax call for populating objectives on section selection
	@RequestMapping(value = "/hr/showObjective_Section", method = RequestMethod.GET)
	public @ResponseBody
	java.util.List<ObjectivesModel> getObjective_Section(
			@RequestParam(value = "section_id") String section_id) {

		return roleService.showObjectives_By_Section(Integer
				.parseInt(section_id));

	}

	// Ajax call for populating objectives on section selection and roles
	// selection
	@RequestMapping(value = "/hr/showSelectedObjectiveAjax", method = RequestMethod.GET)
	public @ResponseBody
	java.util.List<ObjectivesModel> getSelectedObjective_Ajax(
			@RequestParam(value = "section_id") String section_id,
			@RequestParam(value = "role_id") String role_id) {

		java.util.List<ObjectivesModel> objectives = new ArrayList<ObjectivesModel>();

		if (!section_id.equals(null) && !role_id.equals(null)) {
			objectives = roleService.showObjectives_By_Section_Role(
					Integer.parseInt(section_id), (Integer.parseInt(role_id)));
		}

		return objectives;
	}

	private void loadDefaultModel(Model model, int sectionIDtoLoad) {

		List<Section> allSections = sectionService.getAllSections();
		model.addAttribute("allSections", allSections);

		if (sectionIDtoLoad == 0) {
			sectionIDtoLoad = ((Section) allSections.get(0)).getId();
			model.addAttribute("objectiveList",
				roleDao.getObjectivesBySection(sectionIDtoLoad));
		} else {
			// model.addAttribute("sectionId", sectionIDtoLoad);
			model.addAttribute("objectiveList",
				roleDao.getObjectivesBySection(sectionIDtoLoad));

		}

		// populate roles and section list
		model.addAttribute("roleList", roleService.showRole());
		model.addAttribute("sectionToLoad", sectionIDtoLoad);

	}

}