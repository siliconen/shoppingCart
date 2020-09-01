package pm.group01.courseproject.product.model;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndCategoryForUI {
    private List<Product> products;
    private Set<Category> categories;
}
