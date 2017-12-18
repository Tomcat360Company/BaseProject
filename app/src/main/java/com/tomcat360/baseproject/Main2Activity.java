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

    }

}
