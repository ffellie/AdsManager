package app.data.group.groupAd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroupAdRepository extends JpaRepository<GroupAd, Long> {
    List<GroupAd> getAllByGroupId(Long group);

    List<GroupAd> getAllByAd(Long ad);

    void deleteAllByGroupId(Long group);

    void deleteAllByAd(Long ad);

    void deleteAllByAdIn(List<Long> ads);
}
