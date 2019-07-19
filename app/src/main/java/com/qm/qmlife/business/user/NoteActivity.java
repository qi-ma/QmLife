package com.qm.qmlife.business.user;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.NoteAdapter;
import com.qm.qmlife.business.model.Note;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends BaseActivity {

    @BindView(R.id.iv_break) ImageView ivBreak;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_note_add) TextView tvNoteAdd;
    @BindView(R.id.rv_note)RecyclerView rvNote;
    List<Note> notes=new ArrayList<>();
    private NoteAdapter noteAdapter;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        new Thread(){
            @Override
            public void run() {
                super.run();
                sqLiteDatabase = Connector.getDatabase();
            }
        }.start();
        ButterKnife.bind(this);
        initInclude();
        initView();
    }

    @Override
    protected void onResume() {
        notes.clear();
        notes.addAll(DataSupport.where("useraccount=?",PrefTool.getString(this, Prefs.USER_ACCOUNT,"")).find(Note.class));
        noteAdapter.notifyDataSetChanged();
        super.onResume();
    }

    private void initView() {
        noteAdapter = new NoteAdapter(R.layout.note_recycler_item,notes);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvNote.setLayoutManager(linearLayoutManager);
        rvNote.setAdapter(noteAdapter);

        noteAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        noteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(NoteActivity.this,NoteAddActivity.class);
                intent.putExtra("title",notes.get(position).getTitle());
                startActivity(intent);
            }
        });
        noteAdapter.isFirstOnly(false);


        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(noteAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvNote);

        // 开启滑动删除
        noteAdapter.enableSwipeItem();
        noteAdapter.setOnItemSwipeListener(onItemSwipeListener);

    }

    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("备忘");
        tvNoteAdd.setVisibility(View.VISIBLE);
    }
    @OnClick({R.id.tv_note_add,R.id.iv_break})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_note_add:
                Intent intent=new Intent(this,NoteAddActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_break:
                this.finish();
                break;
        }
    }

    //滑动回调方法
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }
        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            sqLiteDatabase.delete("note","title=?",new String[]{notes.get(pos).getTitle()});
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };
}
