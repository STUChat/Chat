package cn.edu.stu.chat.presenter.api;

import android.content.Intent;

/**
 * Created by dell on 2016/8/29.
 */
public interface IUpdateUserInfoPresenter extends IPresenter{
    void init(Intent intent);
    void update(String newContent);
}
