package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Administrator;

import java.util.List;
import java.util.Optional;

@Service
public interface AdministratorService {
    List<Administrator> findAllAdministrators();
    Optional<Administrator> findAdministratorById(int id);
    Administrator saveAdministrator(Administrator administrator);
    Administrator saveAdministratorNotChangePass(Administrator administrator);

    Optional<Administrator> findAdministratorByUsername(String username);
    Optional<Administrator> findAdministratorByEmail(String email);
}
