package ie.cct.ger_garage.model;

public class Payment {
    private int idPmt;
    private int idCustomer;
    private int idBooking;
    private double cost;

    public Payment() {
    }

    public Payment(int idPmt, int idCustomer, int idBooking, double cost) {
        this.idPmt = idPmt;
        this.idCustomer = idCustomer;
        this.idBooking = idBooking;
        this.cost = cost;
    }

    public int getIdPmt() {
        return idPmt;
    }

    public void setIdPmt(int idPmt) {
        this.idPmt = idPmt;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
