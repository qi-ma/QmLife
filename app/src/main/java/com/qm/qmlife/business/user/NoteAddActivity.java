package com.qm.qmlife.business.user;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.model.Note;
import com.qm.qmlife.util.DateUtil;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteAddActivity extends BaseActivity {

    @BindView(R.id.iv_break) ImageView ivBreak;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_note_add) TextView tvNoteAdd;
    @BindView(R.id.et_note_title)EditText etNoteTitle;
    @BindView(R.id.et_note_content)EditText etNoteContent;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        new Thread(){
            @Override
            public void run() {
                super.run();
                sqLiteDatabase = Connector.getDatabase();
            }
        }.start();
        ButterKnife.bind(this);

        Intent intent =getIntent();
        String title=intent.getStringExtra("title");
        selectNote(title);
        initInclude();
        initView();
    }

    private void selectNote(String title) {
        if (title==null){
        }else {
            Note note= DataSupport.where("title=?",title).findFirst(Note.class);
            etNoteTitle.setText(note.getTitle());
            etNoteContent.setText(note.getContent());
        }
    }

    private void initView() {
        etNoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                if (s.toString().trim().length()>6){
//                    tlNoteTitle.setErrorEnabled(true);
//                    tlNoteTitle.setError("名字长度不能大于6");
//                }else {
//                    tlNoteTitle.setErrorEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("添加");
    }
    @OnClick({R.id.iv_break,R.id.btn_note_add})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_break:
                this.finish();
                break;
            case R.id.btn_note_add:
                noteAdd();
                break;
        }
    }


    private void noteAdd() {
        if ("".equals(etNoteContent.getText().toString().trim())||
                "".equals(etNoteTitle.getText().toString().trim())
               ){
            ToastUtil.showToast(this,"输入不能为空");

        }else {
            if (etNoteTitle.getText().toString().trim().length()>6){
                ToastUtil.showToast(this,"标题不能超过六个字");
            }else {

                    Cursor cursor = DataSupport.findBySQL("select * from note where title=? ",
                            etNoteTitle.getText().toString().trim());
                    String title = null;
                    if (cursor.moveToFirst()){
                        title=cursor.getString(cursor.getColumnIndex("title"));
                    }
                    cursor.close();
                    if (etNoteTitle.getText().toString().equals(title)){
                        if (updateNote()){
                            ToastUtil.showToast(this,"修改成功");
                        }else {
                            ToastUtil.showToast(this,"修改失败");
                        }
                    }else {
                        if (addNote()){
                            ToastUtil.showToast(this,"保存成功");
                        }else {
                            ToastUtil.showToast(this,"保存失败");
                        }
                    }
            }

        }
    }

    private boolean updateNote() {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("content",etNoteContent.getText().toString());
            contentValues.put("date",DateUtil.getDateTime(new Date()));
            contentValues.put("useraccount", PrefTool.getString(this, Prefs.USER_ACCOUNT,""));
            sqLiteDatabase.update("note",contentValues,"title=?",new String[]{etNoteTitle.getText().toString().trim()});
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean addNote() {
        Note note=new Note();
        note.setTitle(etNoteTitle.getText().toString().trim());
        note.setContent(etNoteContent.getText().toString());
        note.setDate(DateUtil.getDateTime(new Date()));
        note.setUserAccount(PrefTool.getString(this, Prefs.USER_ACCOUNT,""));
        return note.save();
    }
}
