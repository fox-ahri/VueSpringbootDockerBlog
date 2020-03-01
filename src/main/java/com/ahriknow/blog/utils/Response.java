package com.ahriknow.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {
    private boolean success;
    private Integer code;
    private String msg;
    private Object data;
}
