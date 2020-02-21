package app.data.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Transactional
    public Optional<User> findByName(String email);

    @Transactional
    List<User> findByNameContains(String name, Pageable pageable);

    @Transactional
    public long countByNameContains(String name);
}
