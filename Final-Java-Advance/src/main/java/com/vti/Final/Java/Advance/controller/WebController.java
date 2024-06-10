package com.vti.Final.Java.Advance.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
//    @GetMapping(value = "/login")
//    public String loginPage(){
//        return "login";
//    }
    @GetMapping(value = {"/", "/admin"})
    public String homepage() {
        return "admin"; // Trả về admin.html
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "hello"; // Trả về hello.html
    }
}
