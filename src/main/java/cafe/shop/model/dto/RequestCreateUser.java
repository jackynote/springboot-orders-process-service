package cafe.shop.model.dto;

import cafe.shop.model.constant.UserRole;
import lombok.Data;

@Data
public class RequestCreateUser {

    private String username;
    private String password;
    private UserRole role;

}
