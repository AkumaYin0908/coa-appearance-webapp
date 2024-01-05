package com.coa.controller;


import com.coa.exceptions.UserNotFoundException;
import com.coa.model.User;
import com.coa.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String processRegistration(@ModelAttribute("user") User user,
                                      HttpSession httpSession, Model model, RedirectAttributes redirectAttributes, @RequestParam("direction")String direction){

        String userName= user.getUserName();

        //check the database if user already exists
        Optional<User> optionalUser = userService.findByUserName(userName);

        if(optionalUser.isPresent()){
            model.addAttribute("user",user);
          redirectAttributes.addFlashAttribute("registrationError","Username already exists");
           return String.format("redirect:/%s",direction);
        }else{

            //create user account and store to the database
           userService.save(user);
           redirectAttributes.addFlashAttribute("message", "Registration successful!");
           httpSession.setAttribute("user",user);
        }




        return String.format("redirect:/%s",direction);
    }
}
