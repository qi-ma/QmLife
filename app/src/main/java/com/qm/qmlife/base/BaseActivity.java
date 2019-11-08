package com.qm.qmlife.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qm.qmlife.R;
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
        this.overridePendingTransition(R.anim.activity_anim_one,R.anim.activity_anim_two);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
