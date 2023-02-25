package com.jiwoong.assignment2.controller;

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
		
		List<Ticket> tickets = ticketRepo.findAllByPassenger(passenger);
		
		System.out.println(tickets.size());
		
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
		
		model.addAttribute("userId", userId);
		model.addAttribute("ticketId", ticketId);
		model.addAttribute("ticket", ticket);
		return "ticket-pay";
	}
	
	@PostMapping("/{ticketId}/payticket")
	public String postPayTicket(@PathVariable String userId, @PathVariable String ticketId, Model model) {
		
		// Invalid TicketId => Redirect to Ticket list
		Ticket ticket = ticketRepo.findByTicketId(ticketId);
		if (ticket == null) {
			
			return "redirect:/{userId}/ticket/";			
		}			
		
		ticket.setStatus("Paid");
		ticketRepo.save(ticket);
		
		return "redirect:/{userId}/ticket/";
	}
}