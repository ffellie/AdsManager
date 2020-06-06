package app.data.group.groupUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroupUserRepository extends JpaRepository<GroupUser,Long> {
    List<GroupUser> getAllByGroupId(Long group);
    List<GroupUser> getAllByUser(Long user);

    void deleteAllByGroupId(Long group);
    void deleteAllByUser(Long user);
    void deleteAllByUserAndGroupId(Long user, Long groupId);
}
