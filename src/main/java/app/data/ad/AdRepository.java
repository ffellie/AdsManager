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
public interface AdRepository extends JpaRepository<Ad,Long> {
    Ad getById (long id);
    List<Ad> findAll();
    List<Ad> findByNameContains (String name, Pageable pageable);
    int countAllByNameContains(String  name);
    Set<Ad> findAllByIdIn (Collection<Long> IDs);
    Set<Ad> findAllByUser (User user);

}
