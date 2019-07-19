package com.qm.qmlife.business.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qm.qmlife.R;
import com.qm.qmlife.business.model.Note;

import java.util.List;

/**
 * Created by syt on 2019/7/9.
 */

public class NoteAdapter extends BaseItemDraggableAdapter<Note,BaseViewHolder> {

    public NoteAdapter(int layoutResId, @Nullable List<Note> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note item) {
        helper.setText(R.id.tv_note_title,item.getTitle());
        helper.setText(R.id.tv_note_date,item.getDate());
    }
}
