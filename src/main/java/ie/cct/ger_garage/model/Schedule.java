package ie.cct.ger_garage.model;

public class Schedule {
    private String mechName;
    private Integer mechId;
    private Integer bookingId;

    public Schedule() {
        super();

    }

    public Schedule(String mechName, Integer mechId, Integer bookingId) {
        super();
        this.mechName = mechName;
        this.mechId = mechId;
        this.bookingId = bookingId;
    }

    public String getMechName() {
        return mechName;
    }

    public void setMechName(String mechName) {
        this.mechName = mechName;
    }

    public Integer getMechId() {
        return mechId;
    }

    public void setMechId(Integer mechId) {
        this.mechId = mechId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }


}
