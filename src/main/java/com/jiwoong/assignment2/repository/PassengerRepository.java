package com.jiwoong.assignment2.repository;

import org.springframework.data.repository.CrudRepository;
import com.jiwoong.assignment2.model.Passenger;

public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
	Passenger findByEmail(String email);
	Passenger findByPassengerId(String userId);
}
