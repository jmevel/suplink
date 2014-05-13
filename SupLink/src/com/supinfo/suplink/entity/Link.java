package com.supinfo.suplink.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.net.*;
import java.util.*;

@Entity
@Table(name="links")
public class Link {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private URL originalURL;
	private URL shortenedURL;
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;
	
	//@LazyCollection(LazyCollectionOption.FALSE)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	/*@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
        org.hibernate.annotations.CascadeType.DELETE,
        org.hibernate.annotations.CascadeType.MERGE,
        org.hibernate.annotations.CascadeType.PERSIST})*/
	/*@LazyCollection(LazyCollectionOption.FALSE)*/
	@OneToMany(mappedBy="link", cascade=CascadeType.ALL/*, fetch=FetchType.EAGER*/)
	//@IndexColumn(name="index_click")
	private List<Click> clicks;
	
	private Date creationDate;
	private Boolean isEnable;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public URL getOriginalURL() {
		return originalURL;
	}
	public void setOriginalURL(URL originalURL) {
		this.originalURL=originalURL;
	}
	
	public URL getShortenedURL() {
		return shortenedURL;
	}
	public void setShortenedURL(URL shortenedURL) {
		this.shortenedURL = shortenedURL;
	}
	
	public List<Click> getClicks() {
		return clicks;
	}
	public void setClicks(List<Click> clicks) {
		this.clicks = clicks;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
