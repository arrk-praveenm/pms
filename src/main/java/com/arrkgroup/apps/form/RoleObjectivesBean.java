package com.arrkgroup.apps.form;

import java.util.Date;
import java.util.List;

public class RoleObjectivesBean {

	int id;

	private Date last_modified_date;

	int role_id;

	int section_id;

	List<String> multiselect_to;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getSection_id() {
		return section_id;
	}

	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}

	public List<String> getMultiselect_to() {
		return multiselect_to;
	}

	public void setMultiselect_to(List<String> multiselect_to) {
		this.multiselect_to = multiselect_to;
	}

}
