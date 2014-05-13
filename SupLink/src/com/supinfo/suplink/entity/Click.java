package com.supinfo.suplink.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import java.util.Date;


@Entity
@Table(name="clicks")
public class Click {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="link_fk")
	private Link link;
	
	private Date clickDate;
	private String referer;
	private String country;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getClickDate() {
		return clickDate;
	}
	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}
	
	public String getReferer() {
		return referer;
	}
	public void setReferer(String refferer) {
		this.referer = refferer;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	/*public Link getLink() {
		return link;
	}
	public void setLink(Link link) {
		this.link = link;
	}*/
}
