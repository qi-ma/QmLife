package com.qm.qmlife.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by syt on 2019/7/2.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
