package pm.group01.courseproject.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.securityconfig.jwt.userdeatils.impl.UserDetailsImp;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;
import pm.group01.courseproject.user.model.EndUser;
import pm.group01.courseproject.user.service.EndUserService;
import springfox.documentation.spring.web.json.Json;

import java.util.Optional;

@RestController
@RequestMapping("/enduser")
public class EndUserController {

    @Autowired
    EndUserService endUserService;

    @Autowired
    JwtUtil jwtUtil;

    //    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{endUserId}")
    public Optional<EndUser> getEndUser(@PathVariable Integer endUserId, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        endUserId = Integer.parseInt(jwtUtil.extractId(token));
        return endUserService.findEndUserById(endUserId);
    }

    @PostMapping("/signup")
    public Json signUpEndUser(@RequestBody EndUser endUser) {
        EndUser retrieved1 = endUserService.findEndUserByUsername(endUser.getUsername());
        EndUser retrieved2 = endUser.getEmail() == null ? null : endUserService.findEndUserByEmail(endUser.getEmail());
        String rs = "";
        if (retrieved1 == null && retrieved2 == null) {
            EndUser newuser = endUserService.saveEndUser(endUser);
            String jwt = jwtUtil.generateToken(new UserDetailsImp(newuser));
            rs =
                    "{" +
                            "\"id\": \"" + newuser.getId() + "\"," +
                            "\"username\": \"" + newuser.getUsername() + "\"," +
                            "\"password\": \"" + newuser.getPassword() + "\"," +
                            "\"email\": \"" + newuser.getEmail() + "\"," +
                            "\"jwt\": \"" + jwt + "\"}";
            return new Json(rs);
        }
        if (retrieved1 != null)
            rs += "User Name";

        if (retrieved2 != null)
            rs += rs.isEmpty() ? "email" : " and email";
        return new Json("{\"comment\": \"" + rs + " already exist\"}");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public EndUser updateEndUser(@RequestBody EndUser endUser, @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer enduserid = Integer.parseInt(jwtUtil.extractId(token));
        EndUser dbendUser = endUserService.findEndUserById(enduserid).get();
        if (endUser.getEmail() != null) dbendUser.setEmail(endUser.getEmail());
        if (endUser.getName() != null) dbendUser.setName(endUser.getName());
        if (endUser.getAddress() != null) dbendUser.setAddress(endUser.getAddress());
        if (endUser.getPassword() != null) dbendUser.setPassword(endUser.getPassword());
        return endUserService.updateEndUser(dbendUser);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/editProfile")
    public EndUser updateEndUser_NotChangePass(@RequestBody EndUser endUser,
                                            @RequestHeader(name = "Authorization") String token) {
        token = token.replaceAll("Bearer", "").trim();
        Integer enduserid = Integer.parseInt(jwtUtil.extractId(token));
        EndUser dbendUser = endUserService.findEndUserById(enduserid).get();
        dbendUser.setEmail(endUser.getEmail());
        dbendUser.setName(endUser.getName());
        dbendUser.setAddress(endUser.getAddress());
        dbendUser.setPassword(endUser.getPassword());
        return endUserService.updateEndUser(dbendUser);
    }

    /* [addressId] from localhost:8090/enduser/{userId}/address */
    @GetMapping("/{userId}/address")
    public Integer getAddress(@PathVariable Integer userId) {
        Integer addressID = -1;
        Optional<EndUser> endUser = endUserService.findEndUserById(userId);
        if (endUser.isPresent()) {
            addressID = endUser.get().getAddress().getAddressId();
        }
        return addressID;
    }

    @GetMapping("/create")
    public EndUser create() {
        EndUser endUser = new EndUser();
        endUser.setName("Islam");
        endUser.setEmail("e@m.com");
        endUser.setUsername("Islam");
        endUser.setPassword("pass");
        return endUserService.saveEndUser(endUser);
    }
}
