package pm.group01.courseproject.product.model;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFromUserDTO {
    private Integer categoryId;
    private String name;
}
