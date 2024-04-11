package com.spring.blog.services.impl;

import com.spring.blog.entities.User;
import com.spring.blog.exceptions.ResourceNotFoundException;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.repositories.UserRepository;
import com.spring.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createNewUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User saved = this.userRepository.save(user);

        return this.userToDto(saved);
    }
    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = this.userRepository.findById(id)
                .orElseThrow((()-> new ResourceNotFoundException("user", "userId", id)));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User userUpdated = this.userRepository.save(user);
        UserDto userDtoUpdated = this.userToDto(userUpdated);
        return userDtoUpdated;
    }

    @Override
    public UserDto showUser(Integer id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        UserDto userDto = this.userToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> usersList =  users.stream().map(this::userToDto).collect(Collectors.toList());
        return usersList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }
}
