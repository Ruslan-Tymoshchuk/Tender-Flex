package pl.com.tenderflex.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.UserDetailsResponse;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.exception.UserNotFoundException;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${users.per.page}")
    private Integer usersPerPage;
    private final UserRepository userRepository;
    private final MapStructMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, MapStructMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User getByEmail(String email) {
        try {
            return userRepository.getByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("The user with that email is not exists", e);
        } catch (DataAccessException e) {
            throw new ServiceException("Dao error occurred", e);
        }
    }

    @Override
    public void updateLoginDate(Integer userId) {
        try {
            userRepository.updateLoginDate(userId, LocalDate.now());
        } catch (DataAccessException e) {
            throw new ServiceException("Dao error occurred when updating login date", e);
        }
    }

    @Override
    public Page<UserDetailsResponse> getAllUsers(Integer currentPage) {
        Integer amountUsers = currentPage * usersPerPage;
        Integer amountUsersToSkip = (currentPage - 1) * 5;
        Integer allUsersAmount = userRepository.countAllUsers();
        Integer totalPages = 1;
        if (allUsersAmount >= usersPerPage) {
            totalPages = allUsersAmount / usersPerPage;
            if (allUsersAmount % usersPerPage > 0) {
                totalPages++;
            }
        }
        try {
            return new Page<>(currentPage, totalPages, userRepository.getAllByCondition(amountUsers, amountUsersToSkip)
                    .stream().map(userMapper::userToUserDetailsResponse).toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Dao error occurred when getting all users", e);
        }
    }
}