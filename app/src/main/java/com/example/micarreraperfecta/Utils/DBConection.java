import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBConection {
    private static final String DATABASE_URL = "jdbc:mysql://ies9021.edu.ar:3306/ies9021_database";
    private static final String DATABASE_USERNAME = "ies9021_userdb";
    private static final String DATABASE_PASSWORD = "Xsw23edc.127";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connection successful!");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Failed to load database driver", e);
        }
    }

    public static ResultSet executeQuery(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            closeResources(connection, statement, result);
        }
    
        return null;
    }

    public static void closeResources(Connection connection, PreparedStatement statement, ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
