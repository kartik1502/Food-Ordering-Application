package org.training.foodorderapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	private String userName;

	private String userEmail;
}
