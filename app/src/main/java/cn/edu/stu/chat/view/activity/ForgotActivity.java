package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.logging.Handler;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.aidl.IMessageManager;
import cn.edu.stu.chat.aidl.IOnNewMessageArrivedListener;
import cn.edu.stu.chat.aidl.MessageInfo;
import cn.edu.stu.chat.aidl.service.MessageManagerService;
import cn.edu.stu.chat.utils.ResidentNotificationHelper;
import cn.edu.stu.chat.utils.ToastHelper;

/**
 * Created by dell on 2016/8/22.
 */
public class ForgotActivity extends Activity {

    private static String TAG = "ForgotActivity";
    private IMessageManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Intent intent = new Intent(this, MessageManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IMessageManager messageManager = IMessageManager.Stub.asInterface(iBinder);
            try{
                mManager = messageManager;
//                List<MessageInfo> list = messageManager.getMessage();
//                for(MessageInfo messageInfo:list)
//                    if(messageInfo!=null)
//                        Log.e(TAG,messageInfo.toString());
                messageManager.registerListener(newMessageArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    private IOnNewMessageArrivedListener newMessageArrivedListener = new IOnNewMessageArrivedListener.Stub(){

        @Override
        public void onNewMessageArrived(MessageInfo message) throws RemoteException {
            //获得信息
            ToastHelper.showToast(ForgotActivity.this,message.toString());
        }
    };

    @Override
    public void onDestroy(){
        if(mManager!=null && mManager.asBinder().isBinderAlive()){
            try{
                mManager.unRegisterListener(newMessageArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
        Log.e(TAG, "unbindservice");
    }
}
