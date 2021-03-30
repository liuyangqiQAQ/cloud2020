package com.qizi.springcloud.controller;

import com.qizi.springcloud.entities.ComResult;
import com.qizi.springcloud.entities.Payment;
import com.qizi.springcloud.service.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {


    @Autowired
    PaymentServiceImpl paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public ComResult<Integer> create(@RequestBody Payment payment){
        int row = paymentService.create(payment);
        log.info("***插入结果:" + row);
        if(row > 0) {
            return new ComResult(200, "成功,serverPort:" + serverPort, row);
        } else {
            return new ComResult(444, "失败,serverPort:" + serverPort);
        }
    }

    @GetMapping("/payment/get/{id}")
    public ComResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("***查询结果:" + payment + "\t" + "嘻嘻嘻~~~");
        if(payment == null) {
            return new ComResult(444, "失败,serverPort:" + serverPort);
        } else {
            return new ComResult<>(200, "成功,serverPort:" + serverPort, payment);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {

        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element:{}", element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return discoveryClient;
    }

    @GetMapping("/payment/getPort")
    public String getPort() {
        return serverPort;
    }


}
