package com.jiwoong.assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.jiwoong.assignment2.model.Passenger;
import com.jiwoong.assignment2.repository.PassengerRepository;

@SpringBootApplication
public class JiwoongHongComp303Assignment2Application {
	
	public static void main(String[] args) {
		
		
		SpringApplication.run(JiwoongHongComp303Assignment2Application.class, args);	
		System.out.println("COMP303 Assignment2 - Jiwoong Hong");
	}

}
