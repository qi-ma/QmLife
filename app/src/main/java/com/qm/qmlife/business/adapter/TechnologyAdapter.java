package com.qm.qmlife.business.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qm.qmlife.R;
import com.qm.qmlife.business.model.technologynow.Technology;
import com.qm.qmlife.business.technology.TechnologyActivity;
import com.qm.qmlife.business.technology.TechnologyInfoActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import static com.qm.qmlife.business.adapter.MyAdapter.scaleMatrix;

/**
 * Created by syt on 2019/7/18.
 */

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyAdapter.VH>{
    private Context context;
    private ImageView imageView;
    private List<Technology> technologies;

    public TechnologyAdapter(Context context,List<Technology> technologies) {

        this.context=context;
        this.technologies=technologies;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.technology_recycler_item,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.title.setText(technologies.get(position).getObject().getData().getTitle());
        holder.abbr.setText(technologies.get(position).getObject().getData().getPublic_abbr());
        holder.comment.setText(""+technologies.get(position).getObject().getData().getPublic_comments_count());
        holder.like.setText(""+technologies.get(position).getObject().getData().getLikes_count());
        if (!"".equals(technologies.get(position).getObject().getData().getList_image_url())){
            Picasso.with(context)
                    .load("https"+technologies.get(position).getObject().getData().getList_image_url().substring(4))
                    .transform(transformation)
                    .into(holder.image);
        }else {
            holder.image.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TechnologyInfoActivity.class);
                intent.putExtra("slug","https://www.jianshu.com/p/"+technologies.get(position).getObject().getData().getSlug());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return technologies.size();
    }




    public class VH extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView title;
        public final TextView abbr;
        public final TextView comment;
        public final TextView like;

        public VH(View itemView) {
            super(itemView);
            //获取控件
            image=(ImageView)itemView.findViewById(R.id.iv_technology_image);
            title=(TextView)itemView.findViewById(R.id.tv_technology_title);
            abbr=(TextView)itemView.findViewById(R.id.tv_technology_abbr);
            comment=(TextView)itemView.findViewById(R.id.tv_technology_comment);
            like=(TextView)itemView.findViewById(R.id.tv_technology_like);

    }
    }

    Transformation transformation=new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels/4-25;// 屏幕宽度（像素）

            return scaleMatrix(source,width);
        }

        @Override
        public String key() {
            return "";
        }
    };
}
