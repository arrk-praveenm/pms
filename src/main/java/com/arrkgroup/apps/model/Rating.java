package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
@NamedQueries({

	@NamedQuery(name = Rating.GET_RATING_MAX, query = "select max(r.score) FROM Rating r "),
	@NamedQuery(name = Rating.GET_RATING_ID, query = "FROM Rating where id=:id "),
	@NamedQuery(name= Rating.GET_ALL_RATING, query = "FROM Rating where score != 0")
	
})
public class Rating {

	public static final String GET_RATING_MAX= "Project.GET_RATING_MAX";
	public static final String GET_RATING_ID= "Project.GET_RATING_ID";
	public static final String GET_ALL_RATING= "Project.GET_ALL_RATING";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;	
	
	int score;
	String description;
	Date last_modified_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLast_modified_date() {
		return last_modified_date;
	}
	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	
	
}
