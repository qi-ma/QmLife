package com.qm.qmlife.business.user;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.model.User;
import com.qm.qmlife.util.ToastUtil;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.iv_break) ImageView ivBreak;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_useraccount) EditText etUseraccount;
    @BindView(R.id.et_userpwd) EditText etUserpwd;
    @BindView(R.id.et_userokpwd) EditText etUserokpwd;
    @BindView(R.id.rb_man) RadioButton rbMan;
    @BindView(R.id.rb_woman) RadioButton rbWoman;
    @BindView(R.id.btn_register)Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initInclude();
        initView();
    }

    private void initView() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                if (s.toString().trim().length()>6){
//                    etUsername.setErrorEnabled(true);
//                    etUsername.setError("名字长度不能大于6");
//                }else {
//                    etUsername.setErrorEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("注册");
    }

    @OnClick({R.id.btn_register,R.id.iv_break,R.id.rb_man,R.id.rb_woman})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.iv_break:
                this.finish();
                break;
            case R.id.rb_man:
                if (rbWoman.isChecked()){
                    rbWoman.setChecked(false);
                }
                break;
            case R.id.rb_woman:
                if (rbMan.isChecked()){
                    rbMan.setChecked(false);
                }
                break;
        }
    }

    private void register() {
        if ("".equals(etUseraccount.getText().toString().trim())||
                "".equals(etUsername.getText().toString().trim())||
                "".equals(etUserpwd.getText().toString().trim())||
                "".equals(etUserokpwd.getText().toString().trim())){
            ToastUtil.showToast(this,"输入不能为空");

        }else {
            if (etUsername.getText().toString().trim().length()>6){
                ToastUtil.showToast(this,"名字不能超过六个字");
            }else {
                if (etUserpwd.getText().toString().trim()
                        .equals(etUserokpwd.getText().toString().trim())){
                    Cursor cursor = DataSupport.findBySQL("select * from user where account=? ",
                            etUseraccount.getText().toString().trim());
                    String account = null;
                    if (cursor.moveToFirst()){
                        account=cursor.getString(cursor.getColumnIndex("account"));
                    }
                    cursor.close();
                    if (etUseraccount.getText().toString().equals(account)){
                        ToastUtil.showToast(this,"此账号已注册");
                    }else {
                        if (addUser()){
                            ToastUtil.showToast(this,"注册成功");
                        }else {
                            ToastUtil.showToast(this,"注册失败");
                        }
                    }
                }else {
                    ToastUtil.showToast(this,"密码输入不匹配");
                }
            }

        }
    }

    private Boolean addUser() {
            User user = new User();
            user.setAccount(etUseraccount.getText().toString().trim());
            user.setName(etUsername.getText().toString().trim());
            user.setPwd(etUserpwd.getText().toString().trim());
            if (rbMan.isChecked()) {
                user.setSex("男神");
            } else {
                user.setSex("女神");
            }
            return user.save();

    }
}
