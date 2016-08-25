package cn.edu.stu.chat.view.api;

import android.app.Activity;

import java.util.List;

/**
 * Created by dell on 2016/8/25.
 */
public interface IContactView extends MvpView {
    public void jumpToActivity(Class<? extends Activity> activityClass);
    public void setTitle(String title);
    public void showList(List list);
    public void showDataChange(List list);
}
