package com.arrkgroup.apps.form;

import javax.validation.constraints.NotNull;

public class AddObjectiveBean {

	int objectiveId;
	@NotNull
	String objectiveDesc;
	
	
	public int getObjectiveId() {
		return objectiveId;
	}
	public void setObjectiveId(int objectiveId) {
		this.objectiveId = objectiveId;
	}
	public String getObjectiveDesc() {
		return objectiveDesc;
	}
	public void setObjectiveDesc(String objectiveDesc) {
		this.objectiveDesc = objectiveDesc;
	}
	
	
	
}
