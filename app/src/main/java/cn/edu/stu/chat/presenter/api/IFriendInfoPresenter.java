package cn.edu.stu.chat.presenter.api;

import android.content.Intent;

/**
 * Created by cheng on 16-8-30.
 */
public interface IFriendInfoPresenter extends IPresenter {

    void init(Intent intent);
    void sendMsg();
    void addFriend();

}
