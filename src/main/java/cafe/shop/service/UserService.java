package cafe.shop.service;

import cafe.shop.model.dto.RequestCreateAdminUser;
import cafe.shop.model.dto.RequestCreateUser;
import cafe.shop.model.entities.User;

public interface UserService {

    void createAdminUser(RequestCreateAdminUser requestCreateAdminUser);

    User createUser(RequestCreateUser request);
}
