package com.arrkgroup.apps.hr.showReports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.hr.managesections.CreateSectionController;

import com.arrkgroup.apps.model.Objective;

import com.arrkgroup.apps.model.AssesseeObjectives;
import com.arrkgroup.apps.model.AssesseesAssessor;
import com.arrkgroup.apps.model.Employee;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.RoleModel;
import com.arrkgroup.apps.model.Section;
import com.arrkgroup.apps.model.pdftableview;

@Repository("ShowReportDao")
@Transactional
public class ShowReportDaoImpl implements ShowReportDao {

	private final Logger log = LoggerFactory
			.getLogger(CreateSectionController.class);

	@PersistenceContext
	private EntityManager entityManager;

	// //////////////////////////////////////////////

	@Override
	public List<AssesseesAssessor> showAssessorById(int id) {

		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSOR_BY_CYCLE,
						AssesseesAssessor.class).setParameter("id", id)
				.getResultList();
	}



	public List<AssesseesAssessor> showAssessesByAssessor(int id) {
		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR,
						AssesseesAssessor.class).setParameter("id", id)
				.getResultList();

	}

	@Override
	public List<AssesseesAssessor> showAssessesByAssessor(int id, int cycle) {
		// TODO Auto-generated method stub

		return entityManager
				.createNamedQuery(
						AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSOR_CYCLE,
						AssesseesAssessor.class).setParameter("id", id)
				.setParameter("cycleId", cycle).getResultList();

	}

	public List<Employee> showAllManager() {


		return entityManager.createNamedQuery(Employee.FIND_ALL_MANAGER,
				Employee.class).getResultList();
	}

	public List<Employee> showAssessorByCycleManager(int cycle, int manager) {
		log.info(" showAssessorByCycleManager method ");
		List<Employee> emp = entityManager
				.createNamedQuery(Employee.FIND_ASSESSOR_BY_MANAGER_CYCLE,
						Employee.class).setParameter("cycle", cycle)
				.setParameter("manager", manager).getResultList();

		log.info(" showAssessorByCycleManager method  " + emp.size());

		return emp;
	}


	@Override
	public List<AssesseesAssessor> showAssesseByEmplyee(int id) {

		return entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSEESSID,
						AssesseesAssessor.class).setParameter("assesseeId", id)
				.getResultList();
	}

	public List<AssesseeObjectives> showAssesseObjectiveByAssessid(int id) {

		return entityManager
				.createNamedQuery(AssesseeObjectives.FIND_SECTION_BY_ASSESSOR,
						AssesseeObjectives.class).setParameter("assesseeAssessorId", id)
				.getResultList();
	}

	public List<pdftableview> getPdfView()
	{

		return entityManager
				.createNamedQuery(pdftableview.GET_ALL_RECORDS,
						pdftableview.class)
				.getResultList();

	}


}