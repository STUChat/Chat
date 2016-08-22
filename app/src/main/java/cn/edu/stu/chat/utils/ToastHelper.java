package cn.edu.stu.chat.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Terence on 2016/8/22.
 * Toast帮助类
 */
public class ToastHelper {
    public static void showToast(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
