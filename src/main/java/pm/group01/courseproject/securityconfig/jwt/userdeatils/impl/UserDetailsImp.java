package pm.group01.courseproject.securityconfig.jwt.userdeatils.impl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pm.group01.courseproject.user.model.*;

import java.util.*;

public class UserDetailsImp implements UserDetails {

    private static final long serialVersionUID = 1L;
    private User systemUser;

    public UserDetailsImp(User systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Map<String, List<GrantedAuthority>> user = new HashMap<>();

        List<GrantedAuthority> enduserRole = new ArrayList<>();
        enduserRole.add(new SimpleGrantedAuthority("USER"));
        user.put("endUser", enduserRole);

        List<GrantedAuthority> sellerRole = new ArrayList<>();
        sellerRole.add(new SimpleGrantedAuthority("SELLER"));
        user.put("seller", sellerRole);

        List<GrantedAuthority> brandOwnerRole = new ArrayList<>();
        brandOwnerRole.add(new SimpleGrantedAuthority("BRANDOWNER"));
        user.put("brandOwner", brandOwnerRole);

        List<GrantedAuthority> administratorRoles = new ArrayList<>();
        administratorRoles.add(new SimpleGrantedAuthority("ADMIN"));
        user.put("admin", administratorRoles);

        List<GrantedAuthority> abstractUser = null;

        if (systemUser instanceof Administrator) abstractUser = user.get("admin");
        if (systemUser instanceof BrandOwner) abstractUser = user.get("brandOwner");
        if (systemUser instanceof EndUser) abstractUser = user.get("endUser");
        if (systemUser instanceof Seller) abstractUser = user.get("seller");


        return abstractUser;
    }

    @Override
    public String getPassword() {
        return systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return systemUser.getUsername();
    }

    public Integer getUserId() {
        return systemUser.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
