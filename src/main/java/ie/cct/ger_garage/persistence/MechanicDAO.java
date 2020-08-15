package ie.cct.ger_garage.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Schedule;
import ie.cct.ger_garage.model.Mechanic;

public class MechanicDAO {

    private final SQLConnection connection;

    public MechanicDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Mechanic m) {

        this.connection.openConnection();

        String sqlInsert = "INSERT INTO mechanic VALUES(null,?)";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, m.getMechanicName());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int id = rs.getInt(1);
                m.setIdMechanic(id);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Mechanic> readAll() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM mechanic";

        ArrayList<Mechanic> mechanics = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Mechanic m = new Mechanic();
                m.setIdMechanic(rs.getInt("id_mechanic"));
                m.setMechanicName(rs.getString("mechanic_name"));
                mechanics.add(m);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return mechanics;

    }

    public Mechanic readById(Mechanic me) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM mechanic WHERE id_mechanic=?";

        Mechanic m = new Mechanic();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, me.getIdMechanic());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                m.setIdMechanic(rs.getInt("id_mechanic"));
                m.setMechanicName(rs.getString("mechanic_name"));

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return m;

    }

    public void update(Mechanic m) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE mechanic SET mechanic_name=? WHERE id_mechanic=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, m.getMechanicName());
            ps.setInt(2, m.getIdMechanic());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Mechanic m) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM mechanic WHERE id_mechanic=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, m.getIdMechanic());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Mechanic> availableMechanics(Date d) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM mechanic INNER JOIN booking ON mechanic.id_mechanic = booking.id_mechanic where booking.date_booking = ? GROUP BY mechanic.id_mechanic";

        ArrayList<Mechanic> availableMechanics = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setDate(1, d);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mechanic available = new Mechanic();
                ArrayList<Mechanic> mechanicRegister = new ArrayList<>();

                String sqlSelect2 = "SELECT * FROM mechanic INNER JOIN booking ON mechanic.id_mechanic = booking.id_mechanic where booking.date_booking = ? AND mechanic.id_mechanic= ?";

                PreparedStatement ps2 = this.connection.getConnection()
                        .prepareStatement(sqlSelect2);

                ps2.setDate(1, d);
                ps2.setInt(2, rs.getInt("id_mechanic"));

                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {

                    Mechanic m = new Mechanic();
                    m.setIdMechanic(rs.getInt("id_mechanic"));
                    m.setMechanicName(rs.getString("mechanic_name"));
                    mechanicRegister.add(m);

                }

                if (mechanicRegister.size() <= 4) {

                    available.setIdMechanic(rs.getInt("id_mechanic"));
                    available.setMechanicName(rs.getString("mechanic_name"));


                    availableMechanics.add(available);
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return availableMechanics;

    }

    public ArrayList<Schedule> schedule() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM mechanic INNER JOIN booking ON mechanic.id_mechanic = booking.id_mechanic";

        ArrayList<Schedule> schedules = new ArrayList<>();


        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);


            ResultSet rs = ps.executeQuery();


            while (rs.next()) {


                Schedule s = new Schedule();
                s.setBookingId(rs.getInt("id_booking"));
                s.setMechId(rs.getInt("id_mechanic"));
                s.setMechName(rs.getString("mechanic_Name"));

                schedules.add(s);


            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();
            return schedules;
        }


    }

}
