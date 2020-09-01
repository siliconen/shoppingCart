package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Address;
import pm.group01.courseproject.user.model.EndUser;
import pm.group01.courseproject.user.repository.EndUserRepository;
import pm.group01.courseproject.user.service.AddressService;
import pm.group01.courseproject.user.service.EndUserService;

import java.util.List;
import java.util.Optional;

@Service
public class EndUserServiceImpl implements EndUserService {
    @Autowired
    EndUserRepository endUserRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EndUser findEndUserByUsername(String username) {
        return endUserRepository.findByUsername(username);
    }

    @Override
    public EndUser findEndUserByEmail(String email) {
        return endUserRepository.findEndUserByEmail(email);
    }

    @Override
    public Optional<EndUser> findEndUserById(Integer id) {
        return endUserRepository.findById(id);
    }

    @Override
    public List<EndUser> findEndUsers() {
        return endUserRepository.findAll();
    }

    @Override
    public EndUser saveEndUser(EndUser endUser) {
        Address newAddress = new Address();
        addressService.saveAddress(newAddress);
        endUser.setAddress(newAddress);
        endUser.setPassword(passwordEncoder.encode(endUser.getPassword()));
        return endUserRepository.save(endUser);
    }

    @Override
    public EndUser updateEndUser(EndUser endUser) {
        Address newAddress = endUser.getAddress();
        addressService.saveAddress(newAddress);
        String password = endUser.getPassword();
        if (password != null) {
            String encodedPassword = passwordEncoder.encode(endUser.getPassword());
            String dbPassword = endUserRepository.findById(endUser.getId()).get().getPassword();
            if (!dbPassword.equals(encodedPassword) && !dbPassword.equals(password))
                endUser.setPassword(encodedPassword);
        }
        return endUserRepository.save(endUser);
    }

    @Override
    public EndUser updateEndUserNotChangePass(EndUser endUser) {
        Address newAddress = endUser.getAddress();
        addressService.saveAddress(newAddress);
        return endUserRepository.save(endUser);
    }
}
