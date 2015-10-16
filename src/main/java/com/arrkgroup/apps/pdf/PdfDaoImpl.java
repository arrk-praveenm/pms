package com.arrkgroup.apps.pdf;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.AssesseesAssessor;

@Repository("PdfDao")
public class PdfDaoImpl implements PdfDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<AssesseesAssessor> getAssesseesAssessorByCycle(int employeeId,
			int cycleId) {
		
		
		// TODO Auto-generated method stub
		return  entityManager
				.createNamedQuery(AssesseesAssessor.FIND_ASSESSEES_BY_ASSESSEESSID_CYCLEID,
						AssesseesAssessor.class).setParameter("assesseeId", employeeId)
						.setParameter("cycleId", cycleId)
				.getResultList();
	}

}
