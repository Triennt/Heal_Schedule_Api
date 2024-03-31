package com.asm3.HealScheduleApp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String yourMethod(HttpServletRequest request, Model model) {

        model.addAttribute("contextPath", request.getServerName());

        return "introduce";
    }
}
