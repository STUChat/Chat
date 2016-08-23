package cn.edu.stu.chat.view.api;

import android.app.Activity;

/**
 * Created by Terence on 2016/8/22.
 */
public interface IWelcomeView extends MvpView{
    public String getUsername();
    public String getPassword();
    public void jumpToActivity(Class<? extends Activity> activityClass);
}
