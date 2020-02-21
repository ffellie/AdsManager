package app.components.search;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<?> find (int offset, int limit, String param, String filter, Map<String, Boolean> sortOrder);
    int count(String param, String filter);
}
