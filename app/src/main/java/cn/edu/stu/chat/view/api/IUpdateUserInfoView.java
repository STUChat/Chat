package cn.edu.stu.chat.view.api;

import cn.edu.stu.chat.model.User;

/**
 * Created by dell on 2016/8/29.
 */
public interface IUpdateUserInfoView  extends MvpView{
    void setTitle(String title);
    void setUser(User user);
    User getUser();
    void showSucessDialog(String title);
    void showInfo(int i,String content);
    void showFailDialog(String title,String message);
    boolean isNetworkAvialable();
}
