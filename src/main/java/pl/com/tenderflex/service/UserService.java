package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.dto.UserDetailsResponse;
import pl.com.tenderflex.model.User;

public interface UserService {

    User getByEmail(String email);

    void updateLoginDate(Integer userId);

    List<UserDetailsResponse> getAllUsers();

}