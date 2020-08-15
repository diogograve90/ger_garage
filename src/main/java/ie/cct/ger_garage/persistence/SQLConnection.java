package ie.cct.ger_garage.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {


    private static final String ip = "localhost";
    private static final String port = "3306";
    private static final String login = "root";
    private static final String password = "Macielgrave";
    private static final String dbName = "ger_db";
    private Connection connection;

    public SQLConnection() {
        super();
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?useTimezone=true&serverTimezone=UTC", login, password);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {

            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

}
