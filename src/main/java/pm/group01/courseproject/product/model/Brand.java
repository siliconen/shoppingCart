package pm.group01.courseproject.product.model;

import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@SequenceGenerator(name="seqBrand", initialValue=5)
public class Brand{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqBrand")
    private Integer id;
    private String code;
    private String name;
}
