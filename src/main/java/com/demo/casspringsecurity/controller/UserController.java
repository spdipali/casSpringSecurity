package com.demo.casspringsecurity.controller;

import com.demo.casspringsecurity.dto.UserDTO;
import com.demo.casspringsecurity.entity.User;
import com.demo.casspringsecurity.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<Boolean> add(@RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        userService.addUser(user);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }


}
