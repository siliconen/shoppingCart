package pm.group01.courseproject.product.model;

import lombok.*;

import javax.persistence.*;

@Data
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="seqProductStock", initialValue=20)
@Entity
public class ProductStock{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqProductStock")
    private Integer stockId;

    @OneToOne
    //@Column(name = "product_id")
    private Product product;
    private Integer quantity;
}
