package application.controller;

import application.dto.RegistrationUserDto;
import application.mapper.Mapper;
import application.model.security.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<RegistrationUserDto, User> mapper;

    @PostMapping("/registration")
    public String register(@ModelAttribute RegistrationUserDto registrationUserDto) {
        User user = mapper.fromDto(registrationUserDto);
        if (userService.isExist(user)) return "redirect:/registration?error";

        userService.create(user);

        System.out.println("Action: registration");
        System.out.println("Username: " + registrationUserDto.getUsername());
        System.out.println("Time: " + LocalDateTime.now().toString().replace("T", " "));
        System.out.println();

        return "redirect:/login";
    }

}