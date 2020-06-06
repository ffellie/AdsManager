package app.routes.usergroups.services;

import app.components.search.SearchService;
import app.data.group.Group;
import app.data.group.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@Service
//@AllArgsConstructor
//public class GroupSearchService implements SearchService {
//    private final GroupService groupService;
//    @Override
//    public List<Group> find (int offset, int limit, String param, String filter, Map<String,Boolean> sortOrders){
//        if (filter!=null)
//            return groupService.findByName(offset,limit,param,sortOrders);
//        return groupService.findAll(offset,limit,sortOrders);
//    }
//    @Override
//    public int count(String name, String filter){
//        if (filter!=null)
//            return groupService.countByName(name);
//        return groupService.count();
//    }
//}
