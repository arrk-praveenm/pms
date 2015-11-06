package com.arrkgroup.apps.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class averageSectionBean {

	List<Integer> id = new ArrayList<Integer>();

	List<String> section = new ArrayList<String>();

	List<Float> weightage = new ArrayList<Float>();

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public List<String> getSection() {
		return section;
	}

	public void setSection(List<String> section) {
		this.section = section;
	}

	public List<Float> getWeightage() {
		return weightage;
	}

	public void setWeightage(List<Float> weightage) {
		this.weightage = weightage;
	}



}
