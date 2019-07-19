package com.qm.qmlife.business.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qm.qmlife.R;
import com.qm.qmlife.business.model.Img;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by syt on 2019/7/2.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private OnRecyclerViewClickListener listener;
    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//下拉刷新
    public static List<Img> imgs=new ArrayList<>();
//    private List<Img> imgs;
    private Context context;
    //创建构造方法获取数据
    public MyAdapter(Context context, String type){
        initData(type);
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==imgs.size()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }

    public void setItemClickListener(OnRecyclerViewClickListener itemClickListener) {
        listener = itemClickListener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建视图
        if (viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.welfare_recycler_foot, parent, false);
            return new FootViewHolder(view);
        }else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_recycler_item,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==TYPE_FOOTER){
            FootViewHolder footViewHolder= (FootViewHolder) holder;

        }else {
            ViewHolder viewHolder= (ViewHolder) holder;
            //这是动画
            ObjectAnimator animator = ObjectAnimator.ofFloat( viewHolder.welfareImg,"alpha",0);
            //动画时间
            animator.setDuration(0);
            //启动动画
            animator.start();

            //为视图添加属性
            Picasso.with(context)
                    .load(imgs.get(position).getImgSrc())
                    .transform(transformation)
//                .placeholder(R.drawable.img_load_placeholder)
//                .error(R.drawable.img_load_error)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.welfareImg);
            //为条目添加点击事件


            //这是动画
            ObjectAnimator animator1 = ObjectAnimator.ofFloat( viewHolder.welfareImg,"alpha",1);
            //动画时间
            animator1.setDuration(1000);
            //启动动画
            animator1.start();

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编写事件
                    listener.onItemClickListener(v,position);
                }
            });
            //为条目子控件添加事件
            viewHolder.welfareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编写事件
                    listener.onItemClickListener(v,position);
                }
            });
        }

    }

//    @Override
//    public void onBindViewHolder(MyAdapter.VH holder, final int position) {
//
//    }

    @Override
    public int getItemCount() {
        //返回数组长度
        return imgs.size()+1;
    }

    private class FootViewHolder extends RecyclerView.ViewHolder{
        private ContentLoadingProgressBar clpbPullOnLoad;
        public FootViewHolder(View itemView) {
            super(itemView);
            clpbPullOnLoad=itemView.findViewById(R.id.clpb_pull_on_load);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView welfareImg;
        public ViewHolder(View itemView) {
            super(itemView);
            //获取控件
            welfareImg=(ImageView)itemView.findViewById(R.id.iv_welfare_img);
        }
    }

    Transformation transformation=new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels/2;// 屏幕宽度（像素）

            return scaleMatrix(source,width);
        }

        @Override
        public String key() {
            return "";
        }
    };

    /**
     * 使用Matrix
     * @param bitmap 原始的Bitmap
     * @param width 目标宽度
     * @return 缩放后的Bitmap
     */
    public static Bitmap scaleMatrix(Bitmap bitmap, int width){

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();
        if (w<=width){
            return bitmap;
        }else {
            float scale=(float) new BigDecimal((float) width / w).doubleValue();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); // 长和宽放大缩小的比例
            Bitmap result= Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
            if (result!=bitmap){
                bitmap.recycle();
            }
            return result;
        }
    }

    /**
     * RecyclerView条目点击接口
     * Created by kang on 2018/9/19.
     */

    public interface OnRecyclerViewClickListener {
        void onItemClickListener(View view,int position);
        void onItemLongClickListener(View view);
    }
    private void initData(String type) {

        imgs.clear();
        if ("1".equals(type)){
            imgs.add(new Img("https://hbimg.huabanimg.com/e54322336173055b0a51e2fa76120f4024320137c25fe-Kg0tCq_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/f6db943808f5a00a7427e1aaa26932b5ebac8efc715e6-tCTs42_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/173a2256d69bafef43c2f09c19b5a69e0af17a9dd947c-0Bgbrj_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/4efd1fd10fc5d7ad59fd0016fcab42817605fd8e1405d-iIEuLW_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/9b795ee216abf7d6c29047475d959e019a34d777b2608-ntIqBy_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/c6b0aaa2e2a9aa30c1d75c211204cd9007852fc51162c4-fJz0wU_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/57a41761150d861867bb4f49f7c2cda7468e8f7519857-Zcu4Wy_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/5d4079b4da0d9816420ca98910e677e6f9e035e897b2e-4nwHVU_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/66f35b77eda735e15094ab4d5e7c7b56e289d21c100291-JP5Yw4_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/435288242e55e65d37d9931f41043dba6afedbdc2b9d2-zSQNpc_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/14f5576e486e7c21d4bd9befd001b4f916027b854fb53-NdBX3n_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/7e157dd2b8595a40459e8c08afcfa6a93323e4ba9be03-bqniDU_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/37e84c7302d631ba84cd8be14710d1b02c71cfb05ba35-C8r9XZ_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/ff2b376401337e271acb5637af14a78d93d99c611ecc59-qJbMpb_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/98f8129f826421f357e83f99da082a0d522c13287fd13-bAWWHC_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/559042d0ae33e4d3a5e8b1a6f4cc679733706695d2381-xhHV17_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/89690dd1d36301e2b13ee54bbbdb42ec5bfbd41ee4c3f-VqarhO_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/1fb675ca54da2d8f8e3d5ad09f6e3a8f1f1cdba8eced9-Z1Lgth_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/0374f01430a917ebfabd1156237ed64d8c3a792f2bdb1-kNeD58_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/be0e0e29546703e2cb6a60c2f888a33bbc8fb9bd23742-A9yOGZ_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/5b2678c104bbbd2216442f8977336b000c0feb471d40a-zq7NTZ_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/2de7bcc2edc393d3fe0393d482610a5e0b6b59eb3375e-ajGlJK_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/de5bb8ab219ed8b37062306c17a96862404c4f7d18768-1LUd7S_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/86405c30c4c39044bec01b1c14c1b5a5ee49074d80586-5pbnN1_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/424a9e8c68b26b7b73d66381b24b374b47632e1ef81d8-dz2QhT_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/3e8c1f3e06fbbe4ebe63a63569503fa48eece18fdf9e0-QtJOuV_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/256366a139aed21814b3d4a7054addf1da85c9d3e0748-lBFsnK_fw658","2018-7-2",0));
            imgs.add(new Img("https://hbimg.huabanimg.com/138243b9c462b01e3f033a4659ebe6ee0b905e0e14e36f-px2g9a_fw658","2018-7-2",0));
        }else {
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697648088&di=6124a7824cfa8540758f16c733c006d7&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201810%2F26%2F20181026084053_hpclf.thumb.700_0.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697837694&di=cb574b664dcbae5a17a52728308b0239&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201807%2F15%2F20180715122122_lfcme.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271056&di=f0416e5901a05ecf551f7e179ed4fc5f&imgtype=0&src=http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi3%2F2703897698%2FTB2_X9piruWBuNjSszgXXb8jVXa_%2521%25212703897698-0-beehive-scenes.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271056&di=c070371044e3358e18d925acfbb48320&imgtype=0&src=http%3A%2F%2Fimg.11665.com%2Fimage_p2%2Fi3%2FTB12_dmJVXXXXcXXVXXXXXXXXXX_%2521%25211-item_pic.gif.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271056&di=a2d841f09400cfc7c9c0006319280b6e&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201605%2F15%2F20160515110609_QcGyi.thumb.700_0.png","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271055&di=191723ed1dd61bff3fd1a6462763ef81&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F23%2F20160723094626_CPJHM.thumb.700_0.jpeg","2018-7-2",0));
            imgs.add(new Img("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1878295021,4268178822&fm=26&gp=0.jpg","2018-7-2",0));
            imgs.add(new Img("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1824530659,2069022069&fm=26&gp=0.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271055&di=7e4640dbd4dee07546ae69ae2f1d97ad&imgtype=0&src=http%3A%2F%2Fshihuo.hupucdn.com%2Fspecial%2F201603%2F2320%2F8b351621d6ffb43367a1114a770c7f1b.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271054&di=5c0d9435af9e851dfdad846f4508c78a&imgtype=0&src=http%3A%2F%2Fimage4.suning.cn%2Fuimg%2Fb2c%2Fnewcatentries%2F0070137433-000000000174554583_1_800x800.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697271053&di=f58ca40f0d78cf660552b9fef35a64e6&imgtype=0&src=http%3A%2F%2Fimg1.doubanio.com%2Fview%2Fgroup_topic%2Fl%2Fpublic%2Fp152120819.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697387293&di=2e31515a92c9486c6597828eb180aeb7&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201604%2F11%2F20160411194942_5v3nR.jpeg","2018-7-2",0));
            imgs.add(new Img("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3973659930,1628317901&fm=26&gp=0.jpg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697479709&di=5d1bfa1ecdf5983191efbe3ee1151d6d&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F13%2F20140913010121_P5dST.thumb.700_0.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697479707&di=4bbfb13ddf86f17b526c9f77e3f7cc74&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201510%2F31%2F20151031224150_2zMrV.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697552011&di=2d85c101650c26bfff462808c99c95d9&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F26e59f23f702e0fe88b6080f73f7ac83883306ce1c8de-jEJq5E_fw658","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697552011&di=7a305efb9c77b16f387db04246c0a572&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201604%2F19%2F20160419183741_2jPYV.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697588809&di=b85ada6f45b9ec6a4e9075859823bad6&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201409%2F07%2F20140907232856_Uuwhe.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697588797&di=d09138cc628a4016b8436f8e2b4d7a65&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201612%2F10%2F20161210220125_LUhRx.thumb.700_0.jpeg","2018-7-2",0));
            imgs.add(new Img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562697648090&di=c905c172029409f1beeffaedad56a587&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F06%2F20181106075637_rvfjv.jpg","2018-7-2",0));

        }

    }

}