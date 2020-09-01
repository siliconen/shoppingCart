package pm.group01.courseproject.user.service;

import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Address;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {
    Optional<Address> findAddressById(int id);
    Address saveAddress(Address address);
    List<Address> findAddresses();
    List<Address> findAddressByCity(String city);
    List<Address> findAddressByState(String state);
    List<Address> findAddressByZip(String zip);
}
