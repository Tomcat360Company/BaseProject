package com.noway.netutils.net.functions;

import android.content.Context;


import com.noway.netutils.net.exception.ExceptionEngine;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 描述:    TODO  http请求异常捕获
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class HttpResponseFunc <T> implements Function<Throwable, Observable<T>> {

    public static final String TAG = HttpResponseFunc.class.getSimpleName();
    public WeakReference<Context> contextWeakReference;


    public HttpResponseFunc(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {

        //ExceptionEngine为处理异常的驱动器

        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
