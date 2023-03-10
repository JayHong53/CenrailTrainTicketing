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
		
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
		
		else {
			model.addAttribute("passenger", passenger);
			model.addAttribute("userId", userId);
			return "plan-main";
		}
	}

//	=================================================
//	PlanTrip - GET (Destination, Date)
//	=================================================	
	@GetMapping("/plan-trip")
	public String getPlanTrip(@PathVariable String userId, Model model, Journey journey) {
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
		
		model.addAttribute("userId", userId);
		return "plan-trip";
	}

//	=================================================
//	PlanTrip - POST (Destination, Date => Validation)
//	=================================================	
	@PostMapping("/plan-trip")
	public String postPlanTrip(@PathVariable String userId, Model model, @Valid Journey journey, BindingResult result,
			String departureStn, String arrivalStn) {
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
				
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
		model.addAttribute("trainSchedule", trainSchedule);

		System.out.println(journey.getDepartureDate());

		return "plan-trip-detail";
	}

//	=================================================
//  PlanTrip-Detail - POST SeatClass, Berth etc... 
//	=================================================	

	@PostMapping("/plan-trip-detail")
	public String postPlanTripDetail(@PathVariable String userId, Model model, @Valid Journey journey,
			BindingResult result, String trainCode) {
		
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
		
		model.addAttribute("trainCode", trainCode);
		model.addAttribute("journey", journey);
		model.addAttribute("trainSchedule", trainSchedule);

		return "plan-trip-detail";
	}

//	=================================================
//  PlanTrip-Summary - POST SeatClass, Berth etc... 
//	=================================================	
	@PostMapping("/plan-trip-summary")
	public String postPlanTripSummary(@PathVariable String userId, Model model, @Valid Journey journey,
			BindingResult result, String seatClass, int extraSeat, int extraDiscountedSeat) {
	
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}
		
		// Save Travel Distance
		int travelDistance = trainSchedule.getDistance(journey.getDepartureStn(), journey.getArrivalStn());
		journey.setTravelDistance(travelDistance);

		journeyRepo.save(journey);

		Ticket ticket = new Ticket();

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

//	=================================================
//  Profile Update
//	=================================================	

	@GetMapping("/update-profile")
	public String getUpdateProfile(@PathVariable String userId, Model model) {

		// If userId is not valid, redirect user to the main page
		Passenger passenger = passengerRepo.findByPassengerId(userId);
		if (passenger == null) {
			return "redirect:/";
		}

		model.addAttribute("passenger", passenger);
		model.addAttribute("userId", userId);

		return "update-profile";
	}

	@PostMapping("/update-profile")
	public String getUpdateProfile(@PathVariable String userId, Model model, @Valid Passenger passenger,
			BindingResult result) {
		
		// Password is not passed, so error count should be 1 if everything is validated
		if (result.getErrorCount() >= 1) {
							
			model.addAttribute("userId", userId);
			return "update-profile";

		} else {
			
			Passenger existingPassenger = passengerRepo.findByPassengerId(userId);
			if (existingPassenger == null) {
				return "redirect:/";
			}
			
			existingPassenger.setFirstname(passenger.getFirstname());
			existingPassenger.setLastname(passenger.getLastname());
			existingPassenger.setGender(passenger.getGender());
			existingPassenger.setAge(passenger.getAge());
			existingPassenger.setPhone(passenger.getPhone());
			existingPassenger.setStreet(passenger.getStreet());
			existingPassenger.setCity(passenger.getCity());
			existingPassenger.setProvince(passenger.getProvince());
			existingPassenger.setPostal(passenger.getPostal());
			
			passengerRepo.save(existingPassenger);
			
			return "redirect:/{userId}";
		}
	}
}
