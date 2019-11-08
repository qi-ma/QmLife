package com.qm.qmlife.business.welfare;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.adapter.MyAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends BaseActivity{

    @BindView(R.id.vp_img_info)ViewPager vpImgInfo;
    @BindView(R.id.tv_img_num)TextView tvImgNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        String position=intent.getStringExtra("position");
        initData();
        initView(Integer.valueOf(position));
    }

    private void initData() {

    }

    private void initView(int position) {

        tvImgNum.setText("  "+(position+1)+"/"+ MyAdapter.imgs.size()+"  ");
        vpImgInfo.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        vpImgInfo.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return MyAdapter.imgs.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                final PhotoView view = new PhotoView(PhotoActivity.this);
//                view.setBackgroundResource(R.color.pv_gb);

                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);

                //这是动画
                ObjectAnimator animator = ObjectAnimator.ofFloat( view,"alpha",0);
                //动画时间
                animator.setDuration(0);
                //启动动画
                animator.start();

                picassoPhoto(position, view);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoActivity.this.finish();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vpImgInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvImgNum.setText("  "+(position+1)+"/"+MyAdapter.imgs.size()+"  ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpImgInfo.setCurrentItem(position);
    }

    private void picassoPhoto(final int position, final PhotoView view) {
        Log.d("debug",MyAdapter.imgs.get(position).getImgSrc());
        Picasso.with(PhotoActivity.this)
                .load(MyAdapter.imgs.get(position).getImgSrc())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        view.setImageBitmap(bitmap);
                        //这是动画
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat( view,"alpha",1);
                        //动画时间
                        animator1.setDuration(1000);
                        //启动动画
                        animator1.start();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat( view,"alpha",1);
                        animator1.setDuration(1000);
                        animator1.start();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat( view,"alpha",1);
                        animator1.setDuration(1000);
                        animator1.start();
                    }

                });
    }
}
