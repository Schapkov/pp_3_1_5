package com.andrey.pp_3_1_5.exceptions;


import org.springframework.stereotype.Component;


@Component
public class ExceptionInfo {

    private String info;

    public ExceptionInfo() {
    }

    public ExceptionInfo(String info) {
        this.info = info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
