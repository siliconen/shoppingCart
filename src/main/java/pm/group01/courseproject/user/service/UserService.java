package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.User;


import java.util.Optional;

@Service
public interface UserService {
    public Optional<User> findUserByUsername(String username);
}
