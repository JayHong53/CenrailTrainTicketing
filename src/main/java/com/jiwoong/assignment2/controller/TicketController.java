package com.jiwoong.assignment2.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiwoong.assignment2.model.Passenger;
import com.jiwoong.assignment2.model.Ticket;
import com.jiwoong.assignment2.model.TrainSchedule;
import com.jiwoong.assignment2.repository.JourneyRepository;
import com.jiwoong.assignment2.repository.PassengerRepository;
import com.jiwoong.assignment2.repository.TicketRepository;

@Controller
@RequestMapping("/{userId}/ticket")
public class TicketController {
	
	@Autowired
	private PassengerRepository passengerRepo;
	@Autowired 
	private JourneyRepository journeyRepo;
	@Autowired
	private TicketRepository ticketRepo;
		
	@GetMapping("/")
	public String getTicketList(@PathVariable String userId, Model model) {
		
		// If user id is not valid, redirect to the login page;
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
		
		// Sort by the departure date
		List<Ticket> tickets = ticketRepo.findAllByPassenger(passenger);
	    Collections.sort(tickets, (t1, t2) -> t1.getJourney().getDepartureDate().compareTo(t2.getJourney().getDepartureDate()));
	
		model.addAttribute("tickets", tickets);
		
		return "ticket-list";
	}
	
	@GetMapping("/{ticketId}")
	public String getTicketDetail(@PathVariable String userId, @PathVariable String ticketId, Model model) {
		
		// Invalid TicketId => Redirect to Ticket list
		Ticket ticket = ticketRepo.findByTicketId(ticketId);
		if (ticket == null) {
			
			return "redirect:/{userId}/ticket/";
				
		}	
		
		
		TrainSchedule trainSchedule = new TrainSchedule();
		HashMap<String, String> journeyInfo = trainSchedule.getDetailedJourneyStringMap(ticket.getJourney());
		
		model.addAttribute("passenger", ticket.getPassenger());
		model.addAttribute("journeyInfo", journeyInfo);
		model.addAttribute("ticket", ticket);
		model.addAttribute("userId", userId);
		
		return "ticket-detail";
	} 
	
	@GetMapping("/{ticketId}/payticket")
	public String getPayTicket(@PathVariable String userId, @PathVariable String ticketId, Model model) {
		
		// Invalid TicketId => Redirect to Ticket list
		Ticket ticket = ticketRepo.findByTicketId(ticketId);
		if (ticket == null) {
			
			return "redirect:/{userId}/ticket/";			
		}	
		
		int[] years = new int[20];
		for (int i = 0; i < 20; i++) {
			years[i] = 2023 + i;
		}
		String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		model.addAttribute("years", years);
		model.addAttribute("months", months);
		model.addAttribute("userId", userId);
		model.addAttribute("ticketId", ticketId);
		model.addAttribute("ticket", ticket);
		
		return "ticket-pay";
	}
	
	@PostMapping("/{ticketId}/payticket")
	public String postPayTicket(@PathVariable String userId, @PathVariable String ticketId, Model model, String creditCardNumber, String holderName) {
		
		// Invalid TicketId => Redirect to Ticket list
		Ticket ticket = ticketRepo.findByTicketId(ticketId);
		if (ticket == null) {
			
			return "redirect:/{userId}/ticket/";			
		}		
		
		boolean hasError = false;
		
	    if (!creditCardNumber.matches("\\d{16}")) {
	    	model.addAttribute("errorMsgCredit", "Invalid Credit Card Number");
	    	hasError = true;
	    }
	    
	    // if there's an error
	    if (hasError) {
	    	// Return 
	    	int[] years = new int[20];
			for (int i = 0; i < 20; i++) {
				years[i] = 2023 + i;
			}
			String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			
			model.addAttribute("years", years);
			model.addAttribute("months", months);
			model.addAttribute("userId", userId);
			model.addAttribute("ticketId", ticketId);
			model.addAttribute("ticket", ticket);
	    	
			return "ticket-pay";
	    }
	    	    
	    else {
	    	ticket.setStatus("Paid");
	    	ticketRepo.save(ticket);
	    	
	    	model.addAttribute("userId", userId);
	    	return "ticket-thankyou"; 
		}
	}
}
