package com.jiwoong.assignment2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Passenger")
public class Passenger {
	
	// Login Credential
	@Id
	@Column(name="passengerId")
	@GeneratedValue(strategy=GenerationType.UUID)
	private String passengerId;
	
	@Column(name="email")
	@NotBlank(message="Please, provide your email")
	@Email(message="Invalid Email format")
	private String email;
	
	@Column(name="password")
	@Size(min=8, message="Your password should be at least 8 letters")
	private String password;
	
	// Personal Information
	@Column(name="firstname")
	@NotBlank(message="Enter your firstname")
	private String firstname;

	@Column(name="lastname")
	@NotBlank(message="Enter your lastname")
	private String lastname;
	
	@Column(name="gender")
	@NotBlank(message="Please select your gender")
	private String gender;

	@Column(name="age")
	@Min(value=1, message="Please enter a valid age")
	private int age;
	
	@Column(name="phone")
	@Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format XXX-XXX-XXXX")
	private String phone;
	
	@Column(name="street")
	@NotBlank(message="Please enter your street address")
	private String street;
	
	@Column(name="city")
	@NotBlank(message="Please enter your city")
	private String city;
	
	@Column(name="province")
	@NotBlank(message="Please choose your province")
	private String province;
	
	@Column(name="postal")
	@Pattern(regexp = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$", message = "Invalid Canadian postal code.")
	private String postal;
	
	public Passenger() {
		super();
	}
	
	public Passenger(String passengerId, String email, String password, String firstname, String lastname, String gender,
			int age, String phone, String street, String city, String province, String postal) {
		super();
		this.passengerId = passengerId;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.street = street;
		this.city = city;
		this.province = province;
		this.postal = postal;
	}

	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
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
