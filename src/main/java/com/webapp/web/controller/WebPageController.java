package com.webapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/get-customers")
    public String getAllCustomers() {
        return "get-all-customer";
    }
    @GetMapping("/add-new-customer")
    public String addNewCustomer() {
        return "add-new-customer";
    }
}
