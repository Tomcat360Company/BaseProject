/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.noway.netutils.net.subscribers;

import android.content.Context;
import android.util.Log;

import com.noway.netutils.net.exception.ApiException;
import com.noway.netutils.utils.NetworkUtils;


import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;




/**
 * <p>描述：订阅的基类</p>
 * 1.可以防止内存泄露。<br>
 * 2.在onStart()没有网络时直接onCompleted();<br>
 * 3.统一处理了异常<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/20 10:35<br>
 * 版本： v2.0<br>
 */

/**

 * 描述:  TODO 订阅的基类
 *       TODO   1.可以防止内存泄露
 *       TODO   2.在onStart()没有网络时直接onCompleted()
 *       TODO   3.统一处理了异常
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public static final String TAG = BaseSubscriber.class.getSimpleName();
    public WeakReference<Context> contextWeakReference;


    public BaseSubscriber(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }



    public BaseSubscriber() {
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"-->http is onStart");
        if (contextWeakReference != null && contextWeakReference.get() != null &&
                !NetworkUtils.isNetworkAvailable(contextWeakReference.get())) {
            //Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }
    }


    @Override
    public void onNext(@NonNull T t) {
        Log.e(TAG,"-->http is onNext");
    }

    @Override
    public final void onError(Throwable e) {
        Log.e(TAG,"-->http is onError");
        if (e instanceof ApiException) {
            Log.e(TAG,"--> e instanceof ApiException err:" + e);
            onError((ApiException) e);
        } else {
            Log.e(TAG,"--> e !instanceof ApiException err:" + e);
            onError(e);
        }
    }

    @Override
    public void onComplete() {
        Log.e(TAG,"-->http is onComplete");
    }


    public abstract void onError(ApiException e);

}
