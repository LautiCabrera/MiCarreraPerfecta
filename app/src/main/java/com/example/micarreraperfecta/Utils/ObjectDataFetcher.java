import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ObjectDataFetcher<T> {
    private final Class<T> type;
    private final ObjectMapper objectMapper;

    public ModelFactory(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }

    public T createInstance(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.treeToValue(rootNode, type);
    }
}
