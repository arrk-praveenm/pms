package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;

import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;

public interface AssessorAssessmentService {
	
	public List<AssesseesAssessor>  getMyAssessees(String email);
	public Employee  getAssessee(int id);
	
	public List<Section> getAllSections();

}
