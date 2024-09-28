package cafe.shop.service;

import cafe.shop.model.dto.CustomerRegistrationRequest;
import cafe.shop.model.dto.CustomerScoreDto;
import cafe.shop.model.entities.User;

import java.util.UUID;

public interface CustomerService {

    CustomerScoreDto getCustomerScore();

    CustomerScoreDto getCustomerScore(UUID customerId);

    User registerCustomer(CustomerRegistrationRequest request);
}
