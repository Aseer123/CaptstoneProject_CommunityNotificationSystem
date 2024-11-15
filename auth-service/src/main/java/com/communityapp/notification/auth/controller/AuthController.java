package com.communityapp.notification.auth.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communityapp.notification.auth.model.User;
import com.communityapp.notification.auth.repository.UserRepository;
import com.communityapp.notification.auth.service.AuthService;
import com.communityapp.notification.auth.service.JwtService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/hello")
    public String hello() {
    	return "Welcome";
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return authService.saveUser(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
    	String token=authService.validateUser(user);
    	return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public Optional<User> getUserByEmail(@PathVariable String username) {
        return authService.findByUsername(username);
    }
    
    @PostMapping("/validate/token")
    public ResponseEntity<?> validateToken(@RequestParam String token){
    	return new ResponseEntity<>(authService.validateToken(token),HttpStatus.OK);
    }
    
    @GetMapping("/validate/role")
    public ResponseEntity<?> validateUserRole(@RequestParam("token")String token,@RequestParam("role") String role ){
    	String username=jwtService.getUserId(token);
    	User user=userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User Not Found"));
    	
    	if(user.getRole().equalsIgnoreCase(role)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    		}else {
    			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    		}
    	
    }
    
    @GetMapping("/users/role/{role}/emails")
    public ResponseEntity<List<String>> getUserEmailsByRole(@PathVariable String role) {
        try {
            List<String> emails = userRepository.findByRole(role)
                .stream()
                .map(User::getUsername)  // since username is email
                .collect(Collectors.toList());
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
