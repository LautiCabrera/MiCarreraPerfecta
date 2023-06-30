package com.example.micarreraperfecta.Utils;
import java.sql.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonDataFetcher<T> {
    /**
     * Devuelve una instancia de la clase especificada. La instancia devuelta es la primera de la tabla correspondiente a la clase que cumple con la concici�n de la clausula WHERE.
     * @param tableName El nombre de la tabla en la cual se encuentran los datos del objeto. Para este proyecto se encuentran como una constante dentro de la clase.
     * @param whereClause Condici�n de busqueda en la tabla. La palabra "WHERE" est� implicita, solo se debe poner el resto de la consulta.
     * @param returnType Es la clase a la cual le vamos a asignar los datos traidos de la tabla. La lista se crear� con objetos de esta clase dentro.
     */
    public static <T> T fetchTableData(String tableName, String whereClause, Class<T> returnType) {
    String query = "SELECT * FROM " + tableName;
    if (whereClause != null && !whereClause.isEmpty()) {
        query += " WHERE " + whereClause;
    }
    ResultSet resultSet = null;

    try {
        resultSet = DBConnection.executeQuery(query);

        if (resultSet != null && resultSet.next()) {
            ObjectMapper mapper = new ObjectMapper();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ObjectNode rowNode = mapper.createObjectNode();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                rowNode.put(columnName, columnValue.toString());
            }

            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rowNode);

            return mapper.readValue(jsonResult, returnType);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        DBConnection.closeResources(null, null, resultSet);
    }

    return null;
    }


    /**
     * Devuelve una instancia de la clase especificada. La instancia devuelta es la primera de la tabla correspondiente a la clase.
     * @param tableName El nombre de la tabla en la cual se encuentran los datos del objeto. Para este proyecto se encuentran como una constante dentro de la clase.
     * @param returnType Clase a la cual se le van a asignar los datos traidos de la tabla. La lista se crear� con objetos de esta clase dentro.
     */
    public static <T> T fetchTableData(String tableName, Class<T> returnType) {
    String query = "SELECT * FROM " + tableName;
    
    ResultSet resultSet = null;

    try {
        resultSet = DBConnection.executeQuery(query);

        if (resultSet != null && resultSet.next()) {
            ObjectMapper mapper = new ObjectMapper();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ObjectNode rowNode = mapper.createObjectNode();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                rowNode.put(columnName, columnValue.toString());
            }

            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rowNode);

            return mapper.readValue(jsonResult, returnType);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        DBConnection.closeResources(null, null, resultSet);
    }

    return null;
}

    /**
     * Devuelve una instancia de la clase especificada si no hay una instancia existente. La instancia devuelta es la primera de la tabla correspondiente a la clase.
     * @param tableName El nombre de la tabla en la cual se encuentran los datos del objeto. Para este proyecto se encuentran como una constante dentro de la clase.
     * @param returnType Clase a la cual se le van a asignar los datos traidos de la tabla. La lista se crear� con objetos de esta clase dentro.
     */
    public static <T> T fetchSingletonTableData(String tableName, String whereClause, Class<T> returnType) {
        String query = "SELECT * FROM " + tableName;
        if (whereClause != null && !whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        
        ResultSet resultSet = null;

        try {
            resultSet = DBConnection.executeQuery(query);

            if (resultSet != null && resultSet.next()) {
                ObjectMapper mapper = new ObjectMapper();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                ObjectNode rowNode = mapper.createObjectNode();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    rowNode.put(columnName, columnValue.toString());
                }

                String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rowNode);

                return (T) mapper.readValue(jsonResult, returnType.getMethod("getInstance").getReturnType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(null, null, resultSet);
        }

        return null;
    }

}