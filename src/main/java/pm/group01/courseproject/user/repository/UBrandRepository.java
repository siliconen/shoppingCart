package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.Brand;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UBrandRepository extends JpaRepository<Brand,Integer> {
    @Query(value = "select p.* " +
            "from Brand p " +
            "where p.name LIKE %:name% and p.code LIKE %:code% ", nativeQuery = true)
    List<Brand> findAllBySomeFields(String code, String name);
}
