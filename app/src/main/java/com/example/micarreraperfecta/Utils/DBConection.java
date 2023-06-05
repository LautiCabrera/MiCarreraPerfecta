import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBConection {
    private static final String DATABASE_URL = "jdbc:mysql://ies9021.edu.ar:3306/ies9021_database";
    private static final String DATABASE_USERNAME = "ies9021_userdb";
    private static final String DATABASE_PASSWORD = "Xsw23edc.127";

    public static Conection getConnection() throws SQLException{
        try{
            class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)
        }catch( ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeQuery(String query){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Procesar los resultados
            while (resultSet.next()) {
                int id = result.getInt("id_branch");
                String nombre = result.getString("name");

                System.out.println("Branch: " + id + " " + nombre);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(result != null){
                    result.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}