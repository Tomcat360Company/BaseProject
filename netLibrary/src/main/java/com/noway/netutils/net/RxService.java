package com.noway.netutils.net;



import com.noway.netutils.net.interceptors.BaseUrlInterceptor;
import com.noway.netutils.net.interceptors.LoggingInterceptor;
import com.noway.netutils.net.interceptors.TokenInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
@Singleton
public class RxService {


    private static final String BASE_URL = "https://api.zhaoyunlicai.com";



    private RxService(){

    }

    private static Retrofit retrofit ;



    private static Retrofit getInstance() {
        if(null== retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }
    public static <T> T createApi(Class<T> cls) {

        return getInstance().create(cls);
    }


    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder
                //并设置超时时间
                .connectTimeout(20, TimeUnit.SECONDS)

                // 对所有请求添加请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

//                        Logger.e(Config.TAG, "url: "+chain.request().url() );

                        return chain.proceed(chain.request() // originalRequest
                                .newBuilder()
                                //默认使用json文本传输
//                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                //其他的头自行添加
                                .addHeader("Cookie", "")
                                .addHeader("token", "")
                                .build());
                    }
                })
                //拦截器中更换不同的base_url
                .addInterceptor(new BaseUrlInterceptor())
                //拦截器中增加了Token
                .addInterceptor(new TokenInterceptor())
                //拦截器中打印请求参数
                .addInterceptor(new LoggingInterceptor());
        return builder.build();
    }


}
