package com.walmart.services;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.walmart.models.Request;
import com.walmart.models.Response;
import com.walmart.models.SafetyRiskFactor;
import com.walmart.models.Seat;

public class MovieTheater {
	
	private Seat[][] seats;
	private SafetyRiskFactor riskFactor;
	private int buffer;
	private Stack<PriorityQueue<Seat>> theatreSeats = new Stack<PriorityQueue<Seat>>();
	private int totalSeatsLeft = 0;


	public MovieTheater(int rows, int columns, SafetyRiskFactor riskFactor, int buffer) {
		super();
		this.buffer = buffer;
		// creating rows upto J
		char rowSymbol =  'A';
		this.seats = new Seat[rows][columns];
		this.riskFactor = riskFactor;
		
		for(int j = 0; j < rows; j++) {
			// Creating priority queue for each row
			PriorityQueue<Seat> pque = new PriorityQueue<>();
			for(int i = 0; i < columns; i++){
				this.seats[j][i] = new Seat();
				// setting seatId
				this.seats[j][i].setSeatId(rowSymbol + String.valueOf(i+1));
				// assigning priority to each seat
	            this.seats[j][i].setPriority(columns - i);
	            // Adding to the priority queue
	            pque.add(this.seats[j][i]);
	            this.seats[j][i].setIsBlocked(false);
	            
	        }
			
			if(this.riskFactor.equals(SafetyRiskFactor.HIGH)) {
				if(rows%2 == 0 && j%2 != 0 || rows%2 != 0 && j%2 == 0) {
					this.theatreSeats.push(pque);
					this.totalSeatsLeft+=pque.size();
				}
			}else {
				this.theatreSeats.push(pque);
				this.totalSeatsLeft+=pque.size();
			}
			
			rowSymbol++;
		}

	}


	public Seat[][] getSeats() {
		return seats;
	}


	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}


	public SafetyRiskFactor getRiskFactor() {
		return riskFactor;
	}


	public void setRiskFactor(SafetyRiskFactor riskFactor) {
		this.riskFactor = riskFactor;
	}


	public Response bookSeat(Request request) {
	
		Response response = new Response(request.getRequestid(), null, null);
		
		// return if there are no available seats
		if(totalSeatsLeft < request.getNumberOfSeats()) {
			response.setStatus("FULL");
			return response;
		}
		int i = request.getNumberOfSeats();
		
		List<String> seatIds = new ArrayList<>();
//		
		PriorityQueue<Seat> topRow = theatreSeats.pop();
//	
		while(i != 0) {
			if(topRow.size() == 0 && theatreSeats.size() != 0) {
//				O(1)
				topRow = theatreSeats.pop();
			}
//			
			seatIds.add(topRow.poll().getSeatId());
			i--;
			this.totalSeatsLeft--;
		}
		
		response.setSeatId(seatIds);
		response.setStatus("SUCCESS");
		
		if(request.getNumberOfSeats() == 0) {
			theatreSeats.push(topRow);
			return response;
		}
		
		// blocking seats
		int block = this.buffer;
		while(topRow.size() > 0 && block != 0) {
			topRow.poll();
			block--;
			
			
		}
		if(topRow.size() > 0) {
			theatreSeats.push(topRow);
		}
		
		return response;
	}
	
	
	
	
	

}
