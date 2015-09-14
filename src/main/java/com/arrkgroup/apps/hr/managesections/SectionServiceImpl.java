package com.arrkgroup.apps.hr.managesections;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arrkgroup.apps.adminUser.AdminUserDao;
import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Section;

@Service("SectionService")
@Transactional
public class SectionServiceImpl implements SectionService {
	@Autowired
	SectionDao sectionDao;
	
	@Override
	public boolean addNewSection(Section section) {
		// TODO Auto-generated method stub
		
		return sectionDao.addNewSection(section);
	}

	@Override
	public List<Section> getAllSections() {
		// TODO Auto-generated method stub
		return sectionDao.getAllSections();
	}

	@Override
	public boolean addNewObjective(Objective objectives) {
		// TODO Auto-generated method stub
		return sectionDao.addNewObjective(objectives);
	}

	@Override
	public List<Objective> getObjectivesBySection(int sectionId) {
		// TODO Auto-generated method stub
		return sectionDao.getObjectivesBySection(sectionId);
	}

	@Override
	public boolean saveObjectivebySection(Objective objectives) {
		// TODO Auto-generated method stub
		return sectionDao.saveObjectivebySection(objectives);
	}

	@Override
	public boolean deleteObjectivebySection(int sectionId) throws SQLException  {
		// TODO Auto-generated method stub
		return sectionDao.deleteObjectivebySection(sectionId);
	}

}
