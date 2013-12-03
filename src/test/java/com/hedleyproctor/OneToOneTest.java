package com.hedleyproctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hedleyproctor.domain.Customer;
import com.hedleyproctor.domain.MarketingPreferences;
import com.hedleyproctor.domain.UserProfile;
import com.hedleyproctor.domain.Wishlist;

public class OneToOneTest {
    private SessionFactory sessionFactory;

    @BeforeClass
    public void setUp() throws SQLException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
    /** Customer has a foreign key to UserProfile.
     * 
     */
    @Test
    public void foreignKey() {
    	Customer customer = new Customer();
    	customer.setForename("Charles");
    	customer.setSurname("Darwin");
    	UserProfile userProfile = new UserProfile();
    	userProfile.setAccountLocked(false);
    	userProfile.setFailedLoginCount(3);
    	customer.setUserProfile(userProfile);
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(customer);
    	
    	// won't commit the transaction, as doing the query will cause Hibernate to flush to the db anyway
    	// Alternatively, you could commit the transaction and close the session, then start again. The results would be the same.
    	Customer c = (Customer) session.createQuery("from Customer where forename = 'Charles' and surname = 'Darwin'").uniqueResult();
    	tx.commit();
    	session.close();
    	
    	assertEquals(c.getForename(), "Charles");
    	assertEquals(c.getSurname(), "Darwin");
    	UserProfile up2 = c.getUserProfile();
    	assertNotNull(up2);
    	assertEquals(up2.getAccountLocked(),Boolean.FALSE);
    	assertEquals(up2.getFailedLoginCount().intValue(), 3);
    }
    
    /** MarketingPreferences is set to use the same primary key as the Customer primary key. Hence it is both a primary
     * key and a foreign key.
     * 
     */
    @Test
    public void sharedPrimaryKey() {
    	Customer customer = new Customer();
    	customer.setForename("Joe");
    	customer.setSurname("Bloggs");
    	MarketingPreferences marketingPreferences = new MarketingPreferences();
    	marketingPreferences.setEmail(true);
    	marketingPreferences.setPhone(false);
    	marketingPreferences.setPost(true);
    	marketingPreferences.setSms(true);
    	customer.setMarketingPreferences(marketingPreferences);
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(customer);

    	Customer c = (Customer) session.createQuery("from Customer where forename='Joe' and surname='Bloggs'").uniqueResult();
    	tx.commit();
    	session.close();
    	
    	assertEquals(c.getForename(),"Joe");
    	assertEquals(c.getSurname(), "Bloggs");
    	MarketingPreferences mp = c.getMarketingPreferences();
    	assertNotNull(mp);
    	assertEquals(mp.isEmail(),true);
    	assertEquals(mp.isPhone(),false);
    	assertEquals(mp.isPost(),true);
    	assertEquals(mp.isSms(), true);
    	
    }
    
    @Test
    public void joinTable() {
    	Customer customer = new Customer();
    	customer.setForename("Julius");
    	customer.setSurname("Caesar");
    	Wishlist wishlist = new Wishlist();
    	// in a real app a wishlist would have a one-to-many association to the products on the customers wishlist, but
    	// I don't want to overcomplicate this example by adding another association, so I'll just use a dummy string
    	String items = "The Decline and Fall of the Roman Empire,The Twelve Caesars";
    	wishlist.setItems(items);
    	customer.setWishlist(wishlist);
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(customer);
    	Customer c = (Customer)session.createQuery("from Customer where forename='Julius' and surname='Caesar'").uniqueResult();
    	tx.commit();
    	session.close();
    	
    	assertEquals(c.getForename(),"Julius");
    	assertEquals(c.getSurname(),"Caesar");
    	Wishlist w = c.getWishlist();
    	assertNotNull(w);
    	assertTrue(w.getItems().equals("The Decline and Fall of the Roman Empire,The Twelve Caesars"));
    	
    }

}
