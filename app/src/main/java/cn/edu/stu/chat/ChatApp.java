package cn.edu.stu.chat;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import cn.edu.stu.chat.client.MessageClient;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.utils.ToastHelper;

/**
 * Created by Terence on 2016/8/22.
 * Application类，便于处理一些需要在整个app生命周期处理的事情
 */
public class ChatApp extends Application {
    private static final int SHOW_MESSAGE = 100;
    private User user;
    /**
     * 设置用户
     */
    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
