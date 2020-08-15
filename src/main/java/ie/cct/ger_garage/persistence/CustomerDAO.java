package ie.cct.ger_garage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Booking;
import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Vehicle;

public class CustomerDAO {


    private final SQLConnection connection;

    public CustomerDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Customer c) {

        this.connection.openConnection();


        String sqlInsert = "INSERT INTO customer VALUES(null,?,?,?,?)";

        try {


            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);


            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getCustomerLogin());
            ps.setString(4, c.getCustomerPassword());


            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();


            if (rs.next()) {

                int id = rs.getInt(1);
                c.setIdCustomer(id);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Customer> readAll() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM customer";

        ArrayList<Customer> customers = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setIdCustomer(rs.getInt("id_customer"));
                c.setCustomerName(rs.getString("customer_name"));
                c.setPhone(rs.getString("phone"));
                c.setCustomerLogin(rs.getString("customer_login"));
                c.setCustomerPassword(rs.getString("customer_pw"));
                customers.add(c);
            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return customers;

    }

    public Customer readById(Customer cs) {


        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM customer WHERE id_customer=?";

        Customer c = new Customer();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);


            ps.setInt(1, cs.getIdCustomer());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                c.setIdCustomer(rs.getInt("id_customer"));
                c.setCustomerName(rs.getString("customer_name"));
                c.setPhone(rs.getString("phone"));
                c.setCustomerLogin(rs.getString("customer_login"));
                c.setCustomerPassword(rs.getString("customer_pw"));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return c;

    }

    public void update(Customer c) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE customer SET customer_name=?, phone=?, customer_login=?, customer_pw=? WHERE id_customer=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getCustomerLogin());
            ps.setString(4, c.getCustomerPassword());
            ps.setInt(5, c.getIdCustomer());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Customer c) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM customer WHERE id_customer=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, c.getIdCustomer());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public Customer authenticate(String login, String password) {

        Customer c = new Customer();

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM customer WHERE customer_login=? AND customer_pw=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                c.setCustomerLogin(rs.getString("id_customer"));
                c.setCustomerName(rs.getString("customer_name"));
                c.setCustomerPassword(rs.getString("customer_pw"));
                c.setCustomerLogin(rs.getString("customer_login"));
                c.setIdCustomer(rs.getInt("id_customer"));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return c;
    }

    public ArrayList<Booking> returnStatusBooking(Customer c) {

        this.connection.openConnection();

        VehicleDAO vdao = new VehicleDAO();
        ArrayList<Integer> vIdList = new ArrayList<>();
        ArrayList<Booking> statuses = new ArrayList<>();
        PreparedStatement ps;
        String sqlQuery;


        for (Vehicle v : vdao.readVehicleByCustomer(c)) {
            vIdList.add(v.getIdVehicle());
        }

        try {

            for (int i : vIdList) {


                sqlQuery = "SELECT * FROM booking WHERE id_vehicle =?";

                ps = this.connection.getConnection().prepareStatement(sqlQuery);

                ps.setInt(1, i);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setIdBooking(rs.getInt("id_booking"));
                    b.setStatus(rs.getString("status_booking"));
                    b.setLocalDate(rs.getDate("date_booking"));
                    b.setIdMechanic(rs.getInt("id_mechanic"));
                    b.setIdVehicle(rs.getInt("id_vehicle"));


                    statuses.add(b);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

        return statuses;
    }

}
