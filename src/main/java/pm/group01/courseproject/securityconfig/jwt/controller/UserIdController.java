package pm.group01.courseproject.securityconfig.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;
import pm.group01.courseproject.user.service.UserService;

@RestController
public class UserIdController {

	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping(value = "/getId", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenUser(@RequestBody String jwt){
		try{
			JSONObject obj = new JSONObject(jwt);
			String token = obj.getString("jwt");
			Integer userID = userService.findUserByUsername(jwtUtil.extractUsername(token)).get().getId();
			return ResponseEntity.ok(userID);
		}catch (Exception e){
			return null;
		}

	}
}
