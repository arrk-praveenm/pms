package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;




@Entity
@Table(name="role_objectives")
@NamedQueries({
	@NamedQuery(name = RoleModel.FIND_BY_ROLE_ID, query = "FROM RoleModel e where e.role.id = :role_id"),
	@NamedQuery(name = RoleModel.FIND_BY_ROLE_SECTION, query = "FROM RoleModel e where e.role.id = :role_id AND e.section.id = :section_id"),
	@NamedQuery(name = RoleModel.CHECK_BY_ROLE_SECTION, query = "FROM RoleModel e where e.role.id = :role_id AND e.section.id = :section_id AND e.objectives.id= :objective_id"),
@NamedQuery(name = RoleModel.findAll, query = "from RoleModel c "),
@NamedQuery(name = RoleModel.delete_BY_role_id, query = "delete  from RoleModel a where a.role.id = :role_id"),
	@NamedQuery(name = RoleModel.delete_BY_role_id_section_id, query = "delete  from RoleModel a where a.role.id = :role_id AND a.section.id = :section_id" )
})
public class RoleModel {
	public static final String findAll = "RoleModel.findAll";
	public static final String FIND_BY_ROLE_ID = "RoleModel.FIND_BY_ROLE_ID";
	public static final String delete_BY_role_id = "RoleModel.delete_BY_role_id";
	public static final String FIND_BY_ROLE_SECTION = "RoleModel.FIND_BY_ROLE_SECTION";
	public static final String CHECK_BY_ROLE_SECTION = "RoleModel.CHECK_BY_ROLE_SECTION";
	public static final String delete_BY_role_id_section_id = "RoleModel.delete_BY_role_id_section_id";
	
	
	
	
	public RoleModel() {
		super();
	}
	public RoleModel(Date last_modified_date, Role role, Objective objectives,
			Section section) {
		super();
		this.last_modified_date = last_modified_date;
		this.role = role;
		this.objectives = objectives;
		this.section = section;
	}
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	

	
	@Column(name="last_modified_date")
	private Date last_modified_date;
	
	
	
	
	   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role ;
	
	
	   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "objective_id")
    private Objective objectives ;
	
	   @ManyToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "section_id")
	    private Section section;
	
	
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Objective getObjectives() {
		return objectives;
	}
	public void setObjectives(Objective objectives) {
		this.objectives = objectives;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	
	
	

}
