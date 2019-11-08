package com.qm.qmlife.business.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.iv_break) ImageView ivBreak;
    @BindView(R.id.tv_title) TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initInclude();
    }
    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("反馈");
    }


    @OnClick({R.id.btn_note_upload,R.id.iv_break})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_note_upload:
                ToastUtil.showToast(this,"后台还没写，哈哈");
                break;
            case R.id.iv_break:
                this.finish();
                break;
        }
    }
}
