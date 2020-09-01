package pm.group01.courseproject.product.model;

import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@SequenceGenerator(name="seqCategory", initialValue=5)
@Entity
public class Category{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqCategory")
    @Column(name="category_id")
    private Integer categoryId;

    private String name;
}
