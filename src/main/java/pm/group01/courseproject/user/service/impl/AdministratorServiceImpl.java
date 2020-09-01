package pm.group01.courseproject.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Administrator;
import pm.group01.courseproject.user.repository.AdministratorRepository;
import pm.group01.courseproject.user.service.AdministratorService;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    AdministratorRepository administratorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Administrator> findAllAdministrators() {
        return administratorRepository.findAll();
    }

    @Override
    public Optional findAdministratorById(int id) {
        return administratorRepository.findById(id);
    }

    @Override
    public Administrator saveAdministrator(Administrator administrator) {
        administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator saveAdministratorNotChangePass(Administrator administrator) {
        String password = administrator.getPassword();
        if (password != null) {
            String encodedPassword = passwordEncoder.encode(administrator.getPassword());
            String dbPassword = administratorRepository.findById(administrator.getId()).get().getPassword();
            if (!dbPassword.equals(encodedPassword) && !dbPassword.equals(password))
                administrator.setPassword(encodedPassword);
        }
        return administratorRepository.save(administrator);
    }

    @Override
    public Optional<Administrator> findAdministratorByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Administrator> findAdministratorByEmail(String email) {
        return Optional.empty();
    }


}
