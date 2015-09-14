package com.arrkgroup.apps.pmsCycle;

import java.text.ParseException;
import java.util.List;
import com.arrkgroup.apps.model.Cycle;

public interface PmsCycleService {
	
	public boolean addCycle(Cycle cycle) throws ParseException;
	public List<Cycle> showList();
	public boolean deleteCycle(int id);

}
