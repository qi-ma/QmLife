package com.qm.qmlife.business.scan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.qm.qmlife.R;
import com.qm.qmlife.base.BaseActivity;
import com.qm.qmlife.business.technology.TechnologyInfoActivity;
import com.qm.qmlife.util.ToastUtil;
import com.qm.qmlife.util.common.Prefs;
import com.qm.qmlife.util.tool.PrefTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity {

    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.iv_break)ImageView ivBreak;
    @BindView(R.id.et_scan_bar)EditText etScanBar;
    @BindView(R.id.et_scan_qr)EditText etScanQr;
    private IntentIntegrator intentIntegratorScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initInclude();
    }

    private void initInclude() {
        ivBreak.setVisibility(View.VISIBLE);
        tvTitle.setText("扫描");
    }
    @OnClick({R.id.iv_break,R.id.btn_scan_bar,R.id.btn_scan_qr,R.id.btn_scan_bar_copy,R.id.btn_scan_qr_get})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_break:
                this.finish();
                break;
            case R.id.btn_scan_bar:
                intentIntegratorScan = new IntentIntegrator(this);
                intentIntegratorScan.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                intentIntegratorScan.setPrompt("扫描条形码");
                intentIntegratorScan.setCameraId(0);
                intentIntegratorScan.setBeepEnabled(true);
                intentIntegratorScan.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegratorScan.initiateScan();
                PrefTool.setString(this,Prefs.SCAN_TYPE,"bar");
                break;
            case R.id.btn_scan_qr:
                intentIntegratorScan = new IntentIntegrator(this);
                intentIntegratorScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegratorScan.setPrompt("扫描二维码");
                intentIntegratorScan.setCameraId(0);
                intentIntegratorScan.setBeepEnabled(true);
                intentIntegratorScan.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegratorScan.initiateScan();
                PrefTool.setString(this,Prefs.SCAN_TYPE,"qr");
                break;
            case R.id.btn_scan_bar_copy:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", etScanBar.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                break;
            case R.id.btn_scan_qr_get:
                Intent intent=new Intent(this, TechnologyInfoActivity.class);
                intent.putExtra("slug",etScanQr.getText().toString());
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
//                ToastUtil.showToast(this,"扫码取消！");
            } else {
                if ("bar".equals(PrefTool.getString(this,Prefs.SCAN_TYPE,""))){
                    etScanBar.setText(result.getContents().trim());
                }else {
                    etScanQr.setText(result.getContents().trim());
                }
//                ToastUtil.showToast(this, "扫描成功，条码值: " + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
