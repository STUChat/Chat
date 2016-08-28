package cn.edu.stu.chat.aidl.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.stu.chat.aidl.MessageInfo;

/**
 * Created by cheng on 16-8-28.
 */
public class PollingService extends IntentService {
    private static String TAG = "PollingService";
    public static final String ACTION = "cn.edu.stu.chat.service.PollingService";

    public PollingService() {
        super("PollingService");
    }

    //执行轮询操作
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent: "+"大王叫我来轮询" );
        //检查MessageManager是否被杀死
        if(!isServiceRunning(this,"cn.edu.stu.chat.aidl.service.MessageManagerService")){
            startService(new Intent(this,MessageManagerService.class));
        }
        //假设有新消息
        Intent newMsgIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("new_message",new MessageInfo("小红","吃饭了吗","13:00"));
        newMsgIntent.putExtras(bundle);
        newMsgIntent.setAction(MessageManagerService.NEW_MESSAGE_ACTION);
        sendBroadcast(newMsgIntent);
    }

    private boolean isServiceRunning(Context context, String serviceName) {
        if (!TextUtils.isEmpty(serviceName) && context != null) {
            ActivityManager activityManager
                    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfoList
                    = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(100);
            for (Iterator<ActivityManager.RunningServiceInfo> iterator = runningServiceInfoList.iterator(); iterator.hasNext(); ) {
                ActivityManager.RunningServiceInfo runningServiceInfo =iterator.next();
                if (serviceName.equals(runningServiceInfo.service.getClassName().toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}