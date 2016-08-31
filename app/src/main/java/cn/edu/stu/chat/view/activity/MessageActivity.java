package cn.edu.stu.chat.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.MessageDetailAdapter;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.view.api.BaseActivity;

public class MessageActivity extends BaseActivity {
    private ListView listView;
    private List<MessageDetailModel> datas;
    MessageDetailAdapter messageDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        /**
         * 获取好友信息包括Userid
         * Intent intent = getIntent();
         * Friend friend = (Friend)intent.getExtras().getSerializable("friend");
         * friend.getUserId();
         */
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

    }
}
