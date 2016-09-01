package cn.edu.stu.chat.presenter.api;

import android.content.Intent;

/**
 * Created by dell on 2016/8/25.
 */
public interface IContactPresenter extends IPresenter {
    public void init();
    void dealNewFriend();
    void dealResult(int resultCode, Intent data);
}
