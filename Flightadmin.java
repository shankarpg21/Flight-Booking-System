import java.sql.Connection;
import java.sql.*;
import java.util.*;
public class Flightadmin{
	Scanner sc=new Scanner(System.in);
	Connection con=Sql.getConnection();
	public void addflights()
	{
		System.out.println("Enter the flight id");
		int f_id=sc.nextInt();
		String t=sc.nextLine();
		System.out.println("Enter the flight name");
		String flight_name=sc.nextLine();
		System.out.println("Enter the departure station");
		String dep_stationname=sc.nextLine();
		System.out.println("Enter the destination station");
		String dest_name=sc.nextLine();
		System.out.println("Enter the flight take off time");
		System.out.println("Represent time in 24-hrs format");
		String time1=sc.nextLine();
		System.out.println("Enter the date");
		System.out.println("Represent date in dd/mm/yyyy proper format");
		String date=sc.nextLine();
		System.out.println("Enter the flight destination time");
		System.out.println("Represent time in 24-hrs format");
		String time2=sc.nextLine();
		String str="insert into flight_info values("+f_id+","+"'"+flight_name+"'"+","+"'"+dep_stationname+"'"+","+"'"+dest_name+"'"+","+"'"+time1+"'"+","+"'"+date+"'"+",60,"+"'"+time2+"')";
		try{
			Statement stmt=con.createStatement();
			stmt.executeUpdate(str);
			System.out.println("Flight added succesfully");
			}
	    catch(Exception e) {
		  System.out.println(e);
	    }
	}
	public void removeflights() {
		System.out.println("Enter the flight id to delete:");
		int id=sc.nextInt();
		try{
			Statement stmt=con.createStatement();
			stmt.executeUpdate("delete from flight_info where flight_id="+id);
			stmt.executeUpdate("update user_info set Booking_status='cancelled' where flight_id="+id);
			System.out.println("Flight removed successfully");
			}
	    catch(Exception e) {
		  System.out.println(e);
	    }
	}
	public boolean check(int id,String time) {
		try{
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select flight_id,arrival_time from flight_info where flight_id="+id+" and arrival_time='"+time+"'");
			while(rs.next()) {
				if(id==rs.getInt("flight_id") && time.equals(rs.getString("arrival_time"))) {
					return true;
				}
			}
			}
	    catch(Exception e) {
		  System.out.println(e);
	    }
		return false;
	}
	public void bookings() {
		System.out.println("Enter the flight id:");
		int flight_id=sc.nextInt();
		String k=sc.nextLine();
		System.out.println("Enter the flight time");
		System.out.println("Represent time in 24 hrs proper format");
		String time=sc.nextLine();
		if(check(flight_id,time)) {
			System.out.println("The bookings of the particular flight are:");
			try{
				Statement stmt=con.createStatement();
				int fl=0;
				ResultSet rs=stmt.executeQuery("select username,arrival_time from user_info u,flight_info f where f.flight_name=u.flight_name and f.arrival_time='"+time+"'");
				while(rs.next()) {
					fl=1;
					System.out.println(rs.getString("username")+" "+rs.getString("arrival_time"));
				}
				if(fl==0) {
					System.out.println("No bookings on this particular flight");
				}
				
				}
		    catch(Exception e) {
			  System.out.println(e);
		    }
		}
		else {
			System.out.println("No flights availabale on that time");
		}
	}
}
