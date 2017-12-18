package com.noway.netutils.net.exception;


import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 描述:    TODO   异常捕获处理类
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */

public class ExceptionEngine {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, String.valueOf(ERROR.HTTP_ERROR));
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) { //后台服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof JsonSyntaxException ||
                e instanceof JsonSerializer ||
                e instanceof NotSerializableException ||
                e instanceof ParseException) {
            ex = new ApiException(e, String.valueOf(ERROR.PARSE_ERROR));
            ex.message = "解析错误"; //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, String.valueOf(ERROR.NET_WORD_ERROR));
            ex.message = "连接失败";  //均视为连接失败
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, String.valueOf(ERROR.TIMEOUT_ERROR));
            ex.message = "连接超时";  //均视为连接时间为60s
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, String.valueOf(ERROR.CAST_ERROR));
            ex.message = "类型转换错误";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, String.valueOf(ERROR.SSL_ERROR));
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, String.valueOf(ERROR.UNKNOWN_HOST_ERROR));
            ex.message = "无法解析该域名";
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, String.valueOf(ERROR.NULL_POINTER_EXCEPTION));
            ex.message = "NullPointerException";
            return ex;
        } else {
            ex = new ApiException(e, String.valueOf(ERROR.UNKNOWN));
            ex.message = "未知错误";  //未知错误
            return ex;
        }
    }
}
