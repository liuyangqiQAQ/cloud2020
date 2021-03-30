package com.qizi.springcloud.controller;

import com.qizi.springcloud.entities.ComResult;
import com.qizi.springcloud.entities.Payment;
import com.qizi.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public LoadBalancer myLB;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public ComResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, ComResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public ComResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, ComResult.class);
    }

    @GetMapping("/consumer/payment/create2")
    public ComResult<Payment> create2(Payment payment) {
        ResponseEntity<ComResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, ComResult.class);
        if(entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new ComResult<>(444, "失败");
        }
    }

    @GetMapping("/consumer/payment/getPort")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(CollectionUtils.isEmpty(instances)) {
            return null;
        }
        ServiceInstance instance = myLB.instances(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/getPort", String.class);
    }

}
