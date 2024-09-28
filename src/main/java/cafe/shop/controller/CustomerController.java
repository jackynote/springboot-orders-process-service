package cafe.shop.controller;

import cafe.shop.exception.CustomerAlreadyExistsException;
import cafe.shop.exception.InvalidInputException;
import cafe.shop.model.dto.CustomerRegistrationRequest;
import cafe.shop.model.dto.CustomerScoreDto;
import cafe.shop.model.dto.ErrorResponse;
import cafe.shop.model.entities.User;
import cafe.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    @Autowired CustomerService customerService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        try {
            User customer = customerService.registerCustomer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } catch (CustomerAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflict", "Customer with this mobile number already exists"));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid input data", e.getMessage()));
        }
    }

    @GetMapping(value = "/score")
    public ResponseEntity<CustomerScoreDto> getCustomerScore() {
        return ResponseEntity.ok(customerService.getCustomerScore());
    }


}
