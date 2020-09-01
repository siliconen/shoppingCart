package pm.group01.courseproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pm.group01.courseproject.user.model.Product;

import java.util.List;

public interface UProductRepository extends JpaRepository<Product, Integer> {
    List<Product>findAllByStatus(Boolean status);
    List<Product>findAllBySeller(Boolean status);
}
