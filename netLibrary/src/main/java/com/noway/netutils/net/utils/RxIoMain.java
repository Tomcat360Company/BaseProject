package com.noway.netutils.net.utils;

import android.content.Context;

import com.noway.netutils.net.base.BaseResponse;
import com.noway.netutils.net.functions.HttpResponseFunc;
import com.noway.netutils.net.functions.ServerResponseFunc;
import com.orhanobut.logger.Logger;


import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述:    TODO  线程调度工具
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class RxIoMain {

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())

                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                Logger.e("+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
//                                Logger.e("+++doFinally+++");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> _io_main(final Context context) {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResponse<T>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                        .map(new ServerResponseFunc<T>(context))
                        .onErrorResumeNext(new HttpResponseFunc<T>(context))
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
//                                Logger.e("+++doOnSubscribe+++" + disposable.isDisposed());

                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
//                                Logger.e("+++doFinally+++");
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> _main(final Context context) {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResponse<T>> upstream) {
                return upstream
                        //.observeOn(AndroidSchedulers.mainThread())


                        .map(new ServerResponseFunc<T>(context))
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                Logger.e("+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
//                                Logger.e("+++doFinally+++");
                            }
                        })
                        .onErrorResumeNext(new HttpResponseFunc<T>(context));
            }
        };
    }
}
