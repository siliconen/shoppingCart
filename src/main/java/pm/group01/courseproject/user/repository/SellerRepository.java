package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.Seller;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findById(Integer userId);
    Seller findByUsername(String s);

}
