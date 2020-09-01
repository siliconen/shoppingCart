package pm.group01.courseproject.user.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BrandOwner extends User {
    @OneToOne
    @JoinColumn(name = "brand_id")
    @Valid @NotNull
    Brand brand;
}
