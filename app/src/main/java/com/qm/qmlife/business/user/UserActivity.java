package com.qm.qmlife.business.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.custom.CircleImageView;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_user_name) TextView tvUserName;
    @BindView(R.id.civ_user_img)CircleImageView civUserImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        tvTitle.setText("我的");
        initView();

    }
    private void initView() {
        if ("".equals(PrefTool.getString(this, Prefs.USER_NAME,""))){
            tvUserName.setText("请登录");
        }else {
            tvUserName.setText(PrefTool.getString(this, Prefs.USER_NAME,""));
            if (PrefTool.getString(this, Prefs.USER_SEX,"").equals("男神")){
                civUserImg.setImageResource(R.drawable.man);
            }else {
                civUserImg.setImageResource(R.drawable.woman);
            }
        }
    }

    @OnClick({R.id.civ_user_img,R.id.tv_user_name,R.id.ll_user_update,R.id.ll_user_note})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.civ_user_img:

                break;
            case R.id.tv_user_name:

                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_user_update:
                ToastUtil.showToast(this,"还未开发");
                break;
            case R.id.ll_user_note:
                Intent intentNote=new Intent(this,NoteActivity.class);
                startActivity(intentNote);
                break;
        }
    }
}
