package com.webapp.web.controller;

import com.webapp.web.entity.CustomerEntity;
import com.webapp.web.entity.UserIdPasswordEntity;
import com.webapp.web.response.CustomerResponse;
import com.webapp.web.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CustomerController {
    private CustomerService customerService;
    private String authorizationHeader="dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestParam("login_id") String loginId,
                                              @RequestParam("password") String password, HttpServletRequest request) {
        try {
//            String accessToken = customerService.authenticateUser(loginId, password);
            String accessToken="dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";

            if (accessToken != null) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", request.getContextPath() + "/get-customers")
                        .body(null);
            } else {
                return ResponseEntity.badRequest().body("Authentication failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
    @GetMapping("/get-customer")
    public ResponseEntity<List<CustomerResponse>> getCustomerList(@RequestHeader("Authorization") String authorizationHeader) {
        List<CustomerResponse> customerList = customerService.getCustomerList(authorizationHeader);
        return ResponseEntity.ok(customerList);
    }
    @PostMapping("/create-customer")
    public ResponseEntity<String> createCustomer(@RequestHeader(name = "Authorization", defaultValue = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=") String authorizationHeader, @RequestBody CustomerEntity customer) {
        String result = customerService.createCustomer(authorizationHeader, customer);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update-customer")
    public ResponseEntity<String> updateCustomer(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("cmd") String cmd,
            @RequestParam("uuid") String customerId,
            @RequestBody CustomerEntity customer
    ) {
        if ("update".equals(cmd)) {
            String result = customerService.updateCustomer(authorizationHeader, customerId, customer);
            return ResponseEntity.ok(result);
        } else if ("delete".equals(cmd)) {
            String result = customerService.deleteCustomer(authorizationHeader, customerId);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body("Invalid 'cmd' parameter.");
        }
    }

    @PostMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomer(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("cmd") String cmd,
            @RequestParam("uuid") String customerId
    ) {
        if ("delete".equals(cmd)) {
            String result = customerService.deleteCustomer(authorizationHeader, customerId);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body("Invalid 'cmd' parameter.");
        }
    }

}