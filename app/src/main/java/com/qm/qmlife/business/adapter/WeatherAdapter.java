package com.qm.qmlife.business.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qm.qmlife.R;
import com.qm.qmlife.business.model.forecast.Daily_forecast;
import com.qm.qmlife.util.DateUtil;

import java.util.List;

/**
 * Created by syt on 2019/7/15.
 */

public class WeatherAdapter extends BaseQuickAdapter<Daily_forecast,BaseViewHolder>{
    public WeatherAdapter(int layoutResId, @Nullable List<Daily_forecast> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Daily_forecast item) {
        helper.setText(R.id.tv_recycler_item_date, DateUtil.getMD(item.getDate())+" "+DateUtil.getWeekOfDate(item.getDate()));
        helper.setText(R.id.tv_recycler_item_cond,item.getCond_txt_d());
        helper.setText(R.id.tv_recycler_item_tmp,item.getTmp_max()+"℃/"+item.getTmp_min()+"℃");
    }
}
