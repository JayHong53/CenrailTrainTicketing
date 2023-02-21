package com.jiwoong.assignment2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Passenger")
public class Passenger {
	
	// Login Credential
	@Id
	@Column(name="passengerId")
	private int passengerId;
	@NotNull
	@Column(name="email", unique=true)
	private String email;
	@NotNull
	@Column(name="password")
	private String password;
	
	// Personal Information
	@Column(name="firstname")
	private String firstname;
	@Column(name="lastname")
	private String lastname;
	@Column(name="gender")
	private String gender;
//	@Column(name="age")
//	private int age;
	
	// Contact and Address
	@Column(name="phone")
	private String phone;
	@Column(name="street")
	private String street;
	@Column(name="city")
	private String city;
	@Column(name="province")
	private String province;
	@Column(name="postal")
	private String postal;
	
	public Passenger() {
		super();
	}
	
	public Passenger(int passengerId, String email, String password, String firstname, String lastname, String gender,
			/*int age,*/ String phone, String street, String city, String province, String postal) {
		super();
		this.passengerId = passengerId;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
//		this.age = age;
		this.phone = phone;
		this.street = street;
		this.city = city;
		this.province = province;
		this.postal = postal;
	}






	public int getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
}
