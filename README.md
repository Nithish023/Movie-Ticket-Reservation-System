 Overview of Movie theatre seating Application


Program Inputs:
•	Takes an input file from command line argument
•	File contains a list of:
•	RequestId numberOfSeats
•	Assuming input doesn't change.

Program Output:
•	Outputs a file name which contains following:
•	RequestId [seatsNumbers(comma separated)]

Assumptions I took note of:
These are the following assumptions I have made during developing this application:
•	The system will not reserve seats for a group if the requested number of seats is greater than the available seats. If this is the case people are informed about that seats are full. 
•	Assuming that the group can be either be a family or friends who wish to enjoy the movie by seating together. Therefore I assume the people in family and friends trust among themselves.

Customer Satisfaction:

•	I have followed the "first come first serve" principle to satisfy the customer.
•	My goal was to provide tickets to the  group of people like family or friends in such a way that they are seated together. We can clearly see from the problem statement and examples that people are reserving seats in group. 
•	I have also allotted seats in the order they are received. So the priority will be to allocate seats for the group in a single row. 
•	If the number of people exceeds the row capacity, I have allotted the remaining people in another row. 

Customer Safety:

Based on the below factors are I have divided customer safety into 2 groups:
•	High: Should have a buffer of 3 seats in between two groups and  seats are allotted in alternative rows.
•	Medium: Buffer of 3 seats in maintained between groups of people.
