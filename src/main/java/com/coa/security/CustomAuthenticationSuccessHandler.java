package com.coa.security;


import com.coa.exceptions.UserNotFoundException;
import com.coa.model.User;
import com.coa.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try{
            String userName=authentication.getName();
            Optional<User> optionalUser = userService.findByUserName(userName);
            User user;

            if(optionalUser.isPresent()){
                user=optionalUser.get();
            }else{
                throw new UserNotFoundException("User not found!");
            }

            HttpSession session=request.getSession();
            session.setAttribute("user",user);

            response.sendRedirect(request.getContextPath() + "/dashboard");
        }catch(Exception ex){
            throw new ServletException("Authentication success handling failed", ex);
        }


    }
}
