package app.data.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String email);

    List<User> findByNameContains(String name, Pageable pageable);

    long countByNameContains(String name);

    List<User> findAllByIdIn (Collection<Long> IDs, Pageable pageable);
    List<User> findAllByIdNotIn (Collection<Long> IDs, Pageable pageable);
    int countAllByIdIn (Collection<Long> IDs);
    int countAllByIdNotIn (Collection<Long> IDs);
}
