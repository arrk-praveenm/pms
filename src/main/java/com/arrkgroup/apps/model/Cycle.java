package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cycle")

@NamedQueries(
{
	@NamedQuery(name = Cycle.FIND_ALL, query = "from Cycle c order by c.id"),
	@NamedQuery(name = Cycle.FIND_BY_ID, query = "from Cycle c where c.id = :id")
})
public class Cycle {
	public static final String FIND_ALL = "Cycle.FIND_ALL";	
	public static final String FIND_BY_ID = "Cycle.FIND_BY_ID";	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name="last_modified_by")
	private String last_modified_by;	
	
	@Column(name="last_modified_date")
	private Date last_modified_date;
	
	@Column(name="start_date")
	private Date start_date;	  
   
	@Column(name="end_date")
	private Date end_date;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLast_modified_by() {
		return last_modified_by;
	}
	
	public void setLast_modified_by(String last_modified_by) {
		this.last_modified_by = last_modified_by;
	}
	
	public Date getLast_modified_date() {
		return last_modified_date;
	}
	
	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}
	
	public Date getStart_date() {
		return start_date;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public Date getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}	

}
