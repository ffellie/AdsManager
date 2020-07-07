package app.data.promotion;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    void deleteAllByAdIDIn(Collection<Long> adIDs);

    void deleteAllByGroupIdAndAdIDIn(Long group, Collection<Long> adIDs);

    int countAllByIdIn(Collection<Long> ids);

    int countAllByGroupId(Long group);

    List<Promotion> findAllByGroupId (Long group);

    List<Promotion> findAllByGroupId (Long group, Pageable pageable);
}
