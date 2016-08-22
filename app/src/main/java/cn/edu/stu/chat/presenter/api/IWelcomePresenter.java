package cn.edu.stu.chat.presenter.api;

import android.app.Activity;

import cn.edu.stu.chat.model.User;

/**
 * Created by Terence on 2016/8/22.
 */
public interface IWelcomePresenter {

    public boolean isSavePassword();
    public boolean login();
    public void setUser(User user);
}
