package cafe.shop.controller;

import cafe.shop.model.dto.RequestCreateAdminUser;
import cafe.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping
    ResponseEntity<?> createAdminUser(@RequestBody RequestCreateAdminUser requestCreateAdminUser) {
        userService.createAdminUser(requestCreateAdminUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
