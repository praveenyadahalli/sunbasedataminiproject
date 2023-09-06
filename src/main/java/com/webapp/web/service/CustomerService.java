package com.webapp.web.service;


import com.webapp.web.entity.CustomerEntity;
import com.webapp.web.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CustomerService {
    public String authenticateUser(String loginId, String password);
    public List<CustomerResponse> getCustomerList(String accessToken);

    public String createCustomer(String accessToken, CustomerEntity customerEntity);

    public String updateCustomer(String accessToken, String customerId, CustomerEntity customer);

    public String deleteCustomer(String accessToken, String customerId);
}
