import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DataLoader<T> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T createInstance(Class<T> type, String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.treeToValue(rootNode, type);
    }
}
