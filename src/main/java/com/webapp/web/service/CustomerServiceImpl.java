package com.webapp.web.service;

import com.webapp.web.entity.CustomerEntity;
import com.webapp.web.entity.UserIdPasswordEntity;
import com.webapp.web.response.AuthResponse;
import com.webapp.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${external.api.baseurl}")
    private String externalApiBaseUrl;

    @Override
    public String authenticateUser(String loginId, String password) {
        String apiUrl = externalApiBaseUrl + "/assignment_auth.jsp";
        UserIdPasswordEntity request = new UserIdPasswordEntity(loginId, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserIdPasswordEntity> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                AuthResponse.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            AuthResponse response = responseEntity.getBody();
            if (response != null && response.getAccess_token() != null) {
                return response.getAccess_token();
            }
        }

        return null;
    }

    @Override
    public List<CustomerResponse> getCustomerList(String accessToken) {
        String apiUrl = externalApiBaseUrl + "/assignment.jsp?cmd=get_customer_list";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<CustomerResponse>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<CustomerResponse>>() {}
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle the error scenario
            return Collections.emptyList(); // Return an empty list or handle it differently
        }
    }

    @Override
    public String createCustomer(String accessToken, CustomerEntity customer) {
        String apiUrl = externalApiBaseUrl + "/assignment.jsp?cmd=create";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerEntity> requestEntity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "Customer created successfully";
        } else {
            // Handle the error scenario
            return "Customer creation failed. HTTP error: " + responseEntity.getStatusCode();
        }
    }

    @Override
    public String updateCustomer(String accessToken, String customerId, CustomerEntity customer) {
        String apiUrl = externalApiBaseUrl + "/assignment.jsp?cmd=update&uuid=" + customerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerEntity> requestEntity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "Customer updated successfully";
        } else {
            return "Customer update failed. HTTP error: " + responseEntity.getStatusCode();
        }
    }

    @Override
    public String deleteCustomer(String accessToken, String customerId) {
        String apiUrl = externalApiBaseUrl + "/assignment.jsp?cmd=delete&uuid=" + customerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "Customer deleted successfully";
        } else {
            return "Customer deletion failed. HTTP error: " + responseEntity.getStatusCode();
        }
    }
}
