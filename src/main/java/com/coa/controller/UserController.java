package com.coa.controller;


import com.coa.model.Role;
import com.coa.model.User;
import com.coa.repository.RoleRepository;
import com.coa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class UserController {


    private final UserService userService;
    private final RoleRepository roleRepository;



    @Value("${pageNums}")
    private List<Integer> pageNums;


    @GetMapping("/users")
    public String showUsers(Model model, @RequestParam(required = false) String searchName,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "userId,asc") String[] sort){

        try{


            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort.Order order=new Sort.Order(direction,sortField);

            Pageable pageable = PageRequest.of(page-1,size,Sort.by(order));

            Page<User> userPage;
            if (searchName == null || searchName.isEmpty()) {
                userPage = userService.findAll(pageable);
            }
            else {
                userPage=userService.findByNameContainingIgnoreCase(searchName,pageable);
                model.addAttribute("searchName",searchName);
            }

            List<User> users = userPage.getContent();
            List<Role> roles=roleRepository.findAll();

            model.addAttribute("user", new User());
            model.addAttribute("users",users);
            model.addAttribute("roles",roles);
            model.addAttribute("currentPage", userPage.getNumber() + 1);
            model.addAttribute("totalPages",userPage.getTotalPages());
            model.addAttribute("pageSize",size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("pageNums", pageNums);
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
        }

        return "settings/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
            Optional<User> optionalUser = userService.findById(id);
            User user;
            if(optionalUser.isPresent()){
                user=optionalUser.get();

                String userFullName = user.getName();

                user.getRoles().clear();
                userService.deleteById(id);
                redirectAttributes.addFlashAttribute("message", userFullName + " has been deleted.");
            }


        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/settings/users";
       
    }

}
