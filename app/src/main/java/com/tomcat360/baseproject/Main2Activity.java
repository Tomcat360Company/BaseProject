package com.tomcat360.baseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noway.netutils.net.RxService;
import com.noway.netutils.net.subscribers.NetCheckerSubscriber;
import com.noway.netutils.net.utils.RxIoMain;
import com.orhanobut.logger.Logger;
import com.tomcat360.baseproject.api.TestApi;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
    }

    private void initData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", 1 + "");

        RxService.createApi(TestApi.class).getData(map)
                .compose(RxIoMain._io_main(this))

                .subscribe(new NetCheckerSubscriber<Object>(this) {
                    @Override
                    protected void onSuccess(Object o) {
                        Logger.e("1成功");
                    }

                    @Override
                    protected void onFailed(String message) {
                        Logger.e("2失败:" + message);
                    }

                    @Override
                    protected void onFinish() {
                        Logger.e("3完成");
                    }
                });
    }


}
