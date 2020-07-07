package app.data.ad;

import app.data.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad getById(long id);

    List<Ad> findAll();

    List<Ad> findByUserAndNameContains(User user, String name, Pageable pageable);

    int countAllByUserAndNameContains(User user, String name);

    Set<Ad> findAllByIdIn(Collection<Long> IDs);

    List<Ad> findAllByUser(User user);

    List<Ad> findAllByUser(User user, Pageable pageable);

    int countAllByUser(User user);

}
