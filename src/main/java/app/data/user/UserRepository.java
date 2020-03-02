package app.data.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String email);

    List<User> findByNameContains(String name, Pageable pageable);

    long countByNameContains(String name);

    List<User> findAllByIdIn (Set<Long> IDs);
    List<User> findAllByIdNotIn (Set<Long> IDs);
    int countAllByIdIn (Set<Long> IDs);
    int countAllByIdNotIn (Set<Long> IDs);
}
