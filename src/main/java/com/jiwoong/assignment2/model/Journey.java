package com.jiwoong.assignment2.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="journey")
public class Journey {
	
	// Journey Info
	@Id
	@Column(name="journeyId")
	@GeneratedValue(strategy=GenerationType.UUID)
	private String journeyId;
	
	@Column(name="trainCode")
	private String trainCode;
	
	// Departure 
	@Column(name="departureStn")
	private String departureStn;
	
	@Column(name="departureDate")
	@NotNull(message="Please select the departure date")
	@FutureOrPresent(message="Invalid Date")
	private LocalDate departureDate;
	
	// Arrival
	@Column(name="arrivalStn")
	private String arrivalStn;
	
	@Column(name="arrivalDateTime")
	@FutureOrPresent(message="Invalid Date")
	private LocalDate arrivalDate;
	
	// Journey Distance
	@Column(name="travelDistance")
	private int travelDistance;

	//Constructor
	public Journey() {
		super();
	}

	public Journey(String journeyId, String trainCode, String departureStn,
			@FutureOrPresent(message = "Invalid Date") LocalDate departureDate, String arrivalStn,
			@FutureOrPresent(message = "Invalid Date") LocalDate arrivalDate, int travelDistance) {
		super();
		this.journeyId = journeyId;
		this.trainCode = trainCode;
		this.departureStn = departureStn;
		this.departureDate = departureDate;
		this.arrivalStn = arrivalStn;
		this.arrivalDate = arrivalDate;
		this.travelDistance = travelDistance;
	}

	public String getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(String journeyId) {
		this.journeyId = journeyId;
	}

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public String getDepartureStn() {
		return departureStn;
	}

	public void setDepartureStn(String departureStn) {
		this.departureStn = departureStn;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalStn() {
		return arrivalStn;
	}

	public void setArrivalStn(String arrivalStn) {
		this.arrivalStn = arrivalStn;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public int getTravelDistance() {
		return travelDistance;
	}

	public void setTravelDistance(int travelDistance) {
		this.travelDistance = travelDistance;
	}
	
	
	// ToString Methods 
	public String getDepartureDateString() {
		// Format date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(this.departureDate);
		
		return date;
	}
	
	
}
