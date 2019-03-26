package cdb.core.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import cdb.core.types.EnumRole;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="role_id")
	private Long id;
	private String role;
	@OneToMany
	@JoinColumn(name="user_id")
	private Collection<User> users=new ArrayList<User>();
	
	public Role() {
		super();
	}
	
	public Role(EnumRole role) {
		this.role=role.getRole();
	}

	public String getRole() {
		return role;
	}

	public void setRole(EnumRole role) {
		this.role = role.getRole();
	}
	


	
}
