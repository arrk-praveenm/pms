package com.arrkgroup.apps.definition;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arrkgroup.apps.model.RatingDescription;
import com.arrkgroup.apps.service.ModelObjectService;

@Controller
public class RatiingController {
	
	private final String DEFINITIONS_RATINGS = "definitions/rating";
	private final String DEFINITIONS_WEIGHTAGE = "definitions/weightage";
	private final String LOGIN = "login/login";
	
	@Autowired
	ModelObjectService modelObjectService;
	
	@RequestMapping(value = "/definitions/ratingdefinition", method = RequestMethod.GET)
	public String loadRatingsDefinitions(Principal principal, Model model) {
		model.addAttribute("allSections", modelObjectService.getAllSections());
		model.addAttribute("ratingsList", modelObjectService.getAllRatings());
		
		model.addAttribute("allSections", modelObjectService.getAllSections());
		model.addAttribute("rating", modelObjectService.getAllWeightages());
		
		return principal != null ? DEFINITIONS_RATINGS : LOGIN;
	}
	
	@RequestMapping(value = "/definitions/weightagedefinition", method = RequestMethod.GET)
	public String loadWeightageDefinitions(Principal principal, Model model) {
		
		model.addAttribute("allSections", modelObjectService.getAllSections());
		model.addAttribute("weightage", modelObjectService.getAllWeightages());
		
		return principal != null ? DEFINITIONS_WEIGHTAGE : LOGIN;
	}
	
	@RequestMapping(value = "/definitions/loadRating", method = RequestMethod.GET)
	public @ResponseBody List<RatingDescription> loadRatingDescription(@RequestParam("sectionId") String sectionId) {
		List<RatingDescription> ratingDescList=new ArrayList<RatingDescription>();
		
		
		ratingDescList=modelObjectService.getRatingDescriptionBySectionId(Integer.parseInt(sectionId));
		//model.addAttribute("allSections", modelObjectService.getAllSections());
		//model.addAttribute("weightage", modelObjectService.getAllWeightages());
		
		return ratingDescList;
	}

}
