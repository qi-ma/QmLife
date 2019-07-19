package com.qm.qmlife;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.bm.library.PhotoView;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.MenuActivity;
import com.qm.qmlife.manager.AppManager;
import com.qm.qmlife.util.DateUtil;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.pv_img)PhotoView pvImg;
    private ObjectAnimator animator1;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        if (!this.isTaskRoot()) {
            finish();
        }


        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        int hour=DateUtil.getHour(new Date());

        animator = ObjectAnimator.ofFloat( pvImg,"alpha",0);
        //动画时间
        animator.setDuration(0);
        //启动动画
        animator.start();

        if (hour>=4&&hour<12){
            pvImg.setImageResource(R.drawable.morning);
            ToastUtil.showToast(this,"早上好"+ PrefTool.getString(this,Prefs.USER_SEX,""));
            //这是动画
            animator1 = ObjectAnimator.ofFloat( pvImg,"alpha",1);
            //动画时间
            animator1.setDuration(2000);
            //启动动画
            animator1.start();
        }else if (hour>=12&&hour<20){
            pvImg.setImageResource(R.drawable.afternoon);
            //这是动画
            animator1 = ObjectAnimator.ofFloat( pvImg,"alpha",1);
            //动画时间
            animator1.setDuration(2000);
            //启动动画
            animator1.start();
            ToastUtil.showToast(this,"下午好"+ PrefTool.getString(this,Prefs.USER_SEX,""));
        }else {
            pvImg.setImageResource(R.drawable.evening);
            //这是动画
            animator1 = ObjectAnimator.ofFloat( pvImg,"alpha",1);
            //动画时间
            animator1.setDuration(2000);
            //启动动画
            animator1.start();
            ToastUtil.showToast(this,"晚上好"+ PrefTool.getString(this,Prefs.USER_SEX,""));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }, 2000);

    }

    @Override
    protected void onDestroy() {
        animator.cancel();
        animator1.cancel();
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }
}
