import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class Flightuser{
	static Scanner sc=new Scanner(System.in);
	static Ticketbooking ticket=new Ticketbooking();
	static HashMap<String,String> available_flights=new HashMap<>();
	int f_id=0;
	Connection con=Sql.getConnection();
	public boolean search(String date,String src,String dest) {
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from flight_info where src="+"'"+src+"'"+"and dest='"+dest+"' and date='"+date+"' and availability>0");
			while(rs.next()) {
				f_id=rs.getInt("flight_id");
				available_flights.put(rs.getString("flight_name"), rs.getString("arrival_time"));
			}
			if(available_flights.size()>0)return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public void airplanebooking(String username) {
		System.out.println("Enter the date to travel:");
		System.out.println("Represent date in dd/mm/yyyy proper format");
		String date=sc.nextLine();
		System.out.println("Enter the departure airport:");
		String src=sc.nextLine();
		System.out.println("Enter the destination airport:");
		String dest=sc.nextLine();
		if(search(date,src,dest)) {
			System.out.println("Enter 1 to book ticket:");
			System.out.println("Enter any number to exit the process");
			int k=sc.nextInt();
			switch(k) {
			case 1:
				ticket.book(username,f_id,available_flights);
				available_flights.clear();
				break;
			default:
				System.out.println("Cancelling the process");
				System.exit(0);
				break;
			}
		}
		else {
			System.out.println("No flights are available on that date");
		}
	}
	public void view(String username)
	{
		try {
			Statement stmt=con.createStatement();
			String che="";
			ResultSet rs=stmt.executeQuery("select username,e.flight_name,f.flight_id,src,dest,arrival_time,reaching_time,date from user_info e inner join flight_info f on e.flight_id=f.flight_id where username="+"'"+username+"'");
			while(rs.next())
			{
				System.out.println("*****Ticket*****");
				System.out.println("Name:"+rs.getString("username"));
				System.out.println("Flight_name:"+rs.getString("flight_name"));
				System.out.println("Flight id:"+rs.getInt("flight_id"));
				System.out.println("From "+rs.getString("src")+" to "+rs.getString("dest"));
				System.out.println("Departure time:"+rs.getString("arrival_time"));
				System.out.println("Destination time:"+rs.getString("reaching_time"));
				System.out.println("Date:"+rs.getString("Date"));
				System.out.println("*****");
				return;
			}
			rs=stmt.executeQuery("select Booking_status from user_info where username='"+username+"'");
			if(rs.next()) {
			che=rs.getString("Booking_status");
			}
			if(che.equals("cancelled")) System.out.println("Flight is cancelled by administration due to bad weather or some other technical issues");
			else System.out.println("No bookings made by you");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
