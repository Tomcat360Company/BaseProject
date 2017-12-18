package com.noway.netutils.net.subscribers;

import android.content.Context;
import android.widget.Toast;


import com.noway.netutils.net.exception.ApiException;
import com.noway.netutils.net.progress.ProgressCancelListener;
import com.noway.netutils.net.progress.SimpleLoadDialog;
import com.noway.netutils.utils.NetworkUtils;

import io.reactivex.observers.DefaultObserver;


/**

 * 描述:    TODO  带进度条的订阅
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */

public abstract class NetCheckerSubscriber<T> extends DefaultObserver<T> implements ProgressCancelListener {

    private Context context;

    private SimpleLoadDialog dialogHandler;

    public NetCheckerSubscriber(Context context) {
        this.context = context;
        dialogHandler = new SimpleLoadDialog(context,this,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isNetworkAvailable(context)) {
            this.cancel();
            Toast.makeText(context, "请检查网络连接后重试!", Toast.LENGTH_SHORT).show();
        }else {
            showProgressDialog();
        }
    }

    @Override
    public void onNext(T t) {
        dismissProgressDialog();

        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        dismissProgressDialog();

        if (e instanceof ApiException) {
            onFailed(((ApiException) e).message);
        } else {
            onFailed("请求失败，请稍后再试...");
        }
        onFinish();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();

        onFinish();
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog(){

        if (dialogHandler != null) {
            dialogHandler.show();
        }

    }
    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialogHandler != null) {
            dialogHandler.dismiss();
            dialogHandler = null;
        }
    }
    @Override
    public void onCancelProgress() {
       this.cancel();
    }


    protected abstract void onSuccess(T t);
    protected abstract void onFailed(String message);
    protected abstract void onFinish();

}