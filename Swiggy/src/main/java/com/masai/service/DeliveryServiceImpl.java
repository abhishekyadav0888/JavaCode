package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.exception.SwiggyException;
import com.masai.model.Customer;
import com.masai.model.DeliveryPartner;
import com.masai.model.OrderStatus;
import com.masai.model.Orders;
import com.masai.model.Restaurant;
import com.masai.model.Users;
import com.masai.repository.CustomerRepo;
import com.masai.repository.DeliveryPartnerRepo;
import com.masai.repository.OrdersRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	@Autowired
	private CustomerRepo cRepo ;
	@Autowired
	private RestaurantRepo rRepo;
	@Autowired
	private DeliveryPartnerRepo dRepo;
	@Autowired
	private OrdersRepo oRepo;
	@Autowired
	private UserRepo uRepo;

	@Override
	public Users addUser(Users users) throws SwiggyException {
		if(users == null) {
			log.warn("User is empty.");
			throw new SwiggyException("User is null.");
		}
		Optional<Users> us = uRepo.findByUsername(users.getUsername());
		if(us.isPresent()) {
			log.warn("User is already present.");
			throw new SwiggyException("User is already present in database.");
		}
		Users savedUser = uRepo.save(users);
		log.info("User is added succesfully.");
		return savedUser;
	}

	@Override
	public Customer addCustomer(Customer customer) throws SwiggyException {
		if(customer == null) throw new SwiggyException("Customer is null.");
		Optional<Customer> cus = cRepo.findByName(customer.getName());
		if(cus.isPresent()) throw new SwiggyException("Customer is already present in database.");
		Customer savedCustomer = cRepo.save(customer);
		return savedCustomer;
	}

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) throws SwiggyException {
		if(restaurant == null) throw new SwiggyException("Restaurant is null.");
		Optional<Restaurant> res = rRepo.findByName(restaurant.getName());
		if(res.isPresent()) throw new SwiggyException("Restaurant is already present in database.");
		Restaurant savedRestaurant = rRepo.save(restaurant);
		return savedRestaurant;
	}

	@Override
	public DeliveryPartner addDeliveryPartner(DeliveryPartner deliveryPartner) throws SwiggyException {
		if(deliveryPartner == null) throw new SwiggyException("DeliveryPartner is null.");
		Optional<DeliveryPartner> dp = dRepo.findByPhoneNumber(deliveryPartner.getPhoneNumber());
		if(dp.isPresent()) throw new SwiggyException("DeliveryPartner is already present in database.");
		DeliveryPartner savedDP = dRepo.save(deliveryPartner);
		return savedDP;
	}

	@Override
	public Orders placeOrder(Integer customerId, Integer resId, Integer devId, Orders order) throws SwiggyException {
		Optional<Customer> cus = cRepo.findById(customerId);
		Optional<Restaurant> res = rRepo.findById(resId);
		Optional<DeliveryPartner> dpart = dRepo.findById(devId);
		if(cus.isEmpty() || res.isEmpty() || dpart.isEmpty() || order==null) throw new SwiggyException("Null value provided.");
		order.setCustomer(cus.get());
		order.setDeliveryPartner(dpart.get());
    	order.setRestaurant(res.get());
    	res.get().getOrders().add(order);
    	cus.get().getOrders().add(order);
    	dpart.get().getOrders().add(order);
    	oRepo.save(order);
		return order;
	}

	@Override
	public String assignDeliveryPartner(Integer orderId, Integer deliveryPartnerId) throws SwiggyException {
		Optional<DeliveryPartner> dpart = dRepo.findById(deliveryPartnerId);
    	Optional<Orders> order = oRepo.findById(orderId);
    	if(dpart.isEmpty() || order.isEmpty()) throw new SwiggyException("null value") ;
    	dpart.get().getOrders().add(order.get());
    	order.get().setDeliveryPartner(dpart.get());
    	oRepo.save(order.get());
		return "DeliveryPartner is assigned to order.";
	}

	@Override
	public String updateOrderStatus(Integer orderId, OrderStatus newStatus) throws SwiggyException {
		Optional<Orders> order = oRepo.findById(orderId) ;
        if (order.get().getOrderStatus() == OrderStatus.DELIVERED) {
            throw new SwiggyException("Cannot change the status of a delivered order") ;
        }
        order.get().setOrderStatus(newStatus);
        oRepo.save(order.get());
		return "Order status updated successfully.";
	}

	@Override
	public List<Orders> fetchCustomerOrderHistory(Integer customerId) throws SwiggyException {
		Optional<Customer> cus = cRepo.findById(customerId ) ;
    	if(cus.isEmpty()) throw new SwiggyException("null value") ;
    	if(cus.get().getOrders().isEmpty()) throw new SwiggyException("Empty order list") ;
    	return cus.get().getOrders();
	}

	@Override
	public List<Customer> getAllCustomers() throws SwiggyException {
		Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
		List<Customer> customersList = cRepo.findAll(pageable).getContent();
		if(customersList.isEmpty()) throw new SwiggyException("Empty customer list");
		return customersList;
	}

	@Override
	public Users getUserDetailByUsername(String username) throws SwiggyException {
		return uRepo.findByUsername(username).orElseThrow(() -> new SwiggyException("User not found this user name: " + username));
	}

}
