package pl.com.tenderflex.dto;

import java.time.LocalDate;
import pl.com.tenderflex.model.Role;

public class UserDetailsResponse {

    private String login;
    private Role role;
    private LocalDate lastLoginDate;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}