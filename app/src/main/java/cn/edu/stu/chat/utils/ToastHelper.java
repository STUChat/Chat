package cn.edu.stu.chat.utils;

import android.app.Activity;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Terence on 2016/8/22.
 * Toast帮助类
 */
public class ToastHelper {
    public static void showToast(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
    public static void showTitleDialog(Activity activity,String message){
        new SweetAlertDialog(activity)
                .setTitleText(message)
                .show();
    }
    public static void showDialog(Activity activity,String title,String message){
        new SweetAlertDialog(activity)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }
    public static void showErrorDialog(Activity activity,String title,String message){
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }
    public static void showWarnDialog(Activity activity,String title,String content,String comfirm){
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(comfirm)
                .show();
    }
    public static void showSuccessDialog(Activity activity,String title,String message){
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }
}
