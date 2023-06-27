package com.example.micarreraperfecta.Utils;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.ResultSet;


public class DBCConnection {
    private static final String DATABASE_URL = "jdbc:mysql://ies9021.edu.ar:3306/ies9021_database";
    private static final String DATABASE_USERNAME = "ies9021_userdb";
    private static final String DATABASE_PASSWORD = "Xsw23edc.127";

    @Nullable
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultsetIES9021 selectQuery(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultsetIES9021 resultsetIES9021 = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            resultsetIES9021.result = statement.executeQuery();
            resultsetIES9021.state = true;
            resultsetIES9021.clarification = "OK";
        } catch (SQLException e) {
            e.printStackTrace();
            resultsetIES9021.state = false;
            resultsetIES9021.clarification = e.getMessage();
            resultsetIES9021.result = null;
        } finally {
            try {
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
        return resultsetIES9021;
    }

    public static ResultsetIES9021 executeQuery(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultsetIES9021 resultsetIES9021 = null;

        if (query.substring(0, 5).equalsIgnoreCase("INSERT") || query.substring(0, 5).equalsIgnoreCase("DELETE") || query.substring(0, 5).equalsIgnoreCase("UPDATE")) {
            try {
                connection = getConnection();
                statement = connection.prepareStatement(query);

                int fActualizadas = statement.executeUpdate();

                resultsetIES9021.state = true;
                switch (query.toUpperCase().charAt(0)) {
                    case 'I':
                        resultsetIES9021.clarification = ("Se insertaron " + fActualizadas + " filas.");
                        break;
                    case 'D':
                        resultsetIES9021.clarification = ("Se borraron " + fActualizadas + " filas.");
                        break;
                    case 'U':
                        resultsetIES9021.clarification = ("Se actualizaron " + fActualizadas + " filas.");
                        break;

                }
                resultsetIES9021.result = null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resultsetIES9021.state = false;
                resultsetIES9021.clarification = e.getMessage();
                resultsetIES9021.result = null;
            }
        }else {
            resultsetIES9021.state = false;
            resultsetIES9021.clarification = "La consulta que intenta realizar no es correcta";
            resultsetIES9021.result = null;
        }

        return resultsetIES9021;
    }
}
