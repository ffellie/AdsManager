package app.data.group;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface GroupService {
    Group getGroupById (long groupID);
    void saveGroup (Group group);
    List<Group> findAll (int offset, int limit , Map<String, Boolean> sortOrders);
    List<Group> findAll ();
    Integer count();
    List<Group> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders);
    Integer countByName(String name);
}
