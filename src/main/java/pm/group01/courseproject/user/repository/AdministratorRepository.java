package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.Administrator;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findById(Integer userId);
    Administrator findByUsername(String s);

}
