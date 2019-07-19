package com.qm.qmlife.business.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.MenuActivity;
import com.qm.qmlife.manager.AppManager;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_break) ImageView ivBreak;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.et_useraccount) EditText etUseraccount;
    @BindView(R.id.et_userpwd) EditText etUserpwd;
    @BindView(R.id.tv_logout) TextView tvLogout;
    @BindView(R.id.tv_register) TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initInclude();
        initView();

    }

    private void initView() {
        etUserpwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login();
                return true;
            }
        });
    }

    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("登录");

        if (!"".equals(PrefTool.getString(this,Prefs.USER_NAME,""))){
            tvLogout.setText("退出账号（"+PrefTool.getString(this,Prefs.USER_NAME,"")+"）");
        }else {
            tvLogout.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_logout,R.id.tv_register,R.id.iv_break})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_logout:
                logout();
                break;
            case R.id.tv_register:
                Intent intentRegister=new Intent(this,RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.iv_break:
                this.finish();
                break;
        }
    }

    private void logout() {
        AppManager.getInstance().finishActivity(MenuActivity.class);
        ToastUtil.showToast(this,"账号已退出");
        PrefTool.setString(this, Prefs.USER_NAME,"");
        PrefTool.setString(this, Prefs.USER_ACCOUNT,"");
        PrefTool.setString(this, Prefs.USER_SEX,"");
        Intent intent=new Intent(this,MenuActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void login() {
        if ("".equals(etUseraccount.getText().toString().trim())
                ||"".equals(etUserpwd.getText().toString().trim())){
            ToastUtil.showToast(this,"输入不能为空");
        }else {

            String account=null;
            String name=null;
            String pwd=null;
            String sex=null;
            Cursor cursor = DataSupport.findBySQL("select * from user where account=? and pwd=?",
                    etUseraccount.getText().toString(),
                    etUserpwd.getText().toString());
            if (cursor.moveToFirst()) {
                account = cursor.getString(cursor.getColumnIndex("account"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                sex = cursor.getString(cursor.getColumnIndex("sex"));
            }
            cursor.close();

            if (account==null||pwd==null){
                ToastUtil.showToast(this,"账号密码错误");
            }else {
                PrefTool.setString(this, Prefs.USER_NAME,name);
                PrefTool.setString(this, Prefs.USER_SEX,sex);
                PrefTool.setString(this, Prefs.USER_ACCOUNT,account+"md5");
                AppManager.getInstance().finishActivity(MenuActivity.class);
                Intent intent=new Intent(this,MenuActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }
}
