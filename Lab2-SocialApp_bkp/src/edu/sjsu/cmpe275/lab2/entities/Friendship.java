package edu.sjsu.cmpe275.lab2.entities;

// Generated Nov 4, 2015 12:22:03 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Friendship generated by hbm2java
 */
@Entity
@Table(name = "FRIENDSHIP", catalog = "CMPE275_LAB2")
public class Friendship implements java.io.Serializable {


	private int personId;
	private int friendId;

	public Friendship() {
	}

	public Friendship(int personId, int friendId) {
		this.personId = personId;
		this.friendId = friendId;
	}

	@Id
	@Column(name = "PersonID", nullable = false)
	public int getPersonId() {
		return this.personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	@Id
	@Column(name = "FriendID", nullable = false)
	public int getFriendId() {
		return this.friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

}
