package cn.edu.stu.chat.view.api;

import cn.edu.stu.chat.model.User;

/**
 * Created by dell on 2016/8/29.
 */
public interface IUserInfoView extends MvpView{
    void setUser(User user);
    User getUser();
    void showUserInfo(User user);
    void setTitle(String title);
    boolean isNetworkAvialable();
    void showFailDialog(String title, String message);
}
