package app.data.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    void deleteAllByAdIDIn(List<Long> adIDs);
    void deleteAllByGroupAndAdIDIn(Group group,List<Long> adIDs);

}
