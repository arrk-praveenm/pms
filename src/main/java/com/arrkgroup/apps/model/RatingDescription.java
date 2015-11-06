package com.arrkgroup.apps.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name="rating_description")
@NamedQueries({@NamedQuery(name = RatingDescription.GET_RATING_DESCRIPTION_BY_SECTIONID, query = "from RatingDescription e where section.id=:sectionId and rating.id!= 1"),})
public class RatingDescription {
	
	public static final String GET_RATING_DESCRIPTION_BY_SECTIONID = "RatingDescription.GET_RATING_DESCRIPTION_BY_SECTIONID";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rating")
	Rating rating;
	
	String description;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="section")
	Section section;
	Date last_modified_date;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Date getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	

}
