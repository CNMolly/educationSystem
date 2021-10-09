package com.moli.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class selfException extends RuntimeException{

    private Integer code;

    private String msg;
}
