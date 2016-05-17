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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;






@Entity
@Table(name="objectives")
@NamedQueries({
	@NamedQuery(name = Objective.findAll, query = "select c from Objective c "),
	@NamedQuery(name = Objective.GET_BY_ID, query = "FROM Objective e where e.id = :id"),
@NamedQuery(name = Objective.GET_ALL_SECTION_OBJECTIVES, query = "from Objective a where a.section.id=:section ORDER BY a.objectiveDesc ASC"),
@NamedQuery(name = Objective.DELETE_OBJECTIVE_BY_OBJECTIVEID, query = "delete from Objective a where a.id = :objectiveId"),
@NamedQuery(name = Objective.UPDATE_OBJECTIVE_BY_OBJECTIVEID, query = "update Objective set objectiveDesc=:description  where id = :objectiveId"),
@NamedQuery(name = Objective.FIND_BY_SECTION_ID_NOT_PRESENT , query = "SELECT e FROM Objective e where  e.section.id = :section_id AND e.id  not in ( SELECT objectives.id FROM RoleModel e2 where e2.role.id = :role_id AND e2.section.id = :section_id)")

})
public class Objective {

	public static final String GET_BY_ID = "Objective.GET_BY_ID";
public static final String GET_ALL_SECTION_OBJECTIVES = "Objective.GET_ALL_SECTION_OBJECTIVES";
public static final String DELETE_OBJECTIVE_BY_OBJECTIVEID = "Objective.DELETE_OBJECTIVE_BY_OBJECTIVEID";
public static final String UPDATE_OBJECTIVE_BY_OBJECTIVEID = "Objective.UPDATE_OBJECTIVE_BY_OBJECTIVEID";
public static final String findAll = "Objective.findAll";
public static final String FIND_BY_SECTION_ID_NOT_PRESENT = "Objective.FIND_BY_SECTION_ID_NOT_PRESENT";

public  Objective(){}
public Objective(String objectiveDesc,Section section, Date lastModifiedDate)
{
	this.objectiveDesc=objectiveDesc;
	this.section=section;
	this.lastModifiedDate=lastModifiedDate;

	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "description", nullable = false)
	private String objectiveDesc;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private Section section;

	@Column(name = "last_modified_date", nullable = false)
	private Date lastModifiedDate;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getObjectiveDesc() {
		return objectiveDesc;
	}
	public void setObjectiveDesc(String objectiveDesc) {
		this.objectiveDesc = objectiveDesc;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



}
