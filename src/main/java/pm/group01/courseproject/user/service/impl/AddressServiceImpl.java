package pm.group01.courseproject.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.user.model.Address;
import pm.group01.courseproject.user.repository.AddressRepository;
import pm.group01.courseproject.user.service.AddressService;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public Optional<Address> findAddressById(int id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAddresses() {
        return null;
    }

    @Override
    public List<Address> findAddressByCity(String city) {
        return null;
    }

    @Override
    public List<Address> findAddressByState(String state) {
        return null;
    }

    @Override
    public List<Address> findAddressByZip(String zip) {
        return null;
    }
}
