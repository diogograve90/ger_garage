package ie.cct.ger_garage.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ie.cct.ger_garage.model.Booking;
import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Mechanic;
import ie.cct.ger_garage.model.Stock;
import ie.cct.ger_garage.model.Vehicle;
import ie.cct.ger_garage.persistence.BookingDAO;
import ie.cct.ger_garage.persistence.CustomerDAO;
import ie.cct.ger_garage.persistence.MechanicDAO;
import ie.cct.ger_garage.persistence.StockDAO;
import ie.cct.ger_garage.persistence.VehicleDAO;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    CustomerDAO cdao = new CustomerDAO();
    BookingDAO bdao = new BookingDAO();
    VehicleDAO vdao = new VehicleDAO();
    StockDAO sdao = new StockDAO();
    MechanicDAO mdao = new MechanicDAO();


    //A customer must be able to create an account;
    //TESTED
    @PostMapping("/register")
    private void signUp(@RequestBody Customer c) {
        cdao.create(c);


    }

    //TESTED
    @CrossOrigin
    @GetMapping("/login")
    private ResponseEntity<Customer> signIn(@RequestParam String login, String password) {

        Customer c = cdao.authenticate(login, password);

        return ResponseEntity.ok(c);


    }

    @PostMapping("/vehicle-add")
    private void vehicleAdd(@RequestBody Vehicle v){

        vdao.create(v);


    }


    //A customer must be able to check and alter their account’s data at any given time;
    //TESTED
    @GetMapping("/your-account")
    private ResponseEntity yourAccount(@RequestParam Integer id) {

        Customer c = new Customer();
        c.setIdCustomer(id);

        c = cdao.readById(c);


        return ResponseEntity.ok(c)
                ;
    }

    //TESTED
    @PutMapping("/alter-account")
    private ResponseEntity alterAccount(@RequestBody Customer c) {


        cdao.update(c);

        return ResponseEntity.ok(c);
    }

    //A customer must be able to access a homepage
    //and check the availability of booking with detailed list of mechanics and service;
    //TESTED
    //RETEST AFTER ADMIN
    @GetMapping("/date-available")
    public static Date dateAvailable() {


        int dayOfWeek = 0;


        Calendar cal = Calendar.getInstance();

        Date availability = new Date(new java.util.Date().getTime());
        BookingDAO bdao = new BookingDAO();


        int countBookings = 0;
        int countDays = 0;


        cal.setTime(availability);


        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        if (dayOfWeek != Calendar.SUNDAY) {


            for (Booking b : bdao.readAll()) {

                countBookings++;


                if (countBookings % 4 == 0) {


                    countDays++;

                    b.setDate(new Date(b.getDate().getTime() + countDays * 24 * 60 * 60 * 1000));


                    cal.setTime(b.getDate());


                    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


                    if (dayOfWeek == Calendar.SUNDAY) {
                        b.setDate(new Date(b.getDate().getTime() + 1 * 24 * 60 * 60 * 1000));
                    }

                }


                availability = b.getDate();
            }
        } else {


            availability.setTime(availability.getTime() + 1 * 24 * 60 * 60 * 1000);
        }


        for (Booking b2 : bdao.readAll()) {
            bdao.updateDate(b2);
        }


        return availability;

    }

    //TESTED
    //RETEST AFTER ADMIN
    @GetMapping("/mechanic-available")
    private ResponseEntity mechanicAvailable() {


        Date available = dateAvailable();

        ArrayList<Mechanic> availableMechanics = mdao.availableMechanics(available);


        return ResponseEntity.ok(availableMechanics);


    }

    //A customer must be able to access a shopping section with a detailed list of products;
    //TESTED
    //RETEST AFTER ADMIN
    @GetMapping("/store-list")
    private ResponseEntity checkProducts() {

        ArrayList<Stock> stock = sdao.readAll();

        return ResponseEntity.ok(stock);
    }

    //A customer must be able to buy products in the shop;
    //TEST AFTER ADMIN
    @PutMapping("/store-buy")
    private void buyProduct(@RequestBody Stock[] prods,@RequestParam int custId) {


        sdao.shoppingCartPayment(prods, custId);


    }

    //A customer must be able to book in an appointment for their vehicle
    //with four different type of service available;
    //TEST AFTER ADMIN
    @PostMapping("/booking-new")
    private void createBooking(@RequestBody Booking b, @RequestParam Integer idCustomer) {


        Customer c = new Customer();
        c.setIdCustomer(idCustomer);




        bdao.create(b,c);





    }

    //A customer must be able to see the status of their bookings;
    //TEST AFTER ADMIN
    @PostMapping("/booking-list")
    private ResponseEntity customerBookings(@RequestBody Customer c) {
        return ResponseEntity.ok(cdao.returnStatusBooking(c));
    }

    @PostMapping("/vehicle-list")
    private ResponseEntity customerVehicles(@RequestBody Customer c){

        ArrayList<Vehicle> vehicles = vdao.readVehicleByCustomer(c);
        return ResponseEntity.ok(vehicles);

    }
}
	
	
	
	
	

