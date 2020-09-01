package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.EndUser;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EndUserRepository extends JpaRepository<EndUser, Integer> {
    Optional<EndUser> findById(Integer userId);
    EndUser findByUsername(String s);
    EndUser findEndUserByEmail(String s);
}
