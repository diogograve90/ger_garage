package ie.cct.ger_garage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Vehicle;

public class VehicleDAO {

    private final SQLConnection connection;

    public VehicleDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Vehicle v) {


        this.connection.openConnection();


        String sqlInsert = "INSERT INTO vehicle VALUES(null,?,?,?,?)";


        try {


            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1, v.getIdCustomerFK());
            ps.setString(2, v.getTypeVehicle());
            ps.setString(3, v.getMake());
            ps.setString(4, v.getLicense());


            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {


                int id = rs.getInt(1);
                v.setIdVehicle(id);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Vehicle> readAll() {

        this.connection.openConnection();


        String sqlSelect = "SELECT * FROM vehicle";


        ArrayList<Vehicle> vehicles = new ArrayList<>();

        try {


            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);


            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Vehicle v = new Vehicle();


                v.setIdVehicle(rs.getInt("id_vehicle"));
                v.setIdCustomerFK(rs.getInt("id_customer"));
                v.setTypeVehicle(rs.getString("type_vehicle"));
                v.setMake(rs.getString("make"));
                v.setLicense(rs.getString("license"));
                vehicles.add(v);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }


        return vehicles;

    }

    public Vehicle readById(Vehicle vh) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM vehicle WHERE id_vehicle=?";

        Vehicle v = new Vehicle();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, vh.getIdVehicle());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                v.setIdVehicle(rs.getInt("id_vehicle"));
                v.setIdCustomerFK(rs.getInt("id_customer"));
                v.setTypeVehicle(rs.getString("type_vehicle"));
                v.setMake(rs.getString("make"));
                v.setLicense(rs.getString("license"));


            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return v;

    }

    public void update(Vehicle v) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE vehicle SET id_customer=?, type_vehicle=?, make=?, license=? WHERE id_vehicle=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, v.getIdCustomerFK());
            ps.setString(2, v.getTypeVehicle());
            ps.setString(3, v.getMake());
            ps.setString(4, v.getLicense());
            ps.setInt(5, v.getIdVehicle());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Vehicle v) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM vehicle WHERE id_vehicle=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, v.getIdVehicle());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Vehicle> readVehicleByCustomer(Customer c) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM vehicle WHERE id_customer=?";


        ArrayList<Vehicle> vehicles = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, c.getIdCustomer());


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Vehicle v = new Vehicle();

                v.setIdVehicle(rs.getInt("id_vehicle"));
                v.setIdCustomerFK(rs.getInt("id_customer"));
                v.setTypeVehicle(rs.getString("type_vehicle"));
                v.setMake(rs.getString("make"));
                v.setLicense(rs.getString("license"));

                vehicles.add(v);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return vehicles;

    }

}
