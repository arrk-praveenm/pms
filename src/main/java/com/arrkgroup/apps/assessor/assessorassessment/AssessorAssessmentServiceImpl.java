package com.arrkgroup.apps.assessor.assessorassessment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Section;

@Service("AssessorAssessmentService")
@Transactional
public class AssessorAssessmentServiceImpl implements AssessorAssessmentService {

	@Autowired
	AssessorAssessmentDao assessorAssessmentDao;
	@Override
	public List<AssesseesAssessor>  getMyAssessees(String email) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getMyAssessees(email);
	}
	@Override
	public Employee getAssessee(int id) {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getAssessee(id);
	}
	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return assessorAssessmentDao.getAllSections();
	}
	
	

}
