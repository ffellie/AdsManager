package app.routes.usergroups.services;

import app.components.search.SearchService;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.User;
import app.data.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class UserSearchService  implements SearchService {
    private final UserService userService;
    @Override
    public List<User> find (int offset, int limit, String param, String filter, Map<String,Boolean> sortOrders){
        if (filter!=null)
            return userService.findByName(offset,limit,param,sortOrders);
        return userService.findAll(offset,limit,sortOrders);
    }
    @Override
    public int count(String name, String filter){
        if (filter!=null)
            return userService.countByName(name);
        return userService.count();
    }
}
