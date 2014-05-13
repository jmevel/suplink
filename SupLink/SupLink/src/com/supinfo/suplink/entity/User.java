package com.supinfo.suplink.entity;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String email;
	private String password;
	
	//@LazyCollection(LazyCollectionOption.FALSE)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	//@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	//@IndexColumn(name="index_link")
	private List<Link> links;
	
	private Boolean isRegistered;
	
	private String confirmationLink;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public Boolean getIsRegistered() {
		return isRegistered;
	}
	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
	public String getConfirmationLink() {
		return confirmationLink;
	}
	public void setConfirmationLink(String confirmationLink) {
		this.confirmationLink = confirmationLink;
	}
}
