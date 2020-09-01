package pm.group01.courseproject.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller extends User {

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Product> productList = new ArrayList<>();
    boolean status = false;

    public boolean getStatus() {
        return this.status;
    }
}
