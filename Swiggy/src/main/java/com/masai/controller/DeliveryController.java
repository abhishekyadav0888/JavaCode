package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.Customer;
import com.masai.model.DeliveryPartner;
import com.masai.model.OrderStatus;
import com.masai.model.Orders;
import com.masai.model.Restaurant;
import com.masai.model.Users;
import com.masai.service.DeliveryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/signIn")
	public ResponseEntity<String> getLoggedInCustomerDetailsHandler(Authentication auth){
		System.out.println(auth); // this Authentication object having Principle object details
		Users user = deliveryService.getUserDetailByUsername(auth.getName());
		 
		return new ResponseEntity<>(user.getUsername()+" Logged In Successfully.", HttpStatus.ACCEPTED);	
	}
	
	@PostMapping("/users")
	public ResponseEntity<Users> addUser(@RequestBody Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setRole("ROLE_" + users.getRole().toUpperCase());
		Users savedUser = deliveryService.addUser(users);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		Customer savedCust = deliveryService.addCustomer(customer);
		return new ResponseEntity<>(savedCust, HttpStatus.CREATED);
	}
	
	@PostMapping("/restaurants")
	public ResponseEntity<Restaurant> addRestaurantHandler(@RequestBody Restaurant restaurant){
		Restaurant savedRes = deliveryService.addRestaurant(restaurant);
		return new ResponseEntity<>(savedRes, HttpStatus.CREATED);
	}
	
	@PostMapping("/deliveryPartners")
	public ResponseEntity<DeliveryPartner> addDeliveryPartnerHandler(@RequestBody DeliveryPartner deliveryPartner){
		DeliveryPartner savedDP = deliveryService.addDeliveryPartner(deliveryPartner);
		return new ResponseEntity<>(savedDP, HttpStatus.CREATED);
	}
	
	@PostMapping("/orders/{cusId}/{resId}/{dpartId}")
	public ResponseEntity<Orders> placeOrderHandler(@RequestBody Orders order, @PathVariable("cusId") Integer cus, @PathVariable Integer resId, @PathVariable Integer dpartId){
		Orders savedOrder = deliveryService.placeOrder(cus, resId, dpartId, order);
		return new ResponseEntity<>(savedOrder, HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/orders/{orderId}/deliveryPartner/{deliveryPartnerId}")
	public ResponseEntity<String> assignDeliveryPartnerHandler(@PathVariable Integer orderId, @PathVariable Integer deliveryPartnerId){
		String msg = deliveryService.assignDeliveryPartner(orderId, deliveryPartnerId);
		return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/orders/{orderId}/status/{orderStatus}")
    public ResponseEntity<String> updateOrderStatusHandler(@PathVariable Integer orderId, @PathVariable OrderStatus orderStatus) {
        String msg = deliveryService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
    }
	
	@GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<Orders>> fetchCustomerOrderHistoryHandler(@PathVariable Integer customerId) {
        List<Orders> orders = deliveryService.fetchCustomerOrderHistory(customerId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomersHandler() {
		List<Customer> customers = deliveryService.getAllCustomers();
		return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
	}

}
