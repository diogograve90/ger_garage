package ie.cct.ger_garage.model;

public class Vehicle {

    private int idVehicle;


    private int idCustomerFK;

    private String typeVehicle;
    private String make;
    private String license;

    public Vehicle(int idVehicle, int idCustomerFK, String typeVehicle, String make, String license) {
        super();
        this.idVehicle = idVehicle;
        this.idCustomerFK = idCustomerFK;
        this.typeVehicle = typeVehicle;
        this.make = make;
        this.license = license;
    }

    public Vehicle() {
        super();
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public int getIdCustomerFK() {
        return idCustomerFK;
    }

    public void setIdCustomerFK(int idCustomerFK) {
        this.idCustomerFK = idCustomerFK;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "Vehicle [idVehicle=" + idVehicle + ", idCustomerFK=" + idCustomerFK + ", typeVehicle=" + typeVehicle
                + ", make=" + make + ", license=" + license + "]";
    }


}
