package cn.edu.stu.chat.view.api;

import android.app.Activity;

import cn.edu.stu.chat.model.User;

/**
 * Created by Terence on 2016/8/22.
 */
public interface IWelcomeView extends MvpView{
    public String getUsername();
    public String getPassword();
    public void jumpToActivity(Class<? extends Activity> activityClass);
    public boolean isLogin();
    public void setUser(User user);
}
