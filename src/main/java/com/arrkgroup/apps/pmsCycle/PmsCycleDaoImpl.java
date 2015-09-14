package com.arrkgroup.apps.pmsCycle;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.loader.custom.Return;
import org.springframework.stereotype.Repository;

import com.arrkgroup.apps.model.Cycle;

@Repository("PmsCycleDao")
@Transactional
public class PmsCycleDaoImpl implements PmsCycleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean addCycle(Cycle cycle) {

		try {

			entityManager.persist(cycle);
		}

		catch (Exception e) {
			// log it or do something
			return false;
		}

		return true;

	}

	@Override
	public List<Cycle> showList() {

		TypedQuery<Cycle> query = entityManager.createNamedQuery(Cycle.FIND_ALL,
				Cycle.class);
		List<Cycle> results = query.getResultList();

		return results;
	}

	@Override
	public Cycle currentCycle() {

		TypedQuery<Cycle> query = entityManager.createNamedQuery(Cycle.FIND_ALL,
				Cycle.class);
		List<Cycle> results = query.getResultList();

		return results.get(0);
	}

	@Override
	public Boolean checkCycleStatus() {
		Boolean staus = false;
		TypedQuery<Cycle> query = entityManager.createNamedQuery(Cycle.FIND_ALL,
				Cycle.class);
		List<Cycle> results = query.getResultList();

		for (Cycle cycle : results) {
			if (cycle.getStatus().equals("current")) {
				System.out.println("status is current");

				staus = true;
				break;
			}

		}

		return staus;
	}

	@Override
	public boolean deleteCycle(int id) {
		boolean flag = false;

		Query query = entityManager
				.createQuery("DELETE FROM Cycle AS c WHERE c.id=:id");
		query.setParameter("id", id);
		int result = query.executeUpdate();

		if (result > 0) {
			flag = true;

		}

		return flag;
	}

}
