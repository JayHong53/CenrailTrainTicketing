package com.jiwoong.assignment2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jiwoong.assignment2.model.TrainSchedule;

@Controller
public class PassengerController {

	@PostMapping("/login")
	public String login(String email, String password) {
		if (email.isEmpty() || password.isEmpty()) {
			return "index";
		}

		else {
			TrainSchedule trainSchedule = new TrainSchedule();
			
			List<String> trainList = trainSchedule.getTrainList("Toronto", "Vancouver");
			
			for (String s: trainList) {
				System.out.println(s);
			}
			

			return "main";
		}
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(String email, String password, String confirmPassword, Model model) {

		if (email == "" || password == "" || confirmPassword == "") {
			model.addAttribute("errMsg-email", "Please enter valid email and password");

			return "register";
		}

		else if (!password.equals(confirmPassword)) {
			model.addAttribute("errMsg-password", "Password not matching");

			return "register";
		}

		else {
			return "index";
		}
	}
}
