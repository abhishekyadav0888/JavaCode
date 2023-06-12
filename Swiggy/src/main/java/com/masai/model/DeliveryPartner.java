package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryPartner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deliveryPartnerId;
	private String name;
	private String phoneNumber;
	
	@OneToMany(mappedBy = "deliveryPartner" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Orders> orders ;
	@ManyToMany(mappedBy = "deliveryList")
	@JsonIgnore
	private List<Restaurant> restaurants = new ArrayList<>() ;

}
