package ie.cct.ger_garage.controller;

import ie.cct.ger_garage.model.*;
import ie.cct.ger_garage.persistence.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8100")
public class AdminController {

    MechanicDAO mdao = new MechanicDAO();
    BookingDAO bdao = new BookingDAO();
    AdminDAO adao = new AdminDAO();
    CustomerDAO cdao = new CustomerDAO();
    StockDAO sdao = new StockDAO();
    VehicleDAO vdao = new VehicleDAO();
    PaymentDAO pdao = new PaymentDAO();


    @PostMapping("/register")
    private ResponseEntity createAdmin(@RequestBody Admin a) {
        adao.create(a);
        return ResponseEntity.ok(a);

    }



    @GetMapping("/login")
    @CrossOrigin(origins = "http://localhost:8100")
    private ResponseEntity<Admin> loginAdmin(@RequestParam String login, String pw){
        Admin a = adao.authenticate(login,pw);

        return ResponseEntity.ok(a);
    }
    //Ger must be able to see data for all bookings and staff schedule;

    @GetMapping("/all-bookings")
    private ResponseEntity allBookings() {
        return ResponseEntity.ok(bdao.readAll());
    }


    @GetMapping("/schedule")
    private ResponseEntity staffSchedule() {
        ArrayList<Schedule> s = mdao.schedule();
        return ResponseEntity.ok(s);
    }

    //Ger must be able to manage the staff’s assigned vehicle and workload;

    @PutMapping("edit-booking-schedule")
    private void editSchedule(@RequestParam Integer bid, Integer newMid) {

        bdao.updateSchedule(bid, newMid);


    }


    @PutMapping("edit-booking-vehicle")
    private void editBookingVehicle(@RequestParam Integer bid, Integer newVid) {

        bdao.updateVehicle(bid, newVid);


    }

    //Ger must be able to edit the financial details (cost, discount, etc) of each booking;
    //Once a service is finished, Ger must be able to see/generate a bill/invoice with details of the service;
    //Ger must be able to generate a bill/invoice for the products a customer bought;

    @GetMapping("/payments")
    private ArrayList<Payment> payments() {

        return pdao.readAll();

    }


    @PutMapping("/edit-payments")
    private void editPayments(@RequestBody Payment p) {

        pdao.update(p);


    }


    @PutMapping("/edit-booking")
    private void editBooking(@RequestBody Booking b){
        bdao.update(b);
    }


    @PutMapping("edit-booking-payment")
    private void editBookingPayment(@RequestBody Payment p) {

        pdao.update(p);
    }

    //Ger must be able to see and manage the shop’s stock;

    @GetMapping("/stock")
    private ResponseEntity stock() {
        return ResponseEntity.ok(sdao.readAll());
    }

    @PutMapping("/edit-stock")
    private void editStock(@RequestBody Stock s) {
        sdao.update(s);
    }



    @GetMapping("/all-customers")
    private ResponseEntity allCustomers() {
        return ResponseEntity.ok(cdao.readAll());
    }


    @GetMapping("/all-mechanics")
    private ResponseEntity allMechanics() {
        return ResponseEntity.ok(mdao.readAll());
    }



    @GetMapping("/all-vehicles")
    private ResponseEntity allVehicles() {
        return ResponseEntity.ok(vdao.readAll());
    }

    @DeleteMapping("/delete-booking")
    private void deleteBooking(@RequestParam Integer idBooking){

        Booking b = new Booking();
        b.setIdBooking(idBooking);
        bdao.deleteById(b);

    }
    @DeleteMapping("/delete-stock")
    private void deleteStock(@RequestParam Integer idStock){
       sdao.deleteById(idStock);

    }


    @DeleteMapping("/delete-mechanic")
    private void deleteMechanic(@RequestParam Integer idMechanic){

        Mechanic m = new Mechanic();
        m.setIdMechanic(idMechanic);
        mdao.deleteById(m);

    }


    @PostMapping("/create-mechanic")
    private void createMechanic(@RequestBody Mechanic m ){

        mdao.create(m);

    }
    @PostMapping("/create-stock")
    private void createMechanic(@RequestBody Stock s ){

        sdao.create(s);

    }
}
