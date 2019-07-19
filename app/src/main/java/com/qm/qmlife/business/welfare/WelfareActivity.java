package com.qm.qmlife.business.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.MyAdapter;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareActivity extends BaseActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.rv_welfare)RecyclerView rvWelfare;
    @BindView(R.id.srl_pull_refresh)SwipeRefreshLayout srlPullToRefresh;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);
        ButterKnife.bind(this);
        tvTitle.setText("福利");

        initView();
    }

    private void initView() {
        srlPullToRefresh.setRefreshing(true);
        rvWelfare.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
                srlPullToRefresh.setRefreshing(false);
                rvWelfare.setVisibility(View.VISIBLE);
            }
        }, 3000);

        srlPullToRefresh.setColorSchemeResources(R.color.tab_selected_color);
        srlPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWelfareData();
                //具体操作
            }
        });
        //设置瀑布式布局管理器每行3个Item
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvWelfare.setLayoutManager(staggeredGridLayoutManager);
        String type;
        if ("男神".equals(PrefTool.getString(this,Prefs.USER_SEX,""))){
            type="1";
        }else {
            type="2";
        }
        myAdapter = new MyAdapter(this,type);
        myAdapter.setItemClickListener(new MyAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent=new Intent(WelfareActivity.this, PhotoActivity.class);
                intent.putExtra("position",""+position);
                startActivity(intent);
            }
            @Override
            public void onItemLongClickListener(View view) {
            }
        });
        rvWelfare.setAdapter(myAdapter);



    }

    private void refreshWelfareData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
                srlPullToRefresh.setRefreshing(false);
                rvWelfare.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }


}
