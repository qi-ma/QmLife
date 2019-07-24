package com.qm.qmlife.business;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.library.BottomBarLayout;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.MyViewPagerAdapter;
import com.qm.qmlife.business.scan.ScanActivity;
import com.qm.qmlife.business.technology.TechnologyActivity;
import com.qm.qmlife.business.user.UserActivity;
import com.qm.qmlife.business.weather.WeatherActivity;
import com.qm.qmlife.business.welfare.WelfareActivity;
import com.qm.qmlife.custom.CircleImageView;
import com.qm.qmlife.util.DateUtil;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends BaseActivity {
    // 记录两次退出(是否需要退出)
    boolean mFlag = false;
    // 点两次返回键，退出，，时间间隔
    private static final int QUIT_INTERVAL = 2000;
    private LocalActivityManager manager;

    @BindView(R.id.vp_content)ViewPager vpContent;
    @BindView(R.id.bbl)BottomBarLayout bottomBarLayout;
    @BindView(R.id.dl_menu_drawer)DrawerLayout dlMenuDrawer;
    @BindView(R.id.nv_menu_navigation)NavigationView nvMenuNavigation;
//    private UpgradeInfo upgradeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);


        initPager(0);
        initNavigation();
        Bugly.init(getApplicationContext(), "4e48c2a5cd", true);
    }

    private void initNavigation() {
        if (nvMenuNavigation!=null){
            nvMenuNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setCheckable(true);
                    String title=item.getTitle().toString();
                    if (title.equals("我们的相遇")){
                        ToastUtil.showToast(MenuActivity.this,
                                PrefTool.getString(MenuActivity.this, Prefs.USER_SEX,"")+
                                "我们相遇了"+DateUtil.differentDaysByMillisecond(
                                        PrefTool.getString(MenuActivity.this, Prefs.MEET_DATE,""),
                                        DateUtil.getDate(new java.util.Date())
                                )+"天");
                    }else if (title.equals("设置")){
                        ToastUtil.showToast(MenuActivity.this,"还未开发");

                    }else if (title.equals("关于作者")){
                        ToastUtil.showToast(MenuActivity.this,"百度“齐码闯天涯”QQ2714730493");
                    }else if (title.equals("检查更新")){
                        try {
                            int getCode= Beta.getUpgradeInfo().versionCode;
                            Beta.checkUpgrade(false,false);
                        }catch (Exception e){
                            ToastUtil.showToast(MenuActivity.this,"以是最新版本");
                        }

                    }else if (title.equals("扫描")){
                        Intent intentScan=new Intent(MenuActivity.this, ScanActivity.class);
                        startActivity(intentScan);
                    }

                    //关闭导航菜单
                    dlMenuDrawer.closeDrawers();
                    return true;
                }
            });
        }
        View headerView = nvMenuNavigation.getHeaderView(0);
        TextView tvNhText = (TextView)headerView.findViewById(R.id.tv_nh_text);
        CircleImageView civNhImg=(CircleImageView)headerView.findViewById(R.id.civ_nh_img);
        if ("".equals(PrefTool.getString(this, Prefs.USER_SEX,""))){
            tvNhText.setText("哇~~   你好");
        }else {
            tvNhText.setText("哇~~   "+PrefTool.getString(this, Prefs.USER_SEX,""));
            if (PrefTool.getString(this, Prefs.USER_SEX,"").equals("男神")){
                civNhImg.setImageResource(R.drawable.woman);
            }else {
                civNhImg.setImageResource(R.drawable.man);
            }
        }
    }

    private void initPager(Integer integer) {
        addActivitiesToViewPager();
        vpContent.setCurrentItem(integer);
        bottomBarLayout.setViewPager(vpContent);
    }

    private void addActivitiesToViewPager() {
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();


        intent.setClass(this, WelfareActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("tab1", intent));

        intent.setClass(this, WeatherActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("tab2", intent));

        intent.setClass(this, TechnologyActivity.class);
        intent.putExtra("id", 3);
        mViews.add(getView("tab3", intent));

        intent.setClass(this, UserActivity.class);
        intent.putExtra("id", 4);
        mViews.add(getView("tab4", intent));
        vpContent.setAdapter(new MyViewPagerAdapter(mViews));
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mFlag) {
                Toast.makeText(this, "再次点击返回键退出系统", Toast.LENGTH_SHORT).show();
                // 2秒内再次点击返回键，退出程序
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 2秒内没有点击返回键，，则初始化标志位
                        mFlag = false;
                    }
                }, QUIT_INTERVAL);
                // 记录，再次点击返回键就退出程序
                mFlag = true;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    public static int getVersionCode(Context context)//获取版本号(内部识别号)
//    {
//        try {
//            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            return pi.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return 0;
//        }
//    }
}
