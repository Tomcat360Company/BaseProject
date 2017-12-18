package com.noway.netutils.net.exception;


/**
 * 描述:    TODO   错误类
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */

public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = UNKNOWN + 1;
    /**
     * 网络错误
     */
    public static final int NET_WORD_ERROR = PARSE_ERROR + 1;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = NET_WORD_ERROR + 1;

    /**
     * 证书出错
     */
    public static final int SSL_ERROR = HTTP_ERROR + 1;

    /**
     * 连接超时
     */
    public static final int TIMEOUT_ERROR = SSL_ERROR + 1;

    /**
     * 调用错误  —— TODO
     */
    public static final int INVOKE_ERROR = TIMEOUT_ERROR + 1;
    /**
     * 类转换错误
     */
    public static final int CAST_ERROR = INVOKE_ERROR + 1;
    /**
     * 请求取消  —— TODO
     */
    public static final int REQUEST_CANCEL = CAST_ERROR + 1;
    /**
     * 未知主机错误
     */
    public static final int UNKNOWN_HOST_ERROR = REQUEST_CANCEL + 1;

    /**
     * 空指针错误
     */
    public static final int NULL_POINTER_EXCEPTION = UNKNOWN_HOST_ERROR + 1;
}
