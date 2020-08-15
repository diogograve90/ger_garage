package ie.cct.ger_garage.persistence;

import ie.cct.ger_garage.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PaymentDAO {

    private final SQLConnection connection;

    public PaymentDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Payment p) {


        this.connection.openConnection();


        String sqlInsert = "INSERT INTO payment VALUES(null,?,?,?)";


        try {


            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);


            ps.setInt(1,p.getIdCustomer());
            ps.setInt(2,p.getIdBooking());
            ps.setDouble(3,p.getCost());


            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {


                int id = rs.getInt(1);
                p.setIdPmt(id);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Payment> readAll() {

        this.connection.openConnection();


        String sqlSelect = "SELECT * FROM payment";


        ArrayList<Payment> payments = new ArrayList<>();

        try {


            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);


            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Payment p= new Payment();
                p.setIdPmt(rs.getInt("id_pmt"));
                p.setIdCustomer(rs.getInt("id_customer"));
                p.setIdBooking(rs.getInt("id_booking"));
                p.setCost(rs.getDouble("cost"));
                payments.add(p);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }


        return payments;

    }

    public Payment readById(Payment p) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM payment WHERE id_pmt=?";

        Payment v = new Payment();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, p.getIdPmt());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Payment p2 = new Payment();
                p.setIdPmt(rs.getInt("id_pmt"));
                p.setIdCustomer(rs.getInt("id_customer"));
                p.setIdBooking(rs.getInt("id_booking"));
                p.setCost(rs.getDouble("cost"));


            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return v;

    }

    public void update(Payment p) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE payment SET id_customer=?, id_booking=?, cost=? WHERE id_pmt=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,p.getIdCustomer());
            ps.setInt(2,p.getIdBooking());
            ps.setDouble(3,p.getCost());
            ps.setInt(4,p.getIdPmt());


            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Payment p) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM payment WHERE id_pmt=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, p.getIdPmt());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void editPaymentByBooking(Payment p ){


        this.connection.openConnection();

        String sqlInsert = "UPDATE payment SET cost=? WHERE id_pmt=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(3,p.getCost());
            ps.setInt(4,p.getIdPmt());


            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }








    }




