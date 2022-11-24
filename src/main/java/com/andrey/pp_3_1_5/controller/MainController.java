package com.andrey.pp_3_1_5.controller;


import com.andrey.pp_3_1_5.model.User;
import com.andrey.pp_3_1_5.service.RoleService;
import com.andrey.pp_3_1_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    UserService userService;
    RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {

        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }



    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin-page";
    }


    @GetMapping("/user")
    public String showUser() {

        return "user-page";


    }


    @GetMapping("/info")
    public ResponseEntity<?> getAuthorizedUser() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok().body(user);
    }





}
