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

import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.pdftableview;
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

	public List<Employee> showEmployeeByCycle(int id) {

		LinkedHashSet<Employee> employees = new LinkedHashSet<Employee>();

		List<AssesseesAssessor> assessors = showReportDao.showAssessorById(id);

		log.info(" noof assessor in service " + assessors.size());

		for (AssesseesAssessor assessor : assessors) {

			log.info(" assesor id is " + assessor.getAssessorId().getId());

			employees.add(modelObjectService.findEmployeeById(assessor
					.getAssesseeId().getId()));

		}

		List<Employee> list = new ArrayList<Employee>(employees);
		log.info(" noof assessor in service 1 " + list.size());
		return list;
	}

	public List<Employee> showAssessorByCycleManager(int id, int manager_id) {

		return showReportDao.showAssessorByCycleManager(id, manager_id);

	}

	public List<ShowReportBean> showAssessesByAssessor(int id, int cycle) {

		List<Employee> employees = new ArrayList<Employee>();

		List<ShowReportBean> reportBean = new ArrayList<ShowReportBean>();

		List<AssesseesAssessor> assessors = showReportDao
				.showAssessesByAssessor(id, cycle);

		log.info("assesor for" + id + " " + cycle + "size is "
				+ assessors.size());

		for (AssesseesAssessor assessor : assessors) {

			log.info(" assesse id is " + assessor.getAssesseeId().getId());

			employees.add(modelObjectService.findEmployeeById(assessor
					.getAssesseeId().getId()));

			ShowReportBean bean = new ShowReportBean();

			bean.setCycle_id(assessor.getCycleId().getId());
			bean.setAssessor_id(assessor.getAssessorId().getId());
			bean.setAssessor_name(assessor.getAssessorId().getFullname());
			bean.setAssessee_id(assessor.getAssesseeId().getId());
			bean.setAssessee_name(assessor.getAssesseeId().getFullname()
					+ " - " + assessor.getRoleId().getTitle() + " - "
					+ assessor.getProjectId().getProject_name() + " - "
					+ assessor.getStatus());

			bean.setManager_id(1);
			bean.setManager_name("mahesh mohite");

			reportBean.add(bean);

		}
		log.info("1assesor for" + id + " " + cycle + "size is "
				+ reportBean.size());

		return reportBean;

	}

	public List<Employee> showAllManager() {

		return showReportDao.showAllManager();
	}

	public List<AssesseesAssessor> showAssesseByEmplyee(int id) {

		return showReportDao.showAssesseByEmplyee(id);

	}

	public List<AssesseeObjectives> showAssesseObjectiveByAssessid(int id) {

		log.info("employee id is " + id);

		List<AssesseeObjectives> assesseeObjectives = new ArrayList<AssesseeObjectives>();

		List<AssesseesAssessor> assessors = showAssesseByEmplyee(id);

		log.info("size of assesseeis  " + assessors.size());

		for (AssesseesAssessor assesseesAssessor : assessors) {

			assesseeObjectives.addAll(showReportDao
					.showAssesseObjectiveByAssessid(assesseesAssessor.getId()));

		}

		log.info("size of assesseeObjectives is  "
				+ assesseeObjectives.size());

		return assesseeObjectives;

	}

	@Override
	public List<pdftableview> getPdfView() {

		List<pdftableview> list = showReportDao.getPdfView();

		for (pdftableview pdftableview : list) {

			log.info(" comments "
					+ pdftableview.getManager_comments());
		}

		log.info("pdfview size is " + list.size());
		return list;
	}

	public List<pdftableview> showAssesseDetailsByEmplyee(int id) {

		List<AssesseesAssessor> list = showReportDao.showAssesseByEmplyee(id);

		List<pdftableview> pdftableviews = new ArrayList<pdftableview>();

		for (AssesseesAssessor assesseesAssessor : list) {

			pdftableviews.addAll(showReportDao.getPdfViewByID(assesseesAssessor
					.getId()));

		}

		return pdftableviews;

	}

}
