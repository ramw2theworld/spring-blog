package com.spring.blog.controllers;

import com.spring.blog.payloads.ApiResponse;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDto userDto){
        UserDto createNewUserDto = this.userService.createNewUser(userDto);
        return new ResponseEntity<>(createNewUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> fetchAllUsers(){
        List<UserDto> users = this.userService.fetchAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> showUser(@PathVariable("userId") int userId){
        UserDto userDto = this.userService.showUser(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") int userId){
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

}
