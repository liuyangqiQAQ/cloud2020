package com.qizi.springcloud.service;


import com.qizi.springcloud.dao.PaymentDao;
import com.qizi.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl {

    @Autowired
    private PaymentDao paymentDao;


    public int create(Payment payment) {
        return paymentDao.insert(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentDao.selectById(id);
    }

}
