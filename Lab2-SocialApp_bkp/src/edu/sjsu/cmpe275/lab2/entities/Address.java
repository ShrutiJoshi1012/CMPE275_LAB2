package edu.sjsu.cmpe275.lab2.entities;

/*
 * Embeddedable entity - Address
 * Although at database level there is no separate table for Address, 
 * but at application level to simplify the code, the Address entity is declared separately
 * 
 * Address is embedded in both Person and Organization
 */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;



@Embeddable
public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	
	
	public Address(){
		
	}
	
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}



	@Column(name = "Street", length = 100)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "City", length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "State", length = 30)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "Zip", length = 10)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
