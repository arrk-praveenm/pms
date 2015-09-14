package com.arrkgroup.apps.pmsCycle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.model.Cycle;

@Service("PmsCycleService")
@Transactional
public class PmsCycleServiceImpl implements PmsCycleService {

	@Autowired
	PmsCycleDao PmsCycleDao;

	@Override
	public boolean addCycle(Cycle cycle) throws ParseException {
		
		//get current logged in username from ldap context
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		Date start_date = cycle.getStart_date();
		Date end_date = cycle.getEnd_date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date start = formatter.parse(formatter.format(start_date));
		Date end = formatter.parse(formatter.format(end_date));
		Date current = formatter.parse(formatter.format(new Date()));

		cycle.setStart_date(start);
		cycle.setEnd_date(end);
		cycle.setLast_modified_date(current);
		cycle.setLast_modified_by(auth.getName());
		cycle.setStatus("current");
	 return	PmsCycleDao.addCycle(cycle);
	}

	@Override
	public List<Cycle> showList() {
		
		return PmsCycleDao.showList();
	}

	public boolean deleteCycle(int id) {
		
		return PmsCycleDao.deleteCycle(id);
	}
	
}
