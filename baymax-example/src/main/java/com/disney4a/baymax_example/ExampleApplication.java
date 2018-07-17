package com.disney4a.baymax_example;

import com.disney4a.baymax.annotations.Tag_Application;
import com.disney4a.baymax.core.app.application.BaymaxApplication;
import com.disney4a.baymax.core.app.application.Baymax;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

@Tag_Application(annotationsPackages = {"com.disney4a.baymax_example.app"})
public class ExampleApplication extends BaymaxApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 数据库支持
        LitePal.initialize(this);
    }
}
