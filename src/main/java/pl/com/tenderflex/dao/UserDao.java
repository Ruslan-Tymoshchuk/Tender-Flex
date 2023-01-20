package pl.com.tenderflex.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.dao.mapper.UserMapper;
import pl.com.tenderflex.model.User;

@Repository
public class UserDao {

    public static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    
    public UserDao(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }
    
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_QUERY, userMapper, email);
    }
}