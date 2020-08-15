package ie.cct.ger_garage.model;

import java.sql.Date;
import java.time.LocalDate;

public class Booking {

    private int idBooking;
    private String status;
    private Date date;
    private LocalDate localDate;
    private String typeBooking;
    private Double cost;

    private int idMechanic;
    private int idVehicle;


    public Booking(int idBooking, String status, Date date, String typeBooking, int idMechanic, int idVehicle) {
        super();
        this.idBooking = idBooking;
        this.status = status;
        this.date = date;
        this.typeBooking = typeBooking;
        this.idMechanic = idMechanic;
        this.idVehicle = idVehicle;
        this.cost = 200.0;
    }

    public Booking() {
        super();
        this.cost = 200.0;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeBooking() {
        return typeBooking;
    }

    public void setTypeBooking(String typeBooking) {
        this.typeBooking = typeBooking;
    }

    public int getIdMechanic() {
        return idMechanic;
    }

    public void setIdMechanic(int idMechanic) {
        this.idMechanic = idMechanic;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date date) {
        this.localDate = date.toLocalDate();
    }

    @Override
    public String toString() {
        return "Booking [idBooking=" + idBooking + ", status=" + status + ", date=" + date + ", typeBooking="
                + typeBooking + ", idMechanic=" + idMechanic + ", idVehicle=" + idVehicle + "]";
    }


}
