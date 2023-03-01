package org.training.foodorderapplication.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	public void setUserId(int userId) {
		this.userId = userId;
	}

	private String userName;

	private String userEmail;
}
