package app.data.group;

import app.data.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

public interface GroupService {
    Group getGroupById (long groupID);
    void saveGroup (Group group);
    List<Group> findAll (int offset, int limit , Map<String, Boolean> sortOrders);
    List<Group> findAll ();
    Integer count();
    List<Group> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders);
    Integer countByName(String name);
    void remove(Group group);
    void remove(Collection<Group> groups);
    void removeUserFromGroup(Group group,User user);
    List<Group> findDistinctByUserIDsIn (Collection<Long> userIDs);
}
