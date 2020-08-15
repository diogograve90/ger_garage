package ie.cct.ger_garage.model;

import java.util.ArrayList;

public class Customer {

    private int idCustomer;
    private String customerName;
    private String phone;
    private String customerLogin;
    private String customerPassword;
    private final ArrayList<Vehicle> vehicles;

    public Customer(int idCustomer, String customerName, String phone, String customerLogin, String customerPassword) {
        super();
        this.idCustomer = idCustomer;
        this.customerName = customerName;
        this.phone = phone;
        this.customerLogin = customerLogin;
        this.customerPassword = customerPassword;
        this.vehicles = new ArrayList<>();
    }

    public Customer() {
        super();
        this.vehicles = new ArrayList<>();
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerLogin() {
        return customerLogin;

    }

    public void setCustomerLogin(String customerLogin) {
        this.customerLogin = customerLogin;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle v) {
        this.vehicles.add(v);
    }

    @Override
    public String toString() {
        return "Customer [idCustomer=" + idCustomer + ", customerName=" + customerName + ", phone=" + phone
                + ", customerLogin=" + customerLogin + ", customerPassword=" + customerPassword + "]";
    }


}
