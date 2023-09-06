package com.webapp.web.controller;

import com.webapp.web.entity.CustomerEntity;
import com.webapp.web.entity.UserIdPasswordEntity;
import com.webapp.web.response.CustomerResponse;
import com.webapp.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(@RequestBody UserIdPasswordEntity request) {
        String accessToken = customerService.authenticateUser(request.getLogin_id(), request.getPassword());
        if (accessToken != null) {
            return ResponseEntity.ok("Authentication successful. Access Token: " + accessToken);
        } else {
            return ResponseEntity.badRequest().body("Authentication failed.");
        }
    }
    @GetMapping("/get-customer")
    public ResponseEntity<List<CustomerResponse>> getCustomerList(@RequestHeader("Authorization") String authorizationHeader) {
        List<CustomerResponse> customerList = customerService.getCustomerList(authorizationHeader);
        return ResponseEntity.ok(customerList);
    }
    @PostMapping("/create-customer")
    public ResponseEntity<String> createCustomer(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CustomerEntity customer) {
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