package cn.edu.stu.chat.aidl.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.aidl.IMessageManager;
import cn.edu.stu.chat.aidl.IOnNewMessageArrivedListener;
import cn.edu.stu.chat.aidl.MessageInfo;
import cn.edu.stu.chat.utils.PollingUtils;
import cn.edu.stu.chat.utils.ResidentNotificationHelper;

/**
 * 负责轮询接收消息,保存信息
 */
public class MessageManagerService extends Service {
    public static final String TAG = "MessageManagerService";
    public static final String NEW_MESSAGE_ACTION = "cn.edu.stu.chat.newMessage";

    private CopyOnWriteArrayList<MessageInfo> messageInfos = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewMessageArrivedListener> mListenerList = new RemoteCallbackList();
    private MessageRecevier messageRecevier;

    private Binder binder = new IMessageManager.Stub(){

        @Override
        public List<MessageInfo> getMessage() throws RemoteException {
            Log.e(TAG, "获取信息");
            return messageInfos;
        }

        @Override
        public void addMessage(MessageInfo message) throws RemoteException {
            Log.e(TAG, "新增信息");
            messageInfos.add(message);
        }

        @Override
        public void registerListener(IOnNewMessageArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.e(TAG, "注册完成" );
        }

        @Override
        public void unRegisterListener(IOnNewMessageArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.e(TAG, "解注册完成" );
        }
    };

    public MessageManagerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Log.e(TAG,"startMessageManagerService");
        Log.e(TAG, TAG + Process.myPid());
        //20秒一次轮询
        PollingUtils.startPollingService(MessageManagerService.this, 30, PollingService.class, PollingService.ACTION);

        messageRecevier = new MessageRecevier();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NEW_MESSAGE_ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(messageRecevier,filter);
        return START_STICKY;//保持不关闭
    }

    private void notifyNewMessage(MessageInfo message){
        messageInfos.add(message);
        final int N = mListenerList.beginBroadcast();
        for(int i=0;i<N;i++){
            IOnNewMessageArrivedListener l = mListenerList.getBroadcastItem(i);
            if(l!=null){
                try {
                    l.onNewMessageArrived(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageRecevier);
        Log.e(TAG, TAG+"onDestroy: " );
    }

    private class MessageRecevier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case NEW_MESSAGE_ACTION://新消息
                    MessageInfo message =(MessageInfo)intent.getParcelableExtra("new_message");
                    if(message!=null) {
                        //通知全世界
                        notifyNewMessage(message);
                        ResidentNotificationHelper.sendResidentNoticeType0(MessageManagerService.this,message.getSend(),message.getSend(), R.mipmap.ic_launcher);
                    }
                    break;
            }
        }
    }
}
