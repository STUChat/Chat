package cn.edu.stu.chat.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.utils.ResidentNotificationHelper;
import rx.Subscriber;

/**
 * Created by cheng on 16-9-3.
 */
public class MessageService extends Service {
    /**
     * MessageClient 收发消息客户端
     * sendQ 保存需要发送的消息
     * recvQ 保存收到的消息
     */
    private User user;
    volatile private MessageClient client;
    volatile private Queue<String> sendQ;
    volatile private Queue<String> recvQ;
    private Map<String,List<MessageDetailModel>> messageListMap;
    private RemoteCallbackList<IOnNewMessageArrivedListener> listenerList = new RemoteCallbackList();

    @Override
    public IBinder onBind(Intent intent) {
        if(intent.getExtras().getSerializable("user")!=null)
            user = (User) intent.getExtras().getSerializable("user");
        return binder;
    }

    private Binder binder = new IMessageManager.Stub(){

        @Override
        public List<MessageDetailModel> getMessage(String id) throws RemoteException {
            List<MessageDetailModel> list = getMessageList(id);
            messageListMap.put(id,null);
            return list;
        }

        @Override
        public void sendMessage(MessageDetailModel message) throws RemoteException {
            sendMsg(message);
        }

        @Override
        public void registerListener(IOnNewMessageArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
        }

        @Override
        public void unRegisterListener(IOnNewMessageArrivedListener listener) throws RemoteException {
            listenerList.unregister(listener);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getExtras().getSerializable("user")!=null)
            user = (User) intent.getExtras().getSerializable("user");
        client = getClient();
        messageListMap = new HashMap<>();
        recvMessage();
        Log.e("tag","startService");
        return START_STICKY;
    }

    public MessageClient getClient(){
        if(client == null || !client.getClientThread().isAlive()){
            synchronized (MessageClient.class) {
                if(client == null || !client.getClientThread().isAlive()) {
                    client = new MessageClient();
                    recvQ = client.getRecvQ();
                    sendQ = client.getSendQ();
                    sendQ.add("hello,i am client");
                    client.setSendQ(sendQ);
                }
            }
        }
        return client;
    }

    private void recvMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(getClient()!= null && !recvQ.isEmpty()){
                        if(!recvQ.isEmpty()) {
                            Log.e("message",recvQ.peek());
                            notifyNewMessage(recvQ.poll());
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    // id1#id2#type#content
    private void notifyNewMessage(String msg) {
        msg = msg.replaceAll(" ", "");
        Log.e("tag","notify-->"+msg);
        if(!msg.contains("#"))
            return;
        String[] str = msg.split("#");
        String id1 = str[0];
        String id2 = str[1];
        int type =Integer.parseInt(str[2]);
        String content =str[3];
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String date = sDateFormat.format(new java.util.Date());
        MessageDetailModel model = new MessageDetailModel(id2,type,content,date);
        List<MessageDetailModel> list = messageListMap.get(id2);
        if(list ==null){
            list = new CopyOnWriteArrayList();
        }
        list.add(model);
        messageListMap.put(id2,list);

        final int N = listenerList.beginBroadcast();
        for(int i=0;i<N;i++){
            IOnNewMessageArrivedListener l = listenerList.getBroadcastItem(i);
            if(l!=null){
                try {
                    l.onNewMessageArrived(id2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

//        if(N==0){
//            sendNotication(getBaseContext(),model);
//        }
        listenerList.finishBroadcast();
    }

    private void sendNotication(final Context context, final MessageDetailModel model) {
        Map<String,String> map = new HashMap<>();
        map.put("token",user.getToken());
        map.put("userID",model.getUserId());
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ChatResponse chatResponse) {
                Friend friend = JsonHelper.getResponseValue(chatResponse,Friend.class);
                if(friend!=null){
                    ResidentNotificationHelper.sendResidentNoticeType0(context,friend.getName(),model.getMsg(), R.mipmap.ic_launcher,friend);
                }
            }
        }).post(UriConstant.GetUserInfo,map);
    }

    public List<MessageDetailModel> getMessageList(String id){
        return messageListMap.get(id);
    }

    public void sendMsg(MessageDetailModel model){
        client.getSendQ().add(user.getEmail()+"#"+model.toString());
    }
}
