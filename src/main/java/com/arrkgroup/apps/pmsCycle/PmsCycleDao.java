package com.arrkgroup.apps.pmsCycle;

import java.util.List;

import com.arrkgroup.apps.model.Cycle;

public interface PmsCycleDao {

	
public boolean addCycle(Cycle cycle);
	
	public List<Cycle> showList();

	public Cycle currentCycle();

	public Boolean checkCycleStatus();

	public boolean deleteCycle(int id);

	
}
