package com.spring.blog.services;

import com.spring.blog.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto showUser(Integer id);

    List<UserDto> fetchAllUsers();

    void deleteUser(Integer id);
}
