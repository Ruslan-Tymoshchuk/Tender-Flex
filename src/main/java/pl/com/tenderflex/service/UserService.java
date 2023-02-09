package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.UserDetailsResponse;
import pl.com.tenderflex.model.User;

public interface UserService {

    User getByEmail(String email);

    void updateLoginDate(Integer userId);

    Page<UserDetailsResponse> getAllUsers(Integer currentPage);

}