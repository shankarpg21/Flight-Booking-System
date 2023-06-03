import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
public class Ticketbooking extends Flightuser{
	static Scanner sc=new Scanner(System.in);
	public void book(String username,int f_id,HashMap<String,String> available_flights) {
		System.out.println("Show the available flight timings on the date:");
		available_flights.forEach((k,v)->System.out.println("Flight_name="+k+" Flight_time="+v));
		System.out.println("Enter the flight_name to book");
		String name=sc.nextLine();
		try {
			Connection con=Sql.getConnection();
			Statement stmt=con.createStatement();
			stmt.executeUpdate("update flight_info set availability=availability-1 where flight_name="+"'"+name+"'");
		    stmt.executeUpdate("update user_info set flight_name="+"'"+name+"'"+" where username="+"'"+username+"'");
		    stmt.executeUpdate("update user_info set flight_id="+f_id+" where username="+"'"+username+"'");
		    stmt.executeUpdate("update user_info set Booking_status='Booked'");
			System.out.println("Booked succesfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
