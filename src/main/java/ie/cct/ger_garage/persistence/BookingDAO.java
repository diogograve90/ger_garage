package ie.cct.ger_garage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Booking;
import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Mechanic;
import ie.cct.ger_garage.model.Vehicle;

public class BookingDAO {

    private final SQLConnection connection;

    public BookingDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Booking b, Customer c) {

        this.connection.openConnection();

        String sqlInsert = "INSERT INTO booking VALUES(null,'booked',?,?,?,?)";
        String sqlInsert2 = "INSERT INTO payment VALUES(null,?,?,?)";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, b.getDate());
            ps.setInt(2, b.getIdMechanic());
            ps.setInt(3, b.getIdVehicle());
            ps.setString(4, b.getTypeBooking());


            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int id = rs.getInt(1);
                b.setIdBooking(id);

            }

            PreparedStatement ps2 = this.connection.getConnection().prepareStatement(sqlInsert2);

            ps2.setInt(1, c.getIdCustomer());
            ps2.setInt(2, b.getIdBooking());
            ps2.setDouble(3, b.getCost());


            ps2.setDouble(3, 200.0);


            ps2.executeUpdate();


        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Booking> readAll() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM booking";

        ArrayList<Booking> bookings = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setStatus(rs.getString("status_booking"));
                b.setIdBooking(rs.getInt("id_booking"));
                b.setDate(rs.getDate("date_booking"));
                b.setTypeBooking(rs.getString("type_booking"));
                b.setIdMechanic(rs.getInt("id_mechanic"));
                b.setIdVehicle(rs.getInt("id_vehicle"));
                bookings.add(b);
            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return bookings;

    }

    public Booking readById(Booking bk) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM booking WHERE id_booking=?";

        Booking b = new Booking();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, bk.getIdBooking());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                b.setIdBooking(rs.getInt("id_booking"));
                b.setDate(rs.getDate("date_booking"));
                b.setTypeBooking(rs.getString("type_booking"));
                b.setIdMechanic(rs.getInt("id_mechanic"));
                b.setIdVehicle(rs.getInt("id_vehicle"));
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return b;

    }

    public void update(Booking b) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE booking SET status_booking=?, date_booking=?, id_mechanic=?, id_vehicle=?, type_booking=? WHERE id_booking=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, b.getStatus());
            ps.setDate(2, b.getDate());
            ps.setInt(3, b.getIdMechanic());
            ps.setInt(4, b.getIdVehicle());
            ps.setString(5, b.getTypeBooking());
            ps.setInt(6, b.getIdBooking());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void updateSchedule(Integer bid, Integer newMid) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE booking SET id_mechanic=? WHERE id_booking=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, newMid);
            ps.setInt(2, bid);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void updateVehicle(Integer bid, Integer newVid) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE booking SET id_vehicle=? WHERE id_booking=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, newVid);
            ps.setInt(2, bid);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Booking b) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM booking WHERE id_booking=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, b.getIdBooking());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void updateDate(Booking b) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE booking SET  date_booking=? WHERE id_booking=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, b.getDate());
            ps.setInt(2, b.getIdBooking());


            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

}
