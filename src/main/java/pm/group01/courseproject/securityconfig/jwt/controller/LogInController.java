package pm.group01.courseproject.securityconfig.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pm.group01.courseproject.securityconfig.jwt.models.AuthenticationRequest;
import pm.group01.courseproject.securityconfig.jwt.models.AuthenticationResponse;
import pm.group01.courseproject.securityconfig.jwt.userdeatils.service.UserDetailsServiceImp;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;

@RestController
public class LogInController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public ResponseEntity<?> adminAuthenticationTokenUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return processRequest(authenticationRequest, "admin");
    }

    @RequestMapping(value = "/enduserlogin", method = RequestMethod.POST)
    public ResponseEntity<?> enduserAuthenticationTokenUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return processRequest(authenticationRequest, "enduser");
    }

    @RequestMapping(value = "/sellerlogin", method = RequestMethod.POST)
    public ResponseEntity<?> sellerAuthenticationTokenUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return processRequest(authenticationRequest, "seller");
    }

    @RequestMapping(value = "/bologin", method = RequestMethod.POST)
    public ResponseEntity<?> boAuthenticationTokenUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return processRequest(authenticationRequest, "brandowner");
    }

    private ResponseEntity<?> processRequest(AuthenticationRequest authenticationRequest, String userType) throws Exception {
        userDetailsService.setUsertype(userType);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

}
