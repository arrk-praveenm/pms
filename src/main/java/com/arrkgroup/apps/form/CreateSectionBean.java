package com.arrkgroup.apps.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CreateSectionBean {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	 
	@NotNull
//	@NotBlank(message = CreateSectionBean.NOT_BLANK_MESSAGE) 
	private String sectionName;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	
}
