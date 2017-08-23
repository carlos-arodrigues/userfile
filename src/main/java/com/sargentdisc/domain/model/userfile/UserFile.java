package com.sargentdisc.domain.model.userfile;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

@Entity
@Table(name = "USERFILE")
@Access(AccessType.FIELD)
public class UserFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "text")
	private String text;
	
	public UserFile(){}
	
	public UserFile(String name, String location, String text) {
		Validate.validState(name != null && !name.contains(" ") && name.length() <=50);
		Validate.validState(location != null && location.length() <=100);
		this.name = name;
		this.location = location;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getText() {
		return text;
	}

	public static WithMandatories named(String name) {
		return new BuilderUserFile().withName(name);
	}
	
	public interface WithMandatories{
		public BuilderUserFile withLocation(String location);
		public BuilderUserFile withName(String name);
	}
	
	public static class BuilderUserFile implements WithMandatories{
		private String name;
		private String location;
		private String text;
		
		public BuilderUserFile withName(String name){
			this.name = name;
			return this;
		}
		
		public BuilderUserFile withLocation(String location) {
			this.location = location;
			return this;
		}
		
		public BuilderUserFile withText(String text) {
			this.text = text;
			return this;
		}
		
		public UserFile build() {
			return new UserFile(name, location, text);
		}

	}

	
}
