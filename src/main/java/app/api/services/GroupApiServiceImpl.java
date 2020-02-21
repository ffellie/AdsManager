package app.api.services;

import app.api.models.GroupModel;
import app.data.ad.Ad;
import app.data.ad.AdRepository;
import app.data.group.Group;
import app.data.group.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupApiServiceImpl implements GroupApiService {
    private final AdRepository adRepository;
    private final GroupRepository groupRepository;
    @Override
    public ResponseEntity<GroupModel> getGroup (String url)  {
        System.out.println("Got request");
        List<Group> groups = groupRepository.findByUrl(url);
        if (groups.size()!=1)
            return ResponseEntity.notFound().build();
        Group group = groups.get(0);
        List<Ad> ads = new LinkedList<>(adRepository.findAllByIdIn(group.getAdIDs()));
        return ResponseEntity.ok()
                .body(new GroupModel(group.getName(),ads));
    }
}
