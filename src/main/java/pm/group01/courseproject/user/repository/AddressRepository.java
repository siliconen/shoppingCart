package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pm.group01.courseproject.user.model.Address;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AddressRepository  extends JpaRepository<Address, Integer> {
    Optional<Address> findById(Integer userId);}
