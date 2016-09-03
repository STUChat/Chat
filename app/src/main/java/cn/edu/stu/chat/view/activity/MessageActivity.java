package cn.edu.stu.chat.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.MessageDetailAdapter;
import cn.edu.stu.chat.client.IMessageManager;
import cn.edu.stu.chat.client.IOnNewMessageArrivedListener;
import cn.edu.stu.chat.client.MessageClient;
import cn.edu.stu.chat.client.MessageService;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.MessageDetailModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        userInput = (EditText)findViewById(R.id.input_text);
        add_bn = (ImageView)findViewById(R.id.add_bn);
        send_bn = (TextView)findViewById(R.id.send_bn);

        Intent intent = getIntent();
        Friend friend = (Friend)intent.getExtras().getSerializable("friend");
        friendId = friend.getEmail();
        datas = new ArrayList<>();
        messageDetailAdapter = new MessageDetailAdapter(this, datas);
        listView = (ListView)findViewById(R.id.message);
        listView.setAdapter(messageDetailAdapter);
        if(datas!=null && !datas.isEmpty())
            listView.setSelection(datas.size() - 1);
        listView.setDivider(null);
        initListener();
        intent = new Intent(this, MessageService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",((ChatApp)getApplication()).getUser());
        intent.putExtras(bundle);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
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
                mManager.sendMessage(new MessageDetailModel("id2",1,"hello, i am service"));
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
                int type = 1;//代表文本类型
                MessageDetailModel message = new MessageDetailModel(friendId,type,userInput.getText().toString());
                try {
                    mManager.sendMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                message.setUserId(((ChatApp)getApplication()).getUser().getEmail());
                datas.add(message);
                listView.deferNotifyDataSetChanged();
                if(datas!=null && !datas.isEmpty())
                    listView.setSelection(datas.size() - 1);
                userInput.setText("");
            }
        });

    }

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
        Log.e("TAG", "unbindservice");
    }
}
