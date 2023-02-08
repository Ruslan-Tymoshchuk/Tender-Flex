package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.dto.UserDetailsResponse;
import pl.com.tenderflex.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdministratorController {

    private final UserService userService;

    public AdministratorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all_users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetailsResponse> getAllByCondition() {
        return userService.getAllUsers();
    }
}