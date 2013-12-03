package com.hedleyproctor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Wishlist {
	
	private Long id;
	// in a real application, the items on a wishlist would be a set of references to products in the system, but
	// the purpose of this example is to show the one-to-one association between customer and wishlist, so
	// I'm just using a string. I don't want to overcomplicate the example by adding another entity and making a one-to-many
	// association to it
	private String items;
	private Customer customer;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	
	@OneToOne(mappedBy = "wishlist")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
	
}
