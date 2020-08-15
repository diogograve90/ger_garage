package ie.cct.ger_garage.model;

public class Mechanic {

    private int idMechanic;
    private String mechanicName;

    public Mechanic(int idMechanic, String mechanicName) {
        super();
        this.idMechanic = idMechanic;
        this.mechanicName = mechanicName;
    }

    public Mechanic() {
        super();
    }

    public int getIdMechanic() {
        return idMechanic;
    }

    public void setIdMechanic(int idMechanic) {
        this.idMechanic = idMechanic;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    @Override
    public String toString() {
        return "Mechanic [idMechanic=" + idMechanic + ", mechanicName=" + mechanicName + "]";
    }

}
