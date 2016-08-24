package cn.edu.stu.chat.view.api;

import android.app.Activity;

/**
 * Created by dell on 2016/8/23.
 */
public interface ILoginView extends MvpView {
    public void setTitle(String title);
    public void setMessage(String text);
    public void jumpToActivity(Class<? extends Activity> activityClass);
    public void openEye();
    public void closeEye();
}