package com.noway.netutils.net.interceptors;



import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 描述:    TODO  主要用于查看请求信息及返回信息，如链接地址、头信息、参数信息等，参考下面的log-拦截器定义
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class LoggingInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        Logger.e(String.format(Locale.CHINA,"发送请求: %s on %s%n%s", request.url(),
                chain.connection(), request.headers()));


        // TODO: 打印请求参数代码无效
        String method=request.method();
        if("POST".equals(method)){
            String parseParams = parseParams(request.newBuilder().build().body());
            Logger.e("| RequestParams:{"+parseParams.toString()+"}");
        }


        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        ResponseBody responseBody = response.peekBody(1024 * 1024);
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
//            Logger.e(String.format(Locale.CHINA,"接收响应: [%s] %n返回json: %s \t\n in  %.1fms%n%s",
//                    response.message()+"\t"+response.code(),
//                    responseBody.string(),
//                    (t2 - t1) / 1e6d,
//                    response.headers()));
        Logger.e(String.format(Locale.CHINA,"发送请求: %s on %s%n%s \t\n接收响应: [%s] %n返回json: %s \t\n in  %.1fms%n%s",
                request.url(),
                chain.connection(),
                request.headers(),
                response.message()+"\t"+response.code(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));

        return response;
    }

    /**
     * 解析请求服务器的请求参数
     *
     * @param body
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String parseParams(RequestBody body) throws UnsupportedEncodingException {
        MediaType mediaType = body.contentType();
        if (isParseable(mediaType)) {
            try {
                Buffer requestbuffer = new Buffer();
                body.writeTo(requestbuffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "This params isn't parsed";
    }
    /**
     * 是否可以解析
     *
     * @param mediaType
     * @return
     */
    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null) return false;
        return mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }
}
