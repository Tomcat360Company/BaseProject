package com.noway.netutils.net.exception;

/**
 * 描述:    TODO   异常类
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class ApiException extends Exception{

    public String code;

    public String message;


    public ApiException(Throwable throwable, String code) {
        super(throwable);
        this.code = code;

    }

}
