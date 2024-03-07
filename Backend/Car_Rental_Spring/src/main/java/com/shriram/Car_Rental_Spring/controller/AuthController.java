package com.shriram.Car_Rental_Spring.controller;

import com.shriram.Car_Rental_Spring.dto.AuthenticationRequest;
import com.shriram.Car_Rental_Spring.dto.AuthenticationResponse;
import com.shriram.Car_Rental_Spring.dto.SignupRequest;
import com.shriram.Car_Rental_Spring.dto.UserDto;
import com.shriram.Car_Rental_Spring.entity.User;
import com.shriram.Car_Rental_Spring.repository.UserRepository;
import com.shriram.Car_Rental_Spring.services.auth.AuthService;
import com.shriram.Car_Rental_Spring.services.jwt.UserService;
import com.shriram.Car_Rental_Spring.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
        if (authService.hasCustomerWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("Customer already exist with this email",HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createCustomerDto = authService.createCustomer(signupRequest);
        if (createCustomerDto == null){
            return new ResponseEntity<>("User Not Save Please Try Again Later",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createCustomerDto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticateAndGateToken(@RequestBody AuthenticationRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Optional<User> optionalUser = userRepository.findFirstByEmail(authRequest.getEmail());
           AuthenticationResponse authenticationResponse = new AuthenticationResponse();
           authenticationResponse.setJwt(jwtUtil.generateToken(optionalUser.get().getEmail()));
           authenticationResponse.setUserId(optionalUser.get().getId());
           authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            return authenticationResponse;
        } else {
            throw new UsernameNotFoundException("invalid user request ! ");
        }

    }

    @GetMapping("/hello")
    public String message(){
        return "Your Auth Controller is working fine";
    }

}
