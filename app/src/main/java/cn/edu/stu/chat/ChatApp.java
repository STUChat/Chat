package cn.edu.stu.chat;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

import cn.edu.stu.chat.client.MessageClient;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.utils.ToastHelper;

/**
 * Created by Terence on 2016/8/22.
 * Application类，便于处理一些需要在整个app生命周期处理的事情
 */
public class ChatApp extends Application {
    private static final int SHOW_MESSAGE = 100;
    private User user;
    private String userID = "id2";
    private String serverID = "id1";

    /**
     * MessageClient 收发消息客户端
     * sendQ 保存需要发送的消息
     * recvQ 保存收到的消息
     */
    volatile  private MessageClient client;
    volatile  private Queue<String> sendQ;
    volatile  private Queue<String> recvQ;

    /**
     * 获取消息客户端，每个app只有一个客户端，并且保证每次获取的客户端都与服务器保持连接
     * @return 消息客户端
     */
    public MessageClient getClient(){
        if(client == null || !client.getClientThread().isAlive()){
            synchronized (MessageClient.class) {
                if(client == null || !client.getClientThread().isAlive()) {
                    client = new MessageClient();
                    recvQ = client.getRecvQ();
                    sendQ = client.getSendQ();
                    sendQ.add("id2#id1#hello");
                   // client.setSendQ(sendQ);
                }
            }
        }
        return client;
    }

    /**
     * 检测与服务器之间的连接是否断开
     */
    static int x = 0;
    public void detected(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = getClient();
        detected();
        recvMessage();
    }
    public Queue<String> getSendQ(){
        return sendQ;
    }
    public Queue<String> getRecvQ(){
        return recvQ;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_MESSAGE:
                    Toast.makeText(ChatApp.this,getRecvQ().peek(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    void recvMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(getClient()!= null && !getRecvQ().isEmpty()){

                        if(!getRecvQ().isEmpty()) {
                            Log.e("message",getRecvQ().peek());
                            handler.sendEmptyMessage(SHOW_MESSAGE);
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

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

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
