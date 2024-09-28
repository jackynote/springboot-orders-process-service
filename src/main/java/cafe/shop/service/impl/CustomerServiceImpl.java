package cafe.shop.service.impl;

import cafe.shop.exception.CustomerAlreadyExistsException;
import cafe.shop.exception.ResourceNotFoundException;
import cafe.shop.model.constant.UserRole;
import cafe.shop.model.dto.CustomerRegistrationRequest;
import cafe.shop.model.dto.CustomerScoreDto;
import cafe.shop.model.entities.CustomerScore;
import cafe.shop.model.entities.User;
import cafe.shop.repository.CustomerScoreRepository;
import cafe.shop.repository.UserRepository;
import cafe.shop.service.BaseService;
import cafe.shop.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class CustomerServiceImpl extends BaseService implements CustomerService {
    @Autowired
    CustomerScoreRepository customerScoreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public CustomerScoreDto getCustomerScore() {
        return getCustomerScore(UUID.fromString(currentUser().getId()));
    }

    @Override
    public CustomerScoreDto getCustomerScore(UUID customerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        CustomerScore customerScore = customerScoreRepository.findByCustomer(customer)
                .orElseThrow(() -> new ResourceNotFoundException("Customer score not found"));

        CustomerScoreDto dto = new CustomerScoreDto();
        dto.setCustomerId(customerId);
        dto.setScore(customerScore.getScore());

        return dto;
    }

    @Override
    public User registerCustomer(CustomerRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("User with this mobile number already exists");
        }

        // Create a new User entity
        User user = new User();
        user.setUsername(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode("123456a@"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());
        user.setAddress(request.getAddress());
        user.setRole(UserRole.CUSTOMER);

        // Save the user to the database
        return userRepository.save(user);
    }
}
