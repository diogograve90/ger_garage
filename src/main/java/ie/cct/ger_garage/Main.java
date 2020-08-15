package ie.cct.ger_garage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import ie.cct.ger_garage.model.Admin;
import ie.cct.ger_garage.model.Booking;
import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Mechanic;
import ie.cct.ger_garage.model.Vehicle;
import ie.cct.ger_garage.persistence.AdminDAO;
import ie.cct.ger_garage.persistence.BookingDAO;
import ie.cct.ger_garage.persistence.CustomerDAO;
import ie.cct.ger_garage.persistence.MechanicDAO;
import ie.cct.ger_garage.persistence.VehicleDAO;

public class Main {

	
	// I CREATED IT AS A DRAFT FOR THE ENDPOINTS
	public static void main(String[] args) {

		Scanner k = new Scanner(System.in);

		System.out.println("Welcome to Ger's Garage!");
		System.out.println("Press 1 to sign up a customer");
		System.out.println("Press 2 to logon as a customer");
		System.out.println("Press 3 to sign up an admin");
		System.out.println("Press 4 to logon as an admin");

		int ans = k.nextInt();

		switch (ans) {

		case (1):

			createCustomer(k);

		case (2):

			logonCustomer(k);

			break;
		case (3):
			createAdmin(k);

		case (4):
			logonAdmin(k);
			break;

		}

		k.close();
	}

	public static void createCustomer(Scanner k) {

		String ans = null;

		int i = 0;

		// this is a loop that binds us into the customer creation
		// at the end of each customer creation, I will be asked if I want to create
		// another one
		// if I do, the escape variable stays the same
		// if I don't, the variable iterates and I get out of the loop
		while (i == 0) {

			Customer c = new Customer();

			System.out.println("What's the name of the customer?");
			c.setCustomerName(k.next());

			System.out.println("What's the customer's phone number?");
			c.setPhone(k.next());

			System.out.println("What's the customer's login?");
			c.setCustomerLogin(k.next());

			System.out.println("What's the customer's password");
			c.setCustomerPassword(k.next());

			// creating a DATA ACCESS object
			CustomerDAO cdao = new CustomerDAO();
			// using the CREATE method (from Create Read Update Delete)
			cdao.create(c);

			System.out.println("Customer created! would you like to create a vehicle? y/n");
			ans = k.next();

			if (ans.contentEquals("y")) {

				int j = 0;

				do {

					Vehicle v = new Vehicle();
					v.setIdCustomerFK(c.getIdCustomer());
					System.out.println("What's the type of the vehicle? (e.g: bus, car, bike)");
					v.setTypeVehicle(k.next());
					System.out.println("What's the make of the vehicle? (e.g: ford, chevrolet, volkswagen)");
					v.setMake(k.next());
					System.out.println("What's the vehicle's license plate?");
					v.setLicense(k.next());

					// adding the vehicle to the customer object
					c.addVehicle(v);

					VehicleDAO vdao = new VehicleDAO();
					vdao.create(v);

					System.out.println("Vehicle created! would you like to create another vehicle? y/n)");
					ans = k.next();

					if (!ans.contentEquals("y")) {
						j++;
					}

				} while (j == 0);
			}

			System.out.println("Keep creating customers? y/n");
			ans = k.next();

			if (!ans.contentEquals("y")) {
				i++;
			}

		}

	}

	public static void logonCustomer(Scanner k) {

		System.out.println("Enter your login");
		String login = k.next();

		System.out.println("Enter your password");
		String password = k.next();

		CustomerDAO cdao = new CustomerDAO();

		// Customer c = cdao.authenticate(login, password);

		// the authenticate() method from CustomerDAO is supposed to return a filled
		// Customer from the database
		// if no customers are found in the database, it still returns a customer, but
		// an empty one
		// this loop checks whether or not the customer is filled, and if it is, the
		// login is successful, else it wasn't
//		if (!c.equals(null)) {

		System.out.println("Logon failed. Try a different login or password");
	}

	public static void customerMenu(Scanner k, Customer c, CustomerDAO cdao) {

		System.out.println("Press 1 to alter your user data");
		System.out.println("Press 2 to see available bookings");
		System.out.println("Press 3 to see the status of your bookings");
		int ans = k.nextInt();

		switch (ans) {

		case (1):
			alterCustomer(k, c, cdao);
			break;
		case (2):
			newBooking(k, c);
			break;
		case (3):
			checkBookingStatus(c, cdao);
		}
	}

	public static void alterCustomer(Scanner k, Customer c, CustomerDAO cdao) {
		// Just a simple "redoing" of the customer creation, just this time I do not use
		// create() from CustomerDAO, I use update()

		System.out.println("Do you want to alter your account details? y/n");
		String ans = k.next();

		if (ans.contentEquals("y")) {
			System.out.println("Enter your name");
			c.setCustomerName(k.next());

			System.out.println("Enter your phone");
			c.setPhone(k.next());

			System.out.println("Enter your login");
			c.setCustomerLogin(k.next());

			System.out.println("Enter your password");
			c.setCustomerPassword(k.next());

			cdao.update(c);

			System.out.println("Done!");
		}

	}

	public static void newBooking(Scanner k, Customer c) {


		// this variable is used to store the day of the week
		// Calendar sees days of the week as numbers from 1 to 7
		int dayOfWeek = 0;

		// instancing a calendar, it's a bit different from a normal object (Object foo
		// = new Object()), because it is an interface, so I use getInstance()
		// to cheat the system and instance it

		Calendar cal = Calendar.getInstance();

		// this date represents the availability, it is a java.sql.Date format (this is
		// the standard format I am importing, any dates identified as just "Date"
		// will be so too)

		Date availability = new Date(new java.util.Date().getTime());
		BookingDAO bdao = new BookingDAO();

		// important variables for a loop, keep these variables
		// in mind
		int countBookings = 0;
		int countDays = 0;

		// just so I can save the booking to the database, as it needs a
		// mechanic and I haven't implemented the mechanic part of the system
		MechanicDAO mdao = new MechanicDAO();
		Mechanic m = new Mechanic();
		m.setMechanicName("ger");
		mdao.create(m);

		// I set our calendar instance to the current time
		cal.setTime(availability);

		// I get the current day of the week from the calendar and save it to a
		// variable
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		// This checks if the day of the week is not Sunday
		// For instance, if it's Monday the dayOfWeek variable will be set to 2
		// this check will be then, brutely speaking, if 2 is not 1
		if (dayOfWeek != Calendar.SUNDAY) {

			// if it's not Sunday, I then go through the list of bookings
			// this loop will be used to count the bookings, divide them and determine the
			// next available day
			for (Booking b : bdao.readAll()) {

				// this will count the bookings on the list, as I go 1 by 1 through the
				// bookings of the database, it will also be added 1 by 1
				countBookings++;

				// once it hits a number divisible by 4, it will then add a day to the bookings
				// on the list
				if (countBookings % 4 == 0) {

					// then, as days are added, they are registered on this variable, which will be
					// used to calculate how many days from
					// the current date there will be a booking available
					countDays++;

					// it then adds a day to the booking,
					// the formula for that long number that SQL interprets as a single day is
					// 24*60*60*1000
					// so, I just need to multiply that by number of days counted and add it to the
					// days on the booking object
					b.setDate(new Date(b.getDate().getTime() + countDays * 24 * 60 * 60 * 1000));

					// I set the time and date of our calendar as the day that was registered on
					// the booking object
					cal.setTime(b.getDate());

					// I register the day of the week the booking object is currently in
					dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

					// if it hits Sunday, I add a single day, just to skip it to Monday
					if (dayOfWeek == Calendar.SUNDAY) {
						b.setDate(new Date(b.getDate().getTime() + 1 * 24 * 60 * 60 * 1000));
					}

				}

				// I altered the availability according to the date set on the booking object
				// which is already processed to have the correct number of days added
				availability = b.getDate();
			}
		} else {

			// if it IS Sunday, I just add a day, again, to skip the availability to Monday
			availability.setTime(availability.getTime() + 1 * 24 * 60 * 60 * 1000);
		}

		// this is another loop, used to update the dates on the database according to
		// our previous calculations
		for (Booking b2 : bdao.readAll()) {
			bdao.updateDate(b2);
		}

		System.out.println("The next available booking is on: " + availability.toString());
		System.out.println("Create a booking? y/n");

		String ans = k.next();

		if (ans.contentEquals("y")) {

			// Just creating a booking
			Booking b = new Booking();

			// the default status of the booking is BOOKED
			b.setStatus("BOOKED");

			// I set the availability as the earliest date available
			b.setDate(availability);

			// setting the mechanic
			b.setIdMechanic(m.getIdMechanic());

			System.out.println("Which vehicle would you like booked? ");

			// this variable will be used to
			int vcount = 0;
			VehicleDAO vdao = new VehicleDAO();

			for (Vehicle v : vdao.readVehicleByCustomer(c)) {
				vcount++;
				System.out.println(vcount + ". - " + "make = " + v.getMake() + ", license = " + v.getLicense());
			}

			int vehicle = k.nextInt();

			b.setIdVehicle(vdao.readVehicleByCustomer(c).get(vehicle - 1).getIdVehicle());

			System.out.println("Which type of booking do you want?");
			System.out.println("1. Annual Service");
			System.out.println("2. Major Service");
			System.out.println("3. Repair / Fault");
			System.out.println("4. Major Repair");

			int booking = k.nextInt();
			String type = null;
			switch (booking) {

			case (1):
				type = "Annual Service";
				break;
			case (2):
				type = "Major Service";
				break;
			case (3):
				type = "Repair / Fault";
				break;
			case (4):
				type = "Major Repair";
				break;
			}

			b.setTypeBooking(type);

			// bdao.create(b, m, vdao.readVehicleByCustomer(c).get(vehicle - 1));

		}

	}

	public static void checkBookingStatus(Customer c, CustomerDAO cdao) {

		ArrayList<Booking> statuses = cdao.returnStatusBooking(c);
		int i = 0;

		for (Booking b : statuses) {
			i++;
			System.out.println(i + ". " + b.getDate().toString() + " = " + b.getStatus());
		}

	}

	public static void createAdmin(Scanner k) {

		Admin a = new Admin();
		AdminDAO adao = new AdminDAO();
		System.out.println("What's the login");
		a.setAdminLogin(k.next());
		System.out.println("What's the password");
		a.setAdminPw(k.next());

		adao.create(a);
		System.out.println("Admin created!");

	}

	public static void logonAdmin(Scanner k) {

		System.out.println("Enter your login");
		String login = k.next();

		System.out.println("Enter your password");
		String password = k.next();

		AdminDAO adao = new AdminDAO();

		// Admin a = adao.authenticate(login, password);

		// if (!a.equals(null)) {
		System.out.println("Logon successful!");

		// adminMenu(k, a, adao);

		// } else {
		// System.out.println("Logon failed. Try a different login or password");
		// }

		// }

		// public static void adminMenu(Scanner k, Admin a, AdminDAO adao) {

		System.out.println("Press 1 to see all bookings");
		System.out.println("Press 2 to edit bookings");
		System.out.println("Press 3 to see the stock");
		System.out.println("Press 4 to edit the stock");
		System.out.println("Press 5 to create a mechanic");
		System.out.println("Pres 6 to edit mechanics");
		int ans = k.nextInt();

		switch (ans) {

		case (1):
			BookingDAO bdao = new BookingDAO();
			for (Booking b : bdao.readAll()) {
				System.out.println(b.toString());
			}

			break;
		case (2):

			break;
		case (3):

		}

	}

}
