package cafe.shop.controller;

import cafe.shop.model.dto.RequestCreateUser;
import cafe.shop.model.entities.User;
import cafe.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody RequestCreateUser requestCreateUser) {
        User user = userService.createUser(requestCreateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
