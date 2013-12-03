package com.hedleyproctor.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Customer {
	private Long id;
	private String forename;
	private String surname;
	private MarketingPreferences marketingPreferences;
	private UserProfile userProfile;
	private Wishlist wishlist;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public MarketingPreferences getMarketingPreferences() {
		return marketingPreferences;
	}
	public void setMarketingPreferences(MarketingPreferences marketingPreferences) {
		this.marketingPreferences = marketingPreferences;
		marketingPreferences.setCustomer(this);
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
		userProfile.setCustomer(this);
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable( name = "CUSTOMER_WISHLIST", 
		joinColumns = @JoinColumn( name = "CUSTOMER_ID"),
		inverseJoinColumns = @JoinColumn( name = "WISHLIST_ID"))
	public Wishlist getWishlist() {
		return wishlist;
	}
	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
		wishlist.setCustomer(this);
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
