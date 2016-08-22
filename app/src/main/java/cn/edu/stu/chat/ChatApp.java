package cn.edu.stu.chat;

import android.app.Application;
import android.content.SharedPreferences;

import cn.edu.stu.chat.model.Constant;

/**
 * Created by Terence on 2016/8/22.
 * Application类，便于处理一些需要在整个app生命周期处理的事情
 */
public class ChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
