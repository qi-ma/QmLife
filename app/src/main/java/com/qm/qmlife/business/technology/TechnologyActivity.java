package com.qm.qmlife.business.technology;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.TechnologyAdapter;
import com.qm.qmlife.business.model.technologynow.Technology;
import com.qm.qmlife.util.OkhttpUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechnologyActivity extends BaseActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.srl_pull_refresh) SwipeRefreshLayout srlPullToRefresh;
    @BindView(R.id.rv_technology) RecyclerView rvTechnology;

    private List<Technology> technologies = new ArrayList<>();
    private TechnologyAdapter technologyAdapter;
    private Animator animatorEnd;
    private Animator animatorStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);
        ButterKnife.bind(this);
        tvTitle.setText("简书");
        getData();
        initView();
    }

    private void getData() {
        OkhttpUtil.getInstance().doGet(this, "https://www.jianshu.com/asimov/trending/now", new OkhttpUtil.OkCallback() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                try {
                    //转module对象，因为其中有时间所以添加setDateFormat("yyyy-MM-dd HH:mm")方法
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                    List<Technology> technologyList = gson.fromJson(json, new TypeToken<List<Technology>>() {
                    }.getType());

                    technologies.clear();
                    technologies.addAll(technologyList);
                    technologyAdapter.notifyDataSetChanged();
                    playAnimator();

                } catch (Exception e) {
                }
            }
        });
    }

    private void initView() {
        technologyAdapter = new TechnologyAdapter(this, technologies);


        srlPullToRefresh.setColorSchemeResources(R.color.tab_selected_color);
        srlPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWelfareData();
                //具体操作
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTechnology.setLayoutManager(linearLayoutManager);

        rvTechnology.setAdapter(technologyAdapter);


    }

    private void refreshWelfareData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srlPullToRefresh.setRefreshing(false);
                getData();
            }
        }, 500);
    }

    private void playAnimator() {
        //这是动画

        animatorStart = ObjectAnimator.ofFloat(rvTechnology, "alpha", 0);
        //动画时间
        animatorStart.setDuration(0);
        //启动动画
        animatorStart.start();
        //这是动画
        animatorEnd = ObjectAnimator.ofFloat(rvTechnology, "alpha", 1);
        //动画时间
        animatorEnd.setDuration(1000);
        //启动动画
        animatorEnd.start();
    }
}
