package com.noway.netutils.net.exception;

/**
 * 描述:    TODO  服务器返回的异常类
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class ServerException extends RuntimeException {

    public String code;

    public String message;

    public ServerException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

}
