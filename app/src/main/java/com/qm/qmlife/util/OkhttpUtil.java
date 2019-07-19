package com.qm.qmlife.util;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.qm.qmlife.base.BaseActivity;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wjy on 2019/3/13.
 */

public class OkhttpUtil {
    private static final String TAG = "OkhtttpUtils";
    private static OkhttpUtil mOkhtttpUtils;
    private OkHttpClient mOkHttpClien;
    private final Handler mHandler;


    private OkhttpUtil() {
        //创建一个主线程的handler
        mHandler = new Handler(Looper.getMainLooper());
        mOkHttpClien = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkhttpUtil getInstance() {
        if (mOkhtttpUtils == null) {
            synchronized (OkhttpUtil.class) {
                if (mOkhtttpUtils == null) {
                    return mOkhtttpUtils = new OkhttpUtil();
                }
            }
        }
        return mOkhtttpUtils;
    }

    public void doGet(final BaseActivity context,String url, final OkCallback okCallback) {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Request request = new Request.Builder()
                .get()

                .url(url)
                .build();

        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {
                    if (!context.isFinishing()&&progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!context.isFinishing()&&progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (okCallback != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    okCallback.onResponse(json);
                                }
                            });

                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (okCallback != null) {
                    okCallback.onFailure(new Exception("网络异常"));
                }

            }
        });
    }

    public void doPostForForm(String url, Map<String, String> map, final OkCallback okCallback) {

        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null){
            for (String key : map.keySet()) {
                builder.add(key, map.get(key));
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();
        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {

                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (okCallback != null) {
                            Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                                    okCallback.onResponse(json);
                                }
                            });

                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (okCallback != null) {
                    okCallback.onFailure(new Exception("网络异常"));
                }

            }
        });
    }

    public void doPostForJson(Request request, final OkCallback okCallback) {

        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {

                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (okCallback != null) {
                            Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                                    okCallback.onResponse(json);
                                }
                            });

                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (okCallback != null) {
                    okCallback.onFailure(new Exception("网络异常"));
                }

            }
        });
    }

    public interface OkCallback {
        void onFailure(Exception e);

        void onResponse(String json);
    }
    public void doPostForFormZto(final BaseActivity context, String url, Map<String, String> map, final OkCallback okCallback) {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("网络请求中");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FormBody.Builder builder = new FormBody.Builder();
        StringBuffer sb = new StringBuffer();
        if (map!=null){
            for (String key : map.keySet()) {
                sb.append(key+"="+map.get(key)+"&");
            }
        }
        Log.i("TAG", ""+sb.toString());
        RequestBody formBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),sb.toString());
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();
        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (!context.isFinishing()&&progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (okCallback != null) {

                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!context.isFinishing()&&progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (okCallback != null) {
                            Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "onResponse: "+ Thread.currentThread().getName());
                                    okCallback.onResponse(json);
                                }
                            });

                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (okCallback != null) {
                    okCallback.onFailure(new Exception("网络异常"));
                }



            }
        });
    }



}
