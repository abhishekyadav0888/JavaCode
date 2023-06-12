package com.masai.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private Integer customerId;
	private Integer restaurantId;
	private Integer deliveryPartnerId;
	private List<String> items;
	private OrderStatus orderStatus;
	
	@ManyToOne
	private Customer customer ;
	@ManyToOne
	private Restaurant restaurant ;
	@ManyToOne
	private DeliveryPartner deliveryPartner ;

}
