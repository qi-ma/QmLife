package com.qm.qmlife.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qm.qmlife.manager.AppManager;

/**
 * Created by syt on 2019/7/1.
 */

public class BaseActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载日志管理、Activity管理、开发便捷
        Logger.addLogAdapter(new AndroidLogAdapter());
        AppManager.getInstance().addActivity(this);


    }

}
