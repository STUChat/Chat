package cn.edu.stu.chat.view.api;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.User;

/**
 * Created by dell on 2016/8/25.
 */
public interface IContactView extends MvpView {
     void jumpToActivity(Class<? extends Activity> activityClass);
     void setTitle(String title);
     void showDataChange(List list);
     boolean isNetworkAvailable();
     User getUser();
     void showErrorMessage();
     void showNewFriend(int num);
     void jumpToActivity(Class<? extends Activity> activityClass,ArrayList<Friend> list);
}
