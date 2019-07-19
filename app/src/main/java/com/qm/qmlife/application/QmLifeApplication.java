package com.qm.qmlife.application;

import com.qm.qmlife.util.DateUtil;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import org.litepal.LitePalApplication;

import java.util.Date;

/**
 * Created by syt on 2019/7/1.
 */

public class QmLifeApplication extends LitePalApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        if ("".equals(PrefTool.getString(this, Prefs.MEET_DATE,""))){
            PrefTool.setString(this, Prefs.MEET_DATE, DateUtil.getDateTime(new Date()));
        }
    }
}
