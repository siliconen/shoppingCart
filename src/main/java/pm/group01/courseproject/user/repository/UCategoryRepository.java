package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.Category;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UCategoryRepository extends JpaRepository<Category,Integer> {
}
