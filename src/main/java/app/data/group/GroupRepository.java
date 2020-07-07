package app.data.group;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group getById(long id);

    List<Group> findAll();

    List<Group> findByNameContains(String name, Pageable pageable);

    List<Group> findDistinctByIdIn(List<Long> ids, Pageable pageable);

    int countAllByNameContains(String name);

    int countDistinctByIdIn(List<Long> ids);

    List<Group> findByUrl(String url);

}
