package com.masai.service;

import java.util.List;

import com.masai.exception.SwiggyException;
import com.masai.model.Customer;
import com.masai.model.DeliveryPartner;
import com.masai.model.OrderStatus;
import com.masai.model.Orders;
import com.masai.model.Restaurant;
import com.masai.model.Users;

public interface DeliveryService {
	
	public Users addUser (Users users) throws SwiggyException;
	public Customer addCustomer(Customer customer) throws SwiggyException;
	public Restaurant addRestaurant(Restaurant restaurant) throws SwiggyException;
	public DeliveryPartner addDeliveryPartner(DeliveryPartner deliveryPartner) throws SwiggyException;
	public Orders placeOrder(Integer customerId, Integer resId, Integer devId, Orders order) throws SwiggyException;
	public String assignDeliveryPartner(Integer orderId, Integer deliveryPartnerId) throws SwiggyException;
	public String updateOrderStatus(Integer orderId, OrderStatus newStatus) throws SwiggyException;
	public List<Orders> fetchCustomerOrderHistory(Integer customerId) throws SwiggyException;
	public List<Customer> getAllCustomers() throws SwiggyException;
	public Users getUserDetailByUsername (String username) throws SwiggyException;
	
}
