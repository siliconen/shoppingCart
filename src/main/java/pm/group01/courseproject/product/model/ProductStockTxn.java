package pm.group01.courseproject.product.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="seqProductStockTxn", initialValue=20)
@Entity
public class ProductStockTxn{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqProductStockTxn")
    private Integer stockTxnId;

    @OneToOne
    //@Column(name = "product_id")
    private Product product;

    private Integer sellerId;

    private int quantity;

    private LocalDate txnDate;
}
