package com.arrkgroup.apps.hr.showReports;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.form.ShowReportBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;
import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.service.ModelObjectService;


@Service("ShowReportService")
@Transactional
public class ShowReportServiceImpl implements ShowReportService {

    private final Logger log = LoggerFactory
            .getLogger(CreateSectionController.class);

    @Autowired
    ShowReportDao showReportDao;

    @Autowired
    ModelObjectService modelObjectService;


    // list all roles presnt in Database
    /*@Override
    public List<Role> showRole() {

        return roleDao.showRole();
    }

    // checking objectives for role
    public boolean checkRoleObjectives(RoleObjectivesBean bean) {

        List<RoleModel> objectives = roleDao.Objectives_to_Role_Id(bean);
        log.info("objectives size is " + objectives.size());

        if (objectives.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    // insert record service
    public boolean insert_record(RoleObjectivesBean bean) {

        return roleDao.insert_record(bean);

    }

    public List<Objective> showObjectives_By_Section(int section_id) {

        return roleDao.objective_from_section_id(section_id);

    }

    public boolean delete_record(RoleObjectivesBean bean) {

        return roleDao.delete_record(bean);

    }

    public List<Section> showSections() {

        return roleDao.showSections();

    }

    // populate objectives by selected section and role
    public List<Objective> showObjectives_By_Section_Role(int section_id,
            int role_id) {

        List<Objective> ObjectivesList = new ArrayList<Objective>();

        for (RoleModel objectives : roleDao.showObjectives_By_Section_Role(
                section_id, role_id)) {
            ObjectivesList.add(roleDao.objective_from_id(objectives.getObjectives().getId()));

        }

        return ObjectivesList;

    }*/

    //////////////////////////////////////////////////////////////

    public List<Employee> showEmployeeByCycle(int id)
    {

LinkedHashSet<Employee> employees=new LinkedHashSet<Employee>();

        List<AssesseesAssessor> assessors=  showReportDao.showAssessorById(id);

         System.out.println(" noof assessor in service "+ assessors.size());

         for (AssesseesAssessor assessor : assessors) {

             System.out.println(" assesor id is "+assessor.getAssessorId().getId() );

             employees.add(modelObjectService.findEmployeeById(assessor.getAssessorId().getId()));


        }

         List<Employee> list= new ArrayList<Employee>(employees);


        return list;
    }

	public List<Employee> showAssessorByCycleManager(int id,int manager_id)
	{


        return showReportDao.showAssessorByCycleManager(id, manager_id);



	}









    public List<ShowReportBean> showAssessesByAssessor(int id,int cycle)
    {


        List<Employee> employees=new ArrayList<Employee>();


        List<ShowReportBean> reportBean=new ArrayList<ShowReportBean>();

        List<AssesseesAssessor> assessors=  showReportDao.showAssessesByAssessor(id,cycle);

        System.out.println("assesor for"+ id +" "+ cycle+ "size is "+assessors.size());


        for (AssesseesAssessor assessor : assessors) {

             System.out.println(" assesse id is "+assessor.getAssesseeId().getId() );

             employees.add(modelObjectService.findEmployeeById(assessor.getAssesseeId().getId()));

             ShowReportBean bean=new ShowReportBean();

             bean.setCycle_id(assessor.getCycleId().getId());
             bean.setAssessor_id(assessor.getAssessorId().getId());
             bean.setAssessor_name(assessor.getAssessorId().getFullname());
bean.setAssessee_id(assessor.getAssesseeId().getId());
bean.setAssessee_name(assessor.getAssesseeId().getFullname()+" - "+assessor.getRoleId().getTitle()+" - "+assessor.getProjectId().getProject_name()+" - "+assessor.getStatus());

bean.setManager_id(1);
bean.setManager_name("mahesh mohite");

reportBean.add(bean);


        }
        System.out.println("1assesor for"+ id +" "+ cycle+ "size is "+reportBean.size());

        return reportBean;

    }

    public List<Employee> showAllManager()
    {
System.out.println("11");



		return    showReportDao.showAllManager();
    }




}
