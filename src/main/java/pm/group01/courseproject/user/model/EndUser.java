package pm.group01.courseproject.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndUser extends User {

    @OneToOne
    Address address;

}
