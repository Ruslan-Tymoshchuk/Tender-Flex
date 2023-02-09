package pl.com.tenderflex.dao.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.dao.mapper.UserMapper;
import pl.com.tenderflex.model.User;

@Repository
public class UserDao implements UserRepository {

    public static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    public static final String UPDATE_LOGIN_TIME_QUERY = "UPDATE users SET login_date = ? WHERE id = ?";
    public static final String GET_ALL_USERS_QUERY = "SELECT * FROM users LIMIT ? OFFSET ?";
    public static final String COUNT_ALL_USERS_QUERY = "SELECT count(id) FROM users";

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserDao(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_QUERY, userMapper, email);
    }

    @Override
    public void updateLoginDate(Integer userId, LocalDate loginDate) {
        jdbcTemplate.update(UPDATE_LOGIN_TIME_QUERY, loginDate, userId);
    }

    @Override
    public List<User> getAllByCondition(Integer amountUsers, Integer amountUsersToSkip) {
        return jdbcTemplate.query(GET_ALL_USERS_QUERY, userMapper, amountUsers, amountUsersToSkip);
    }

    @Override
    public Integer countAllUsers() {
        return jdbcTemplate.queryForObject(COUNT_ALL_USERS_QUERY, Integer.class);
    }
}