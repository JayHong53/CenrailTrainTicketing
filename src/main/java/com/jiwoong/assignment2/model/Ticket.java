package com.jiwoong.assignment2.model;

import java.text.DecimalFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Ticket")
public class Ticket {
	
	@Id
	@Column(name="ticketId")
	@GeneratedValue(strategy=GenerationType.UUID)
	private String ticketId;
	
	@OneToOne
	@JoinColumn(name="journeyId")
	private Journey journey;
	
	@ManyToOne
	@JoinColumn(name="passengerId")	
	private Passenger passenger;
	
	@Column(name="seatClass")
	private String seatClass;
	
	@Column(name="extraSeat")
	private int extraSeat;
	
	@Column(name="extraDiscountedSeat")
	private int extraDiscountedSeat;
	
	@Column(name="totalFare")
	private double totalFare;
	
	@Column(name="status")
	private String status;
	
	public Ticket() {
		super();
	}

	public Ticket(String ticketId, Journey journey, Passenger passenger, String seatClass,
			int extraSeat, int extraDiscountedSeat, double totalFare, String status) {
		super();
		this.ticketId = ticketId;
		this.journey = journey;
		this.passenger = passenger;
		this.seatClass = seatClass;
		this.extraSeat = extraSeat;
		this.extraDiscountedSeat = extraDiscountedSeat;
		this.totalFare = totalFare;
		this.status = status;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Journey getJourney() {
		return journey;
	}

	public void setJourney(Journey journey) {
		this.journey = journey;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public int getExtraSeat() {
		return extraSeat;
	}

	public void setExtraSeat(int extraSeat) {
		this.extraSeat = extraSeat;
	}

	public int getExtraDiscountedSeat() {
		return extraDiscountedSeat;
	}

	public void setExtraDiscountedSeat(int extraDiscountedSeat) {
		this.extraDiscountedSeat = extraDiscountedSeat;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public double calculateTotalFare() {
		// Basic fare = distance of travel * 0.1
		double basicFare = this.journey.getTravelDistance() * 0.1;
		
		// Total Seats 
		int seatCount = this.extraSeat;
		int discountedSeatCount = this.extraDiscountedSeat;
		
		if (this.passenger.getAge() <= 17 || this.passenger.getAge() >= 65) {
			discountedSeatCount++;
		}
		else {
			seatCount++;
		}
		
		// Seat Class
		if (this.seatClass.equals("Sleeper-Plus")) {
			basicFare *= 4;
		} 
		else if (this.seatClass.equals("Prestige")) {
			basicFare *= 10;
		}
		
		return ( basicFare * seatCount ) + ( basicFare * 0.85 * discountedSeatCount );
	}
	
	// Return total fare in currency format; 
	public String getTotalFareCurrencyFormat() {
		// Round up to 2 decimal point;
		DecimalFormat formatter = new DecimalFormat("0.00");
		return formatter.format(this.totalFare);
	}
	
//	public String getDepartureDateAndTime(String time) {
//		
//	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", journey=" + journey + ", passenger=" + passenger + ", seatClass="
				+ seatClass + ", extraSeat=" + extraSeat + ", extraDiscountedSeat=" + extraDiscountedSeat
				+ ", totalFare=" + totalFare + ", status=" + status + "]";
	}	
}
