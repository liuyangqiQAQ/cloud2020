package com.qizi.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComResult <T>{

    private Integer Code;

    private String message;

    private T data;

    public ComResult(Integer code, String message) {
        Code = code;
        this.message = message;
    }

}
