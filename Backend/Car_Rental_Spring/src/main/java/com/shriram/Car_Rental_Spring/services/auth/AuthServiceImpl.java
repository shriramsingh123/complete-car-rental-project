package com.shriram.Car_Rental_Spring.services.auth;

import com.shriram.Car_Rental_Spring.dto.SignupRequest;
import com.shriram.Car_Rental_Spring.dto.UserDto;
import com.shriram.Car_Rental_Spring.entity.User;
import com.shriram.Car_Rental_Spring.enums.UserRole;
import com.shriram.Car_Rental_Spring.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null){
            User newAdminAccount = new User();
            newAdminAccount.setName("Admin");
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdminAccount.setUserRole(UserRole.ADMIN);
            userRepository.save(newAdminAccount);
            System.out.println("Admin Account created Successfully");
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
           User user = new User();
           user.setId(signupRequest.getId());
           user.setName(signupRequest.getName());
           user.setEmail(signupRequest.getEmail());
           user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
           user.setUserRole(UserRole.CUSTOMER);

           User createUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        userDto.setName(createUser.getName());
        userDto.setEmail(createUser.getEmail());
        userDto.setUserRole(createUser.getUserRole());
        return userDto;

    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
