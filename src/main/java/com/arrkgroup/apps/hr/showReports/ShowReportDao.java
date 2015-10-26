package com.arrkgroup.apps.hr.showReports;

import java.util.List;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.pdftableview;



public interface ShowReportDao {




	public List<AssesseesAssessor> showAssessorById(int id);
	public List<AssesseesAssessor> showAssessesByAssessor(int id);

	public List<AssesseesAssessor> showAssessesByAssessor(int id,int cycle);

	public List<Employee> showAssessorByCycleManager(int id,int manager);

    public List<Employee> showAllManager();
	public List<AssesseesAssessor> showAssesseByEmplyee(int id);
	public List<AssesseeObjectives> showAssesseObjectiveByAssessid(int id);
	public List<pdftableview> getPdfView();
}
