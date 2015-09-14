package com.arrkgroup.apps.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;



public class AddHrFormBean {
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	 
	@NotBlank(message = AddHrFormBean.NOT_BLANK_MESSAGE)
	private String firstName;
	@NotBlank(message = AddHrFormBean.NOT_BLANK_MESSAGE)
	private String lastName;

	@NotBlank(message = AddHrFormBean.NOT_BLANK_MESSAGE)
	@Email(message = AddHrFormBean.EMAIL_MESSAGE)
	private String emailId;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
