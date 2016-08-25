package cn.edu.stu.chat.view.api;

import android.app.Activity;

/**
 * Created by dell on 2016/8/25.
 */
public interface IContactView extends MvpView {
    public void jumpToActivity(Class<? extends Activity> activityClass);
}
