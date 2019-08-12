import java.util.*;
import java.sql.*;

class Queries{
	Statement stmt;
	Connection con;
	String query;
	public Queries(){
		try{  
			Class.forName("com.mysql.jdbc.Driver"); 
			con=DriverManager.getConnection(  "jdbc:mysql://localhost:3306/blood_bank","root","");
			System.out.println("Database Connected");
			//here blood_donation is database name, root is username and password is blank
			stmt=con.createStatement();  
		}
		catch(Exception e){ 
				System.out.println(e);
		} 
	}
	boolean auth(){
		Scanner sc = new Scanner(System.in);
		System.out.print("Email : ");
		String email = sc.next();
		System.out.print("Password : ");
		String pass = sc.next();
		query = "SELECT pass from users WHERE email='"+email+"'";
		try {
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()) {
			if(pass.equals(rs.getString(1))) {
				System.out.println("Login Successfull");
				return true;
				}
			}
		}
		catch(Exception e) {
			System.out.println("Error in authentication");
		}
		return false;
	}
	boolean register() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Email : ");
		String email = sc.next();
		System.out.print("Password : ");
		String pass = sc.next();
		query = "INSERT INTO users VALUES ('"+email+"','"+pass+"')";
		try {
			stmt.executeUpdate(query);
			System.out.println("Registration Successfull");
			return true;
		}
		catch(Exception e){
			System.out.println("Registration Failed due to some error");
			return false;
		}
	}
	void donate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Details : ");
		System.out.print("Name : ");
		String name = sc.nextLine();
		System.out.print("Age : ");
		int age = sc.nextInt();
		System.out.print("Blood Type : ");
		String btype = sc.next();
		System.out.print("Gender(M/F) : ");
		char gender = sc.next().charAt(0);
		System.out.print("Date(yyyy-mm-dd) : ");
		String date = sc.next();
		System.out.print("Phone Number : ");
		String phone = sc.next();
		System.out.print("City : ");
		sc.nextLine();
		String address = sc.nextLine();
		query = "INSERT INTO details VALUES ('"+name+"',"+age+",'"+btype+"','"+gender+"','"+date+"',"+phone+",'"+address+"')";
		try {
		stmt.executeUpdate(query);
		System.out.println("Details Inserted into DB");
		}
		catch(Exception e) {
			System.out.println("Error while running the Insert Query");
		}
	}
	void find() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Blood Type : ");
		String btype = sc.next();
		query = "SELECT * FROM details WHERE blood_type='"+btype+"'";
		try {
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()) {
		do{
			System.out.println(rs.getString(1)+"\t\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getDate(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
			}while(rs.next());
		}
		else
			System.out.println("Blood Group Not Available");
		}
		catch(Exception e) {
			System.out.println("Error while finding Blood Group");
			System.out.println(e.getMessage());
		}
	}
}
public class Blood_Donation {
	static void call(Queries obj1) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Choose an option : ");
		System.out.println("1.Donate Blood\n2.Need Blood\n3.Exit");
		int choice = sc.nextInt();
		if(choice == 1)
			obj1.donate();
		else if(choice == 2)
			obj1.find();
		else {
			sc.close();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Queries obj = new Queries();
		boolean login = false;
		System.out.println("Welcome to the Blood Bank.");
		while(login == false) {
		System.out.println("1.Login\n2.Signup\n3.Exit");
		int choice = sc.nextInt();
		if(choice == 1) {
			login = obj.auth();
		}
		else if(choice == 2){
			if(obj.register()) {
				System.out.println("---Login---");
				login = obj.auth();
			}
		}
		else {
			System.exit(0);
		}
		if(login == false) {
			System.out.println("Login Failed! Either Email or Password is Incorrect");
			}
		}
		while(true) {
			call(obj);
		}
	}
}
