package pl.com.tenderflex.dao;

import java.time.LocalDate;
import java.util.List;
import pl.com.tenderflex.model.User;

public interface UserRepository {

    User getByEmail(String email);

    void updateLoginDate(Integer userId, LocalDate loginDate);

    List<User> getAll();

}