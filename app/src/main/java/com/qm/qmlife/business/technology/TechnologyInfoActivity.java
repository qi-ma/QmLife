package com.qm.qmlife.business.technology;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TechnologyInfoActivity extends BaseActivity {

    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.iv_break)ImageView ivBreak;
    @BindView(R.id.wv_technology_content)WebView wvTechnologyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_info);
        ButterKnife.bind(this);
        initInclude();

        Intent intent =getIntent();
        String slug=intent.getStringExtra("slug");
        initView(slug);
    }

    private void initView(String slug) {
        WebSettings webSettings = wvTechnologyContent.getSettings();
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wvTechnologyContent.loadUrl(slug);
    }

    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("");
    }
    @OnClick({R.id.iv_break})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_break:
                this.finish();
                break;

        }
    }

    @Override
    public boolean
    onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvTechnologyContent.canGoBack()) {
            wvTechnologyContent.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出H5界面
    }
}
