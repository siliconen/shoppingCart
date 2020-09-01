package pm.group01.courseproject.user.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productID;
    private String name;
    private String description;
    @OneToOne
    private Brand brand;
    private double price;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "sellerId", nullable=false)
    @Valid
    @NotNull
    private Seller seller;
    @OneToOne
    private Category category;
    private String imageLink;
}
