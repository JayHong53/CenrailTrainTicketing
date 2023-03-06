package com.jiwoong.assignment2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TrainSchedule {
	
	// Key: StationName, Value: coordinate;
	private HashMap<String, Integer> stationCoordinate;
	
	// Key: TrainCode, Value: keyvaluePair <StationName, ScheduledTime>
	private HashMap<String, HashMap<String, String>> trainSchedule; 
	
	private List<String> eastboundTrains;
	private List<String> westboundTrains;
	
	public TrainSchedule() {
		// Populate Trains; 
		westboundTrains = new ArrayList<>(Arrays.asList("WestBound 400", "WestBound 401", "WestBound 402"));
		eastboundTrains = new ArrayList<>(Arrays.asList("EastBound 500", "EastBound 501", "EastBound 502"));
		
		// Populate Station Coordinates;
		stationCoordinate = new HashMap<>();
		stationCoordinate.put("Toronto", 0);
		stationCoordinate.put("Winnipeg", 1950);
		stationCoordinate.put("Saskatoon", 2700);
		stationCoordinate.put("Edmonton", 3200);
		stationCoordinate.put("Jasper", 3600);
		stationCoordinate.put("Vancouver", 4400);
		
		// Populate Train Schedule
		trainSchedule = new HashMap<>();

		// Train info
		// WestBound 400
		HashMap<String, String> wb400 = new HashMap<>();
		wb400 = new HashMap<>();
		wb400.put("Toronto", "Day 1, 09:45");
		wb400.put("Winnipeg", "Day 2, 21:30");
		wb400.put("Saskatoon", "Day 3, 11:50");
		wb400.put("Edmonton", "Day 4, 00:01");
		wb400.put("Jasper", "Day 4, 09:30");
		wb400.put("Vancouver", "Day 5, 08:00");
		trainSchedule.put("WestBound 400", wb400);
		
		// WestBound 401
		HashMap<String, String> wb401 = new HashMap<>();
		wb401 = new HashMap<>();
		wb401.put("Toronto", "Day 1, 12:45");
		wb401.put("Winnipeg", "Day 3, 00:30");
		wb401.put("Saskatoon", "Day 3, 14:50");
		wb401.put("Edmonton", "Day 4, 03:01");
		wb401.put("Jasper", "Day 4, 12:30");
		wb401.put("Vancouver", "Day 5, 11:00");
		trainSchedule.put("WestBound 401", wb401);
		
		// WestBound 402
		HashMap<String, String> wb402 = new HashMap<>();
		wb402 = new HashMap<>();
		wb402.put("Toronto", "Day 1, 17:45");
		wb402.put("Winnipeg", "Day 3, 05:30");
		wb402.put("Saskatoon", "Day 3, 19:50");
		wb402.put("Edmonton", "Day 4, 08:01");
		wb402.put("Jasper", "Day 4, 17:30");
		wb402.put("Vancouver", "Day 5, 16:00");
		trainSchedule.put("WestBound 402", wb402);
				
		// EastBound 500
		HashMap<String, String> eb500 = new HashMap<>();
		eb500.put("Vancouver", "Day 1, 09:00");
		eb500.put("Jasper", "Day 2, 06:30");
		eb500.put("Edmonton", "Day 2, 13:50");
		eb500.put("Saskatoon", "Day 3, 00:57");
		eb500.put("Winnipeg", "Day 3, 17:30");		
		eb500.put("Toronto", "Day 5, 08:29");	
		trainSchedule.put("EastBound 500", eb500);
		
		// EastBound 501
		HashMap<String, String> eb501 = new HashMap<>();
		eb501.put("Vancouver", "Day 1, 15:00");
		eb501.put("Jasper", "Day 2, 12:30");
		eb501.put("Edmonton", "Day 2, 19:50");
		eb501.put("Saskatoon", "Day 3, 06:57");
		eb501.put("Winnipeg", "Day 3, 23:30");		
		eb501.put("Toronto", "Day 5, 14:29");
		trainSchedule.put("EastBound 501", eb501);

		// EastBound 502
		HashMap<String, String> eb502 = new HashMap<>();
		eb502.put("Vancouver", "Day 1, 20:00");
		eb502.put("Jasper", "Day 2, 17:30");
		eb502.put("Edmonton", "Day 3, 00:50");
		eb502.put("Saskatoon", "Day 3, 11:57");
		eb502.put("Winnipeg", "Day 4, 04:30");		
		eb502.put("Toronto", "Day 5, 19:29");
		trainSchedule.put("EastBound 502", eb502);
	}
		
	public List<String> getTrainList(String departure, String arrival){
		int departureCoordinate = stationCoordinate.get(departure);
		int arrivalCoordinate = stationCoordinate.get(arrival);
		
		// 
		if (departureCoordinate - arrivalCoordinate <= 0) {
			return westboundTrains;
		}
		else {
			return eastboundTrains;
		}
	}
	
	public int getDistance(String departureStn, String arrivalStn) {
		int from = stationCoordinate.get(departureStn);
		int to = stationCoordinate.get(arrivalStn);
		
		return Math.abs(from - to);
	}
	
	public String getDepartureTime(String trainCode, String departureStn) {
		// Get TrainSchedule
		HashMap<String, String> timeTable = trainSchedule.get(trainCode);
		String departureTime = timeTable.get(departureStn);
		
		// Return just time part
		return departureTime.substring(7);
	}
	
	// will return a map with values of Strings of journey
	// [departureStn, departureDate, departureTime, arrivalStn, arrivalDate, arrivalTime]
	public HashMap<String, String> getDetailedJourneyStringMap(Journey journey) {
		HashMap<String, String> journeyStringMap = new HashMap<>();
		
		// Put Station Info and TrainCode
		journeyStringMap.put("departureStn", journey.getDepartureStn());
		journeyStringMap.put("arrivalStn", journey.getArrivalStn());
		journeyStringMap.put("trainCode", journey.getTrainCode());
		
		HashMap<String, String> timeTable = trainSchedule.get(journey.getTrainCode());
		String departureTime = timeTable.get(journey.getDepartureStn());
		String arrivalTime = timeTable.get(journey.getArrivalStn());		
		
		journeyStringMap.put("departureTime", departureTime.substring(7));
		journeyStringMap.put("arrivalTime", arrivalTime.substring(7));
		
		int day = Integer.parseInt(arrivalTime.substring(4, 5)) - 1;
		
		LocalDate departureDate = journey.getDepartureDate();
		LocalDate arrivalDate = departureDate.plusDays(day);
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd EEE");
		String departureDateString = dateFormat.format(departureDate);
		String arrivalDateString = dateFormat.format(arrivalDate);

		journeyStringMap.put("departureDate", departureDateString);
		journeyStringMap.put("arrivalDate", arrivalDateString);
		
		return journeyStringMap;
	}	
}
