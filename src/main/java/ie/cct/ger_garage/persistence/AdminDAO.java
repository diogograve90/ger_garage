package ie.cct.ger_garage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Admin;
import ie.cct.ger_garage.model.Customer;
import ie.cct.ger_garage.model.Payment;
import ie.cct.ger_garage.model.Stock;

public class AdminDAO {

    private final SQLConnection connection;

    public AdminDAO() {

        super();
        this.connection = new SQLConnection();
    }

    public void create(Admin a) {

        this.connection.openConnection();

        String sqlInsert = "INSERT INTO admin_user VALUES(null,?,?)";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, a.getAdminLogin());
            ps.setString(2, a.getAdminPw());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int id = rs.getInt(1);
                a.setIdAdmin(id);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }
    }

    public ArrayList<Admin> readAll() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM admin_user";

        ArrayList<Admin> admins = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Admin a = new Admin();
                a.setIdAdmin(rs.getInt("id_admin"));
                a.setAdminLogin(rs.getString("admin_login"));
                a.setAdminPw(rs.getString("admin_pw"));

                admins.add(a);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

        return admins;
    }

    public Admin readById(Admin a) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM admin_user WHERE id_admin=?";

        try {
            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, a.getIdAdmin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a.setAdminLogin(rs.getString("admin_login"));
                a.setAdminPw(rs.getString("admin_pw"));
                a.setIdAdmin(rs.getInt("id_admin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

        return a;
    }

    public void update(Admin a) {

        this.connection.openConnection();

        String sqlSelect = "UPDATE admin_user SET admin_login=?, admin_pw=? WHERE id_admin=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, a.getAdminLogin());
            ps.setString(2, a.getAdminPw());
            ps.setInt(3, a.getIdAdmin());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

    }

    public void deleteById(Admin a) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM admin_user WHERE id_admin=?";

        try {
            PreparedStatement ps = this.connection.getConnection().prepareCall(sqlSelect);

            ps.setInt(1, a.getIdAdmin());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

    }

    public Admin authenticate(String login, String password) {

        Admin a = new Admin();

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM admin_user WHERE admin_login=? AND admin_pw=?";
        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a.setIdAdmin(rs.getInt("id_admin"));
                a.setAdminLogin(rs.getString("admin_login"));
                a.setAdminPw(rs.getString("admin_pw"));
                System.out.println("oi");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return a;
    }

    public Payment[] seePayments() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM payment";

        ArrayList<Payment> pmts = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Payment pmt = new Payment();
                pmt.setCost(rs.getDouble("cost"));
                pmt.setIdBooking(rs.getInt("id_booking"));
                pmt.setIdCustomer(rs.getInt("id_customer"));
                pmt.setIdPmt(rs.getInt("id_pmt"));
                pmts.add(pmt);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

        Payment[] payments = new Payment[pmts.size()];
        pmts.toArray(payments);


        return payments;


    }

    private void editPayment(int idPmt, Double cost) {

        this.connection.openConnection();

        String sqlUpdate = "UPDATE payment SET cost=? WHERE id_pmt= ?";

        ArrayList<Payment> pmts = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlUpdate);
            ps.setDouble(1, cost);
            ps.setInt(2, idPmt);

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }


    }

    private void addProdsWhereBooking(int idPmt, Stock[] prods) {

        this.connection.openConnection();
        String sqlUpdate1 = "UPDATE payment SET cost=? WHERE id_pmt=?";
        String sqlUpdate2 = "INSERT INTO prod_bought VALUES(null,?,?)";
        Double cost = 0.0;

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlUpdate1);
            PreparedStatement ps2 = this.connection.getConnection().prepareStatement(sqlUpdate2);

            ps2.setInt(1, idPmt);

            for (Stock s : prods) {
                cost += s.getPrice();

                ps2.setInt(2, s.getIdProd());
                ps2.executeUpdate();


            }

            ps.setDouble(1, cost);
            ps.setInt(2, idPmt);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }

    }

}
