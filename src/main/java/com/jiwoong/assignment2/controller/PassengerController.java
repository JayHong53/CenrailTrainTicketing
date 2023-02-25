package com.jiwoong.assignment2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiwoong.assignment2.model.Passenger;
import com.jiwoong.assignment2.repository.PassengerRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class PassengerController {

	@Autowired
	private PassengerRepository passengerRepo;
	
//	=================================================
//	Login - GET
//	=================================================
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
//	=================================================
//	Login - POST
//	=================================================
	@PostMapping("/login")
	public String postLogin(String email, String password, Model model) {
		if (email.isEmpty() || password.isEmpty()) {
			model.addAttribute("errorMsg", "Please enter your email and password");
			return "login";
		}
		
		else {
			Passenger existingPassenger = passengerRepo.findByEmail(email);		
			if (existingPassenger == null) {
				model.addAttribute("errorMsg", "User not exist");
				return "login";
				
			}
			else {	
				if (!existingPassenger.getPassword().equals(password)) {
					model.addAttribute("errorMsg", "Password not matched");
					return "login";
				}
				
				else {
					return "redirect:/" + existingPassenger.getPassengerId();									
				}
			}
		}
	}

	
//	=================================================
//	Register - GET
//	=================================================	
	@GetMapping("/register")
	public String getRegister(Passenger passenger) {
		return "register";
	}

	
//	=================================================
//	Register - POST
//	=================================================	
	@PostMapping("/register")
	public String postRegister(@Valid Passenger passenger, BindingResult result, Model model) {
		if (result.hasErrors()) {
			
			//
			for (ObjectError err : result.getAllErrors()) {
				System.out.println(err);
			}
			
			return "register";
		} else {
			// Check if email is already taken
			Passenger existingPassenger = passengerRepo.findByEmail(passenger.getEmail());			
			if (existingPassenger != null) {
				result.rejectValue("email", "error.passenger", "This email address is already taken");			
				return "register";
			} else {
				passengerRepo.save(passenger);
				return "login";
			}
		}
	}
}
