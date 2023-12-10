package com.coa.controller;


import com.coa.exceptions.UserNotFoundException;
import com.coa.model.User;
import com.coa.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegisterController {


    private final UserService userService;


    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());


        return "registration";
    }


    @PostMapping("/register/processRegistration")
    public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                      HttpSession httpSession, Model model){

        String userName= user.getUserName();


        //form validation
        if(bindingResult.hasErrors()){
            return "registration";
        }

        //check the database if user already exists
        Optional<User> optionalUser = userService.findByUserName(userName);

        if(optionalUser.isPresent()){
            model.addAttribute("user",user);
            model.addAttribute("registrationError","Username already exists");
            return "registration";
        }else{

            //create user account and store to the database
            userService.save(user);
            model.addAttribute("registrationSuccessful", "Registration successful!");

            httpSession.setAttribute("user",user);
        }




        return "login";
    }
}
