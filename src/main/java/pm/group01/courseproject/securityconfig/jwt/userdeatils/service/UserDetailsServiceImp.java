package pm.group01.courseproject.securityconfig.jwt.userdeatils.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pm.group01.courseproject.securityconfig.jwt.userdeatils.impl.UserDetailsImp;
import pm.group01.courseproject.user.model.User;
import pm.group01.courseproject.user.repository.AdministratorRepository;
import pm.group01.courseproject.user.repository.BrandOwnerRepository;
import pm.group01.courseproject.user.repository.EndUserRepository;
import pm.group01.courseproject.user.repository.SellerRepository;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private BrandOwnerRepository brandOwnerRepository;
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private SellerRepository sellerRepository;

    private User abstractUser = null;

    private String usertype = "anonymous";


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        switch (usertype) {
            case "admin":
                abstractUser = administratorRepository.findByUsername(s);
                break;
            case "enduser":
                abstractUser = endUserRepository.findByUsername(s);
                break;
            case "seller":
                abstractUser = sellerRepository.findByUsername(s);
                break;
            case "brandowner":
                abstractUser = brandOwnerRepository.findByUsername(s);
                break;
            default:
                abstractUser = null;
        }
        if (abstractUser == null)
            throw new UsernameNotFoundException("user name not found");
        return new UserDetailsImp(abstractUser);
    }

    public String getUserType() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

}
