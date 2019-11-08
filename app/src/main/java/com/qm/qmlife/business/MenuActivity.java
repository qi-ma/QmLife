package com.qm.qmlife.business;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity{
    // 记录两次退出(是否需要退出)
    boolean mFlag = false;
    // 点两次返回键，退出，，时间间隔
    private static final int QUIT_INTERVAL = 2000;
    private LocalActivityManager manager;
    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    float xDown = 0,xMove,yDown = 0,yMove;

    @BindView(R.id.vp_content)ViewPager vpContent;
    @BindView(R.id.bbl)BottomBarLayout bottomBarLayout;
    @BindView(R.id.dl_menu_drawer)DrawerLayout dlMenuDrawer;
    @BindView(R.id.nv_menu_navigation)NavigationView nvMenuNavigation;
    @BindView(R.id.fab_reward)FloatingActionButton fabReward;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        createVelocityTracker(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = ev.getRawX();
                yDown = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = ev.getRawX();
                yMove= ev.getRawY();
                //滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY= (int) (yMove - yDown);

                //获取顺时速度
//                int ySpeed = getScrollVelocity();
                //关闭Activity需满足以下条件：
                //1.x轴滑动的距离>XDISTANCE_MIN
                //2.y轴滑动的距离在YDISTANCE_MIN范围内
                //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity

//                Log.d("tag",""+ySpeed);

                if(distanceX > 50 &&(distanceY<100&&distanceY>-100)) {
                    dlMenuDrawer.openDrawer(nvMenuNavigation);
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }

    @OnClick({R.id.fab_reward})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_reward:
                final String[] items3 = new String[]{"微信", "支付宝", "银联"};//创建item

                AlertDialog alertDialog3 = new AlertDialog.Builder(this)
                        .setTitle("选择您打赏作者的支付方式")
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if ("支付宝".equals(items3[i])){
                                    final String orderInfo = "";   // 订单信息

                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(MenuActivity.this);
                                            Map<String,String> result = alipay.payV2(orderInfo,true);

//                                            Message msg = new Message();
//                                            msg.what = SDK_PAY_FLAG;
//                                            msg.obj = result;
//                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    // 必须异步调用
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();
                                }
                            }
                        })
                        .create();
                alertDialog3.show();
                break;
        }
    }


}
