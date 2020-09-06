package application.controller;

import application.dto.RegistrationUserDto;
import application.mapper.Mapper;
import application.model.security.User;
import application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Mapper<RegistrationUserDto, User> mapper;



}