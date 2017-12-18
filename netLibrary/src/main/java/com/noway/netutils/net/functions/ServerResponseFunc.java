package com.noway.netutils.net.functions;

import android.content.Context;


import com.noway.netutils.net.base.BaseResponse;
import com.noway.netutils.net.exception.ServerException;

import java.lang.ref.WeakReference;

import io.reactivex.functions.Function;

/**
 * 描述:    TODO  请求数据处理
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class ServerResponseFunc<T> implements Function<BaseResponse<T>, T> {

    public static final String TAG = ServerResponseFunc.class.getSimpleName();
    public WeakReference<Context> contextWeakReference;


    public ServerResponseFunc(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public T apply(BaseResponse<T> httpResponse) throws Exception {

        //对返回码进行判断，如果不是000000，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常
        if (!httpResponse.isSuccess()) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(httpResponse.getCode() + "", httpResponse.getMsg());
        }
        return httpResponse.getData();
    }
}
