package org.training.foodorderapplication.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ordersId;

	@OneToMany(cascade = CascadeType.ALL)
	private List<FoodQuantity> foodQuantities;

	@ManyToOne
	private Users user;

	@CreationTimestamp
	private LocalDate orderDate;

}
