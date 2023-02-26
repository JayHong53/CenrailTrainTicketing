package com.jiwoong.assignment2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiwoong.assignment2.model.Journey;
import com.jiwoong.assignment2.model.Passenger;
import com.jiwoong.assignment2.model.Ticket;
import com.jiwoong.assignment2.model.TrainSchedule;
import com.jiwoong.assignment2.repository.JourneyRepository;
import com.jiwoong.assignment2.repository.PassengerRepository;
import com.jiwoong.assignment2.repository.TicketRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/{userId}")
public class JourneyController {
	
	@Autowired
	private JourneyRepository journeyRepo;
	@Autowired
	private PassengerRepository passengerRepo;
	@Autowired
	private TicketRepository ticketRepo;
	
	private TrainSchedule trainSchedule;
	
	public JourneyController() {
		trainSchedule = new TrainSchedule();
	}	
	

//	=================================================
//	Main - GET
//	=================================================	
	@GetMapping
	public String getMain(@PathVariable String userId, Model model) {	
		model.addAttribute("userId", userId);
	    return "plan-main";
	}
	
	
//	=================================================
//	PlanTrip - GET (Destination, Date)
//	=================================================	
	@GetMapping("/plan-trip")
	public String getPlanTrip(@PathVariable String userId, Model model, Journey journey) {
		model.addAttribute("userId", userId);
		return "plan-trip";
	}
	
//	=================================================
//	PlanTrip - POST (Destination, Date => Validation)
//	=================================================	
	@PostMapping("/plan-trip")
	public String postPlanTrip(@PathVariable String userId, Model model, @Valid Journey journey, BindingResult result, String departureStn, String arrivalStn) {
		// Validate route 
		if (departureStn.equals(arrivalStn)) {
			result.rejectValue("departureStn", "error.journey", "Invalid Journey: Please pick a different station");
			result.rejectValue("arrivalStn", "error.journey", "Invalid Journey: Please pick a different station");
		}
		
		if (result.hasErrors()) {
			return "plan-trip"; 
		}
				
		List<String> trainList = trainSchedule.getTrainList(departureStn, arrivalStn);
		
		model.addAttribute("trainList", trainList);
		model.addAttribute("journey", journey);
		
		System.out.println(journey.getDepartureDate());
		
		return "plan-trip-detail";
	}
	
//	=================================================
//  PlanTrip-Detail - POST SeatClass, Berth etc... 
//	=================================================	
	
	@PostMapping("/plan-trip-detail")
	public String postPlanTripDetail(@PathVariable String userId, Model model, @Valid Journey journey, BindingResult result, String trainCode) {
		
		model.addAttribute("trainCode", trainCode);
		model.addAttribute("journey", journey);
		
		return "plan-trip-detail";
	}	
	
//	=================================================
//  PlanTrip-Summary - POST SeatClass, Berth etc... 
//	=================================================	
	@PostMapping("/plan-trip-summary")
	public String postPlanTripSummary(@PathVariable String userId, Model model, @Valid Journey journey, BindingResult result, 
		 String seatClass, int extraSeat, int extraDiscountedSeat) {
		// Save Travel Distance
		int travelDistance = trainSchedule.getDistance(journey.getDepartureStn(), journey.getArrivalStn());
		journey.setTravelDistance(travelDistance);
				
		journeyRepo.save(journey);
		
		Ticket ticket = new Ticket();
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		
		ticket.setPassenger(passenger);
		ticket.setJourney(journey);
		ticket.setSeatClass(seatClass);
		ticket.setExtraSeat(extraSeat);
		ticket.setExtraDiscountedSeat(extraDiscountedSeat);
		ticket.setStatus("Not Paid");
		
		double totalFare = ticket.calculateTotalFare();
		ticket.setTotalFare(totalFare);
		
		ticketRepo.save(ticket);
		
		ticket = ticketRepo.findByJourney(journey);

		return "redirect:/{userId}/ticket/" + ticket.getTicketId();
	}
}
