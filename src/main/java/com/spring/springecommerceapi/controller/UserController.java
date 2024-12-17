package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.User;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiRequest<List<User>>> getAllUser() {
        List<User> users = userService.getAllUsers();
        return createApiResponse(ErrorCode.SUCCESS,users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiRequest<User>> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        return createApiResponse(ErrorCode.SUCCESS,user);
    }
    @PostMapping
    public ResponseEntity<ApiRequest<User>> createUser(@RequestBody User user) {
        User saveUser = userService.createUser(user);
        return createApiResponse(ErrorCode.SUCCESS,saveUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRequest<String>> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return createApiResponse(ErrorCode.DELETE_SUCCESS, null);
    }
}
