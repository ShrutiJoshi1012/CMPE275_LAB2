package edu.sjsu.cmpe275.lab2.entities;

// Generated Nov 2, 2015 9:04:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Organization generated by hbm2java
 */
@Entity
@Table(name = "Organization", catalog = "CMPE275_LAB2")
public class Organization implements java.io.Serializable {

	private long organizationId;
	private String name;
	private String description;

	
	@Embedded
	private Address address;
	
	public Organization() {
	}

	public Organization(String name) {
		this.name = name;
	}
	
	public Organization(long organizationid) {
		this.organizationId = organizationid;
	}

	public Organization(String name, String description, Address address) {
		this.name = name;
		this.description = description;
		this.address=address;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrganizationID", unique = true, nullable = false)
	public long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "Name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Description", length = 50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


}
