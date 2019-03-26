package cdb.core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long userID;
	private String username;
	private String password;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public User() {
		super();
	}

	public User(long userID, String username, String password, Role role) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(User anotherUser) {
		if (anotherUser != null) {
			this.userID = anotherUser.userID;
			this.username = anotherUser.getPassword();
			this.password = anotherUser.getPassword();
			this.role = anotherUser.getRole();
		}
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getStringRole() {
		return role.getRole();
	}

}
