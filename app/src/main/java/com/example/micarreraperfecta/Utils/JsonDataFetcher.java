import java.sql.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

public class JsonDataFetcher<T> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Devuelve una lista de objetos de la clase espeficada.
     * @param tableName El nombre de la tabla en la cual se encuentran los datos del objeto. Para este proyecto se encuentran como una constante dentro de la clase.
     * @param whereClause Condición de busqueda en la tabla. La palabra "WHERE" está implicita, solo se debe poner el resto de la consulta.
     * @param returnType Es la clase a la cual le vamos a asignar los datos traidos de la tabla. La lista se creará con objetos de esta clase dentro.
     */
    public static <T> List<T> fetchTableData(String tableName, String whereClause, Class<T> returnType) {
        String query = "SELECT * FROM " + tableName;
        if (whereClause != null && !whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        ResultSet resultSet = null;
    
        try {
            resultSet = DBConection.executeQuery(query);
    
            if (resultSet != null) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode resultNode = mapper.createArrayNode();
    
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                while (resultSet.next()) {
                    ObjectNode rowNode = mapper.createObjectNode();
    
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        rowNode.put(columnName, columnValue.toString());
                    }
    
                    resultNode.add(rowNode);
                }
    
                String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
    
                return mapper.readValue(jsonResult, mapper.getTypeFactory().constructCollectionType(List.class, returnType));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConection.closeResources(null, null, resultSet);
        }
        return null;
    }

    /**
     * Devuelve una lista de objetos de la clase espeficada.
     * @param tableName El nombre de la tabla en la cual se encuentran los datos del objeto. Para este proyecto se encuentran como una constante dentro de la clase.
     * @param returnType Clase a la cual se le van a asignar los datos traidos de la tabla. La lista se creará con objetos de esta clase dentro.
     */
    public static <T> List<T> fetchTableData(String tableName, Class<T> returnType) {
        String query = "SELECT * FROM " + tableName;
        
        ResultSet resultSet = null;
    
        try {
            resultSet = DBConection.executeQuery(query);
    
            if (resultSet != null) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode resultNode = mapper.createArrayNode();
    
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                while (resultSet.next()) {
                    ObjectNode rowNode = mapper.createObjectNode();
    
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        rowNode.put(columnName, columnValue.toString());
                    }
    
                    resultNode.add(rowNode);
                }
    
                String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
    
                return mapper.readValue(jsonResult, mapper.getTypeFactory().constructCollectionType(List.class, returnType));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConection.closeResources(null, null, resultSet);
        }
        return null;
    }
    

    // String json = "{\"atributo1\":\"valor1\",\"atributo2\":42}";
    public static <T> T createInstance(Class<T> type, String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.treeToValue(rootNode, type);
    }
}