package com.jiwoong.assignment2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jiwoong.assignment2.model.Journey;
import com.jiwoong.assignment2.model.Passenger;
import com.jiwoong.assignment2.model.Ticket;
 
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
	Ticket findByJourney(Journey journey);
	Ticket findByTicketId(String ticketId);
	List<Ticket> findAllByPassenger(Passenger passenger);
}
