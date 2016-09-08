package cn.edu.stu.chat.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.MessageDetailAdapter;
import cn.edu.stu.chat.client.IMessageManager;
import cn.edu.stu.chat.client.IOnNewMessageArrivedListener;
import cn.edu.stu.chat.client.MessageService;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.utils.ContactDbUtil;
import cn.edu.stu.chat.view.api.BaseActivity;

public class MessageActivity extends BaseActivity {
    private ListView listView;
    private final static int NEW_MESSAGE = 100;
    volatile private List<MessageDetailModel> datas;
    private MessageDetailAdapter messageDetailAdapter;
    private EditText userInput;
    private ImageView add_bn;
    private TextView send_bn;
    private String friendId = null;
    private IMessageManager mManager;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        userInput = (EditText)findViewById(R.id.input_text);
        add_bn = (ImageView)findViewById(R.id.add_bn);
        send_bn = (TextView)findViewById(R.id.send_bn);
        user = ((ChatApp)getApplication()).getUser();
        Intent intent = getIntent();
        Friend friend = (Friend)intent.getExtras().getSerializable("friend");
        friendId = friend.getEmail();
        addToContactDb(user,friend);
        datas = new ArrayList<>();
        messageDetailAdapter = new MessageDetailAdapter(this, datas,friend,user);
        listView = (ListView)findViewById(R.id.message);
        listView.setAdapter(messageDetailAdapter);
        if(datas!=null && !datas.isEmpty())
            listView.setSelection(datas.size() - 1);
        listView.setDivider(null);
        setToolbar(R.id.main_toolbar);
        setTitle(friend.getName());
        initListener();
        intent = new Intent(this, MessageService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",((ChatApp)getApplication()).getUser());
        intent.putExtras(bundle);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private void addToContactDb(User user, Friend friend) {
        if(user!=null && friend!=null)
        new ContactDbUtil(this).insert(user,friend);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEW_MESSAGE:
                    listView.deferNotifyDataSetChanged();
                    listView.setSelection(datas.size() - 1);
                break;
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IMessageManager messageManager = IMessageManager.Stub.asInterface(iBinder);
            try{
                mManager = messageManager;
                messageManager.registerListener(newMessageArrivedListener);
                Log.e("TAG", "bindservice" );
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String date = sDateFormat.format(new java.util.Date());
                mManager.sendMessage(new MessageDetailModel("id2",1,"hello, i am client",date));
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
        public void onNewMessageArrived(String id) throws RemoteException {
            List messageList = mManager.getMessage(id);
            if(messageList!=null && !messageList.isEmpty()){
                datas.addAll(messageList);
                handler.sendEmptyMessage(NEW_MESSAGE);
            }
        }
    };

    public void initListener(){
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userInput.getText().toString().length() == 0)
                {
                    send_bn.setVisibility(View.GONE);
                    add_bn.setVisibility(View.VISIBLE);

                }else{
                    send_bn.setVisibility(View.VISIBLE);
                    add_bn.setVisibility(View.GONE);
                }
            }
        });
        send_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInput.getText().toString()=="")
                    return;
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String date = sDateFormat.format(new java.util.Date());
                int type = 1;//代表文本类型
                MessageDetailModel message = new MessageDetailModel(friendId,type,userInput.getText().toString(),date);
                try {
                    mManager.sendMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                message.setUserId(user.getEmail());
                datas.add(message);
                listView.deferNotifyDataSetChanged();
                if(datas!=null && !datas.isEmpty())
                    listView.setSelection(datas.size() - 1);
                userInput.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MessageService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",((ChatApp)getApplication()).getUser());
        intent.putExtras(bundle);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mManager!=null && mManager.asBinder().isBinderAlive()){
            try{
                mManager.unRegisterListener(newMessageArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        Log.e("TAG", "unbindservice");
        finish();
    }
}
