package pm.group01.courseproject.product.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer productID;
    private String name;
    private String description;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    @Column(name = "image_link")
    private String imageLink;
    //@JsonBackReference
    @Column(name = "seller_id")
    private Integer sellerId;
    @Column(name = "brand_id")
    private Integer brandId;
}

