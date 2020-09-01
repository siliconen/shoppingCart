package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.BrandOwner;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface BrandOwnerRepository extends JpaRepository<BrandOwner, Integer> {
    BrandOwner findByUsername(String s);
    Optional<BrandOwner> findById(Integer userId);
}
