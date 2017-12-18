package com.noway.netutils.net.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 描述:    TODO   token 拦截器
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
//        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//            final StringBuffer cookieBuffer = new StringBuffer();
//            //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
//            Observable.from(originalResponse.headers("Set-Cookie"))
//                    .map(new Func1<String, String>() {
//                        @Override
//                        public String call(String s) {
//                            String[] cookieArray = s.split(";");
//                            return cookieArray[0];
//                        }
//                    })
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String cookie) {
//                            cookieBuffer.append(cookie).append(";");
//                        }
//                    });
//				SPUtils.put(App.getInstance(), G.SP_COOKIE,cookieBuffer.toString());
//                Logger.e("turn_session",cookieBuffer.toString());
//                if(cookieBuffer.toString().contains("JSESSIONID=")){
//                    Constant.COOKIE = cookieBuffer.toString();
//                }
//        }

        return originalResponse;
    }
}
