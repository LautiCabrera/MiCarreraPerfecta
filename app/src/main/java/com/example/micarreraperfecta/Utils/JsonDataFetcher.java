import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.ResultSet;

import java.io.IOException;

public class JsonDataFetcher<T> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static T fetchTableData(String tableName, String whereClause, Class<T> returnType){
        String query = "SELECT * FROM " + tableName;
        if (whereClause != null && !whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }

        try {
            ResultSet resultSet = DBConection.executeQuery(query);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            JsonNode resultNode = mapper.createArrayNode();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                ObjectNode rowNode = mapper.createObjectNode();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    rowNode.put(columnName, columnValue.toString());
                }

                ((ArrayNode) resultNode).add(rowNode);
            }

            rootNode.set("result", resultNode);
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            return mapper.readValue(jsonResult, returnType);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    // String json = "{\"atributo1\":\"valor1\",\"atributo2\":42}";
    public static <T> T createInstance(Class<T> type, String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.treeToValue(rootNode, type);
    }
}
