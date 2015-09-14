package com.arrkgroup.apps.hr.managesections;

import java.sql.SQLException;
import java.util.List;

import com.arrkgroup.apps.model.Objective;
import com.arrkgroup.apps.model.Section;

public interface SectionService {
	
	public boolean addNewSection(Section section);
	public List<Section> getAllSections();
	
	public boolean addNewObjective(Objective objectives);
	public List<Objective> getObjectivesBySection(int sectionId);
	public boolean saveObjectivebySection(Objective objectives);
	public boolean deleteObjectivebySection(int sectionId)  throws SQLException ;


}
