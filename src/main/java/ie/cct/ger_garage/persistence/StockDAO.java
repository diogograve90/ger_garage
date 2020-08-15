package ie.cct.ger_garage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ie.cct.ger_garage.model.Stock;

public class StockDAO {

    private final SQLConnection connection;

    public StockDAO() {

        super();
        this.connection = new SQLConnection();

    }

    public void create(Stock s) {

        this.connection.openConnection();

        String sqlInsert = "INSERT INTO stock VALUES(null,?,?,?)";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, s.getProduct());
            ps.setDouble(2, s.getPrice());
            ps.setInt(3, s.getQuantity());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int id = rs.getInt(1);
                s.setIdProd(id);

            }


        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public ArrayList<Stock> readAll() {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM stock";

        ArrayList<Stock> products = new ArrayList<>();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Stock s = new Stock();
                s.setIdProd(rs.getInt("id_prod"));
                s.setProduct(rs.getString("product_name"));
                s.setPrice(rs.getDouble("price"));
                s.setQuantity(rs.getInt("qtt"));

                products.add(s);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return products;

    }

    public Stock readById(Stock st) {

        this.connection.openConnection();

        String sqlSelect = "SELECT * FROM stock WHERE id_prod=?";

        Stock s = new Stock();

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, st.getIdProd());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                s.setIdProd(rs.getInt("id_prod"));
                s.setProduct(rs.getString("product_name"));
                s.setPrice(rs.getDouble("price"));
                s.setQuantity(rs.getInt("qtt"));

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

        return s;

    }

    public void update(Stock s) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE stock SET product_name=?, price=?, qtt=? WHERE id_prod=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, s.getProduct());
            ps.setDouble(2, s.getPrice());
            ps.setInt(3, s.getQuantity());
            ps.setInt(4, s.getIdProd());

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void deleteById(Integer id) {

        this.connection.openConnection();

        String sqlSelect = "DELETE FROM stock WHERE id_prod=?";

        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlSelect);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }

    public void shoppingCartPayment(Stock[] prods, Integer custId) {


        this.connection.openConnection();
        String sqlInsert = "INSERT INTO payment VALUES(null,?,null,0.0)";
        String sqlUpdate = "UPDATE payment SET cost = ? WHERE id_pmt = ?";
        int idPmt = 0;
        double cost = 0.0;
        try {
            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, custId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idPmt = rs.getInt(1);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
            shoppingCartUpdate(prods, idPmt);
        }


    }

    private void shoppingCartUpdate(Stock[] prods, int idPmt) {


        this.connection.openConnection();

        String sqlUpdate = "UPDATE payment SET cost = ? WHERE id_pmt = ?";

        double cost = 0.0;
        try {


            for (Stock s : prods) {
                cost += s.getPrice();

                updateQuantity(s, idPmt);

                this.connection.openConnection();

                PreparedStatement ps2 = this.connection.getConnection().prepareStatement(sqlUpdate);
                ps2.setDouble(1, cost);
                ps2.setInt(2, idPmt);

                ps2.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection.closeConnection();
        }


    }

    public void updateQuantity(Stock s, int idPmt) {

        this.connection.openConnection();

        String sqlInsert = "UPDATE stock SET qtt=? WHERE id_prod=?";
        String sqlInsert2 = "INSERT INTO prod_bought VALUES(null,?,?)";
        try {

            PreparedStatement ps = this.connection.getConnection().prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, s.getQuantity());
            ps.setInt(2, s.getIdProd());


            ps.executeUpdate();

            PreparedStatement ps2 = this.connection.getConnection().prepareStatement(sqlInsert2);
            ps2.setInt(1, idPmt);

            ps2.setInt(2, s.getIdProd());

            ps2.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            this.connection.closeConnection();

        }

    }


}
