package cn.edu.stu.chat.view.activity;

import android.content.Context;
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
import cn.edu.stu.chat.client.MessageClient;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;

public class MessageActivity extends BaseActivity {
    private ListView listView;
    private List<MessageDetailModel> datas;
    MessageDetailAdapter messageDetailAdapter;
    EditText userInput;
    ImageView add_bn;
    TextView send_bn;

    ChatApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        userInput = (EditText)findViewById(R.id.input_text);
        add_bn = (ImageView)findViewById(R.id.add_bn);
        send_bn = (TextView)findViewById(R.id.send_bn);
        app = ((ChatApp)getApplication());
//        client = app.getClient();
//        sendQ = app.getSendQ();
//        client.setSendQ(sendQ);
//        recvQ = app.getRecvQ();

        MessageDetailModel data = new MessageDetailModel();
        data.setMsg("傻孩子你怎么会是傻孩子!!!明天要去哪里，我也不知道");
        data.setName("Terence");
        data.setUserId("lawliex");
        datas = new ArrayList<>();
        for(int i = 0; i < 100; i ++)
            datas.add(data);
        messageDetailAdapter = new MessageDetailAdapter(this, datas);

        listView = (ListView)findViewById(R.id.message);
        listView.setAdapter(messageDetailAdapter);
        listView.setSelection(datas.size() - 1);
        listView.setDivider(null);
        initListener();

    }
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
                if(userInput.getText().toString().length() != 0) {
                    add_bn.setVisibility(View.GONE);
                    send_bn.setVisibility(View.VISIBLE);
                }else{
                    add_bn.setVisibility(View.VISIBLE);
                    send_bn.setVisibility(View.GONE);
                }

            }
        });
        send_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = userInput.getText().toString();
                Log.e("send",msg);
                app.getSendQ().add("id2"+'#'+"id1"+'#'+msg);

            }
        });
    }

    @OnClick(R.id.input_text)
    void focus(){
        userInput.setFocusable(true);
    }

}
