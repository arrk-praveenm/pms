package com.arrkgroup.apps.hr.showReports;

import java.util.List;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;



public interface ShowReportDao {




	public List<AssesseesAssessor> showAssessorById(int id);
	public List<AssesseesAssessor> showAssessesByAssessor(int id);

	public List<AssesseesAssessor> showAssessesByAssessor(int id,int cycle);

	public List<Employee> showAssessorByCycleManager(int id,int manager);

    public List<Employee> showAllManager();

}
