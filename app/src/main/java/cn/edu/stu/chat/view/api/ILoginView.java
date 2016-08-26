package cn.edu.stu.chat.view.api;

import android.app.Activity;

import cn.edu.stu.chat.model.User;

/**
 * Created by dell on 2016/8/23.
 */
public interface ILoginView extends MvpView {
    public void setTitle(String title);
    public void saveUsernamePwd(String username,String password);
    public boolean isLogin();
    public void showErrorMessage(String text);
    public void jumpToActivity(Class<? extends Activity> activityClass);
    public void openEye();
    public void closeEye();
    public void setUser(User user);
    public boolean isNetworkAvailable();
}
