package com.tomcat360.baseproject.api;


import com.noway.netutils.net.base.BaseResponse;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * 包名:    com.tomcat360.baseproject.api
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */

public interface TestApi {



    @POST("/api/noauth/index")
    Observable<BaseResponse<Object>> getData(@QueryMap Map<String,String> map);


}
