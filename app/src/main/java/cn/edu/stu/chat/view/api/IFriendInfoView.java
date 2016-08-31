package cn.edu.stu.chat.view.api;

import android.app.Activity;

import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.User;

/**
 * Created by cheng on 16-8-30.
 */
public interface IFriendInfoView extends MvpView {
    void showBtn(int classify);
    boolean isNetworkAvailable();
    void jumpToActivity(Class<? extends Activity> activityClass, Boolean isFinish);
    void showInfo(Friend friend);
    void jumpToActivity(Class<? extends Activity> activityClass, Boolean isFinish,Friend friend);
}
