package com.coa.controller;


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
        User existingUser=userService.findByUserName(userName);

        if(existingUser!=null){
            model.addAttribute("user",new User());
            model.addAttribute("registrationError","Username already exists");
            return "registration";
        }

        //create user account and store to the database
        userService.save(user);

        httpSession.setAttribute("user",user);
        return "login";
    }
}
