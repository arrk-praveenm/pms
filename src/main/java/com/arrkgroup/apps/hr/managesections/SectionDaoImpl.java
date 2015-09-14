package com.arrkgroup.apps.hr.managesections;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.AccessRole;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Section;

@Repository("SectionDao")
public class SectionDaoImpl implements SectionDao {


	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean addNewSection(Section section) {
		// TODO Auto-generated method stub
		
		try {
			
			entityManager.persist(section);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(Section.GET_ALL_SECTIONS,
						Section.class)
				.getResultList();
	}

	@Override
	public boolean addNewObjective(Objective objectives) {
		// TODO Auto-generated method stub
try {
		entityManager.merge(objectives);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Objective> getObjectivesBySection(int sectionId) {
		// TODO Auto-generated method stub
		return entityManager
				.createNamedQuery(Objective.GET_ALL_SECTION_OBJECTIVES,
						Objective.class).setParameter("section", sectionId)
				.getResultList();
		
	}

	@Override
	public boolean saveObjectivebySection(Objective objectives) {
		// TODO Auto-generated method stub
		
		 entityManager
				.createNamedQuery(Objective.UPDATE_OBJECTIVE_BY_OBJECTIVEID
						).setParameter("description", objectives.getObjectiveDesc()).setParameter("objectiveId", objectives.getId()).executeUpdate()
				;
		return false;
	}

	@Override
	public boolean deleteObjectivebySection(int objectiveId) throws SQLException {
		// TODO Auto-generated method stub
		//Objectives.DELETE_OBJECTIVE_BY_SECTIONID
		try{
		System.out.println("Objective id requested to delete is "+objectiveId);
		entityManager
		.createNamedQuery(Objective.DELETE_OBJECTIVE_BY_OBJECTIVEID
				).setParameter("objectiveId", objectiveId).executeUpdate();
		
		return true;
		}catch(Exception exe)
		{
			
			throw new SQLException("Objective Record can't delete");
		
			
		}catch(Error e)
		{
			return false;
		}
	}

}
