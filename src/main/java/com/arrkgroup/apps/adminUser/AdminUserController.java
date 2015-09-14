package com.arrkgroup.apps.adminUser;

import java.security.Principal;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arrkgroup.apps.form.AddHrFormBean;
import com.arrkgroup.apps.layoutSupport.Layout;
import com.arrkgroup.apps.model.AccessRole;
import com.arrkgroup.apps.support.web.MessageHelper;
import com.arrkgroup.apps.support.web.Messages;

@Controller
public class AdminUserController {
	private static final String ROLE_HR = "HR";
	@Autowired
	AdminUserService adminUserService;

	@RequestMapping(value = "/admin/addHr", method = RequestMethod.POST)
	public String addHrUser(
			@Valid @ModelAttribute("addHrFormBean") AddHrFormBean addHrFormBean,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal, Errors errors,
			RedirectAttributes ra) {

		if (errors.hasErrors()) {

			// get remaning all HR from access role table
			List<AccessRole> allHrUsers = adminUserService.getAllHRUsers();
			model.put("allHrUsers", allHrUsers);

			return "adminUser/adminUser";
		}
		// Set data to model bean and add that HR as role
		AccessRole accessRole = setModel(addHrFormBean);
		boolean addUserStatus = adminUserService.addNewHrUserRole(accessRole);

		model.put("useradded", accessRole.getEmail());
		System.out.println("useradded " + accessRole.getEmail());
		// get remaning all HR from access role table
		List<AccessRole> allHrUsers = adminUserService.getAllHRUsers();
		model.put("allHrUsers", allHrUsers);
		model.put("addHrFormBean", new AddHrFormBean());
		

	//	MessageHelper.addSuccessAttribute(ra, "addedHrRole.success");
		// Seeting custom Message
		Messages msg = new Messages();
		if (addUserStatus) {
			msg.setText("SUCCESS");
			msg.setText("addedHrRole.success");
			msg.setName(accessRole.getEmail());
		} else {
			msg.setText("DANGER");
			msg.setText("addedHrRole.failure");
			msg.setName(accessRole.getEmail());
		}

		model.put("messages", msg);
		return principal != null ? "adminUser/adminUser" : "login/login";
	}

	@RequestMapping(value = "/admin")
	public String loadAdminPage(Principal principal, Map<String, Object> model,
			RedirectAttributes ra) {

		model.put("addHrFormBean", new AddHrFormBean());
		List<AccessRole> allHrUsers = adminUserService.getAllHRUsers();
		model.put("allHrUsers", allHrUsers);
		
		MessageHelper.addSuccessAttribute(ra, "addedHrRole.success");
		
		return principal != null ? "adminUser/adminUser" : "login/login";
		//return principal != null ? "home/adminUser" : "login/login";
	}

	@RequestMapping(value = "/admin/deleteHrUser", method = RequestMethod.POST)
	public String deleteHrUser(@ModelAttribute("email") String email,
			BindingResult result, Map<String, Object> model,
			HttpServletRequest request, Principal principal,
			RedirectAttributes ra) {

		// Delete selected user from accessrole Table
		boolean statusDeleteUser = adminUserService.deleteHrUserRole(email);
		// get remaning all HR from access role table
		List<AccessRole> allHrUsers = adminUserService.getAllHRUsers();
		model.put("allHrUsers", allHrUsers);
		// add form bean to model attribute
		model.put("addHrFormBean", new AddHrFormBean());

		Messages msg = new Messages();
		if (statusDeleteUser) {
			msg.setText("SUCCESS");
			msg.setText("addedHrRole.delete.success");
			msg.setName(email);
		} else {
			msg.setText("DANGER");
			msg.setText("addedHrRole.delete.failure");
			msg.setName(email);
		}

		model.put("messages", msg);

		return principal != null ? "adminUser/adminUser" : "login/login";
	}

	private AccessRole setModel(AddHrFormBean addHrFormBean) {
		AccessRole accessRole = new AccessRole();
		accessRole.setEmail(addHrFormBean.getEmailId());
		accessRole.setFname(addHrFormBean.getFirstName());
		accessRole.setLname(addHrFormBean.getLastName());
		accessRole.setRole(ROLE_HR);
		return accessRole;
	}
}
