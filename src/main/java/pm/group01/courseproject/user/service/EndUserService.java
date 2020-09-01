package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.EndUser;

import java.util.List;
import java.util.Optional;

@Service
public interface EndUserService {
     Optional<EndUser> findEndUserById(Integer id);

     List<EndUser> findEndUsers();

     EndUser saveEndUser(EndUser endUser);

     EndUser updateEndUser(EndUser endUser);

     EndUser updateEndUserNotChangePass(EndUser endUser);

     EndUser findEndUserByUsername(String username);

     EndUser findEndUserByEmail(String email);
}
