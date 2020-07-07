package app.data.group;

import app.data.group.groupAd.GroupAd;
import app.data.group.groupAd.GroupAdRepository;
import app.data.group.groupUser.GroupUser;
import app.data.group.groupUser.GroupUserRepository;
import app.data.user.User;
import app.data.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupAdRepository groupAdRepository;
    private final GroupUserRepository groupUserRepository;
    private final UserService userService;

    public Group getGroupById(long groupID) {
        return groupRepository.getById(groupID);
    }

    public List<Group> getGroupByUser(Long user) {
        return groupRepository.findAllById(groupUserRepository.getAllByUser(user).stream()
                .map(GroupUser::getGroupId)
                .collect(Collectors.toList()));
    }

    public List<Long> getUsersByGroup(Long group) {
        return groupUserRepository.getAllByGroupId(group).stream()
                .map(GroupUser::getUser)
                .collect(Collectors.toList());
    }


    public List<Long> getAdsByGroup(Long group) {
        return groupAdRepository.getAllByGroupId(group).stream()
                .map(GroupAd::getAd)
                .collect(Collectors.toList());
    }

    public void removeUserFromGroup(Long user, Long group) {
        groupUserRepository.deleteAllByUserAndGroupId(user, group);
    }

    @Transactional
    public void remove(Group group) {
        groupRepository.delete(group);
        groupUserRepository.deleteAllByGroupId(group.getId());
        groupAdRepository.deleteAllByGroupId(group.getId());
    }

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    @Transactional
    public void createAndSaveGroupWithCurrentUser(Group group) {
        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByName(secUser.getName());

        saveGroup(group);
        addUserToGroup(group.getId(), user.getId());
    }

    public void addUserToGroup(Long group, Long user) {
        groupUserRepository.save(GroupUser.builder()
                .groupId(group)
                .user(user)
                .build());

    }

    public void addAdsToGroup(Long group, List<Long> ads) {
        for (Long ad : ads) {
            groupAdRepository.save(GroupAd.builder()
                    .groupId(group)
                    .ad(ad)
                    .build());
        }
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public List<Group> findAll(int offset, int limit, Map<String, Boolean> sortOrders, List<Long> ids) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        return groupRepository.findDistinctByIdIn(ids, pageRequest);
    }

    public Integer count(List<Long> ids) {
        return Math.toIntExact(groupRepository.countDistinctByIdIn(ids));
    }


    public List<Group> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        List<Group> items = groupRepository.findByNameContains(name, pageRequest);
        return items;
    }

    public Integer countByName(String name) {
        return Math.toIntExact(groupRepository.countAllByNameContains(name));
    }
}
