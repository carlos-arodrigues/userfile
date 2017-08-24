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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	@Column(name = "text", length = 510)
	private String text;

	public UserFile() {
	}

	private UserFile(String name, String location, String text) {
		Validate.validState(name != null && !name.contains(" ") && name.length() <= 50);
		Validate.validState(location != null && location.length() <= 100);
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

	public void removeCommasWithSpace() {
		if(StringUtils.isEmpty(this.text)){
			return;
		}
		StringBuffer sb = new StringBuffer();
		char[] array = text.toCharArray();
		for(int i=0 ; i < array.length ; i++){
			if(array[i] == ',' && array[i+1] == ' '){
				i++;
			}
			sb.append(array[i]);
		}
		this.text = sb.toString();
		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.id)
				.append(this.name)
				.append(this.location)
				.append(this.text)
				.build();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserFile)) {
			return false;
		}
		UserFile other = (UserFile) obj;
		return new EqualsBuilder()
				.append(this.id, other.getId())
				.append(this.name, other.getName())
				.append(this.location, other.getLocation())
				.append(this.text, other.getText())
				.isEquals();
	}	

	public static WithMandatories named(String name) {
		return new BuilderUserFile().withName(name);
	}

	public interface WithMandatories {
		public BuilderUserFile withLocation(String location);

		public BuilderUserFile withName(String name);
	}

	public static class BuilderUserFile implements WithMandatories {
		private String name;
		private String location;
		private String text;

		public BuilderUserFile withName(String name) {
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
