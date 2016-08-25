package cn.edu.stu.chat.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.MessageAdapter;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.MessageModel;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.activity.MessageActivity;
import cn.edu.stu.chat.view.api.BaseFragment;

/**
 * Created by cheng on 16-8-22.
 */
public class MessagingFragment extends BaseFragment implements AdapterView.OnItemClickListener{
   // @BindView(R.id.message_listview)
    private ListView msgListView;
    private List<MessageModel> datas;
    private MessageAdapter messageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_messaging, null);
        msgListView = (ListView) view.findViewById(R.id.message_listview);
        MessageModel msg = new MessageModel();
        msg.setFriendId("lawliex");
        msg.setMsg("世界那么大，我想去看看");
        msg.setNickName("Terence");
        msg.setPhotoUrl("http://lawliex.cn/terence.jpg");
        msg.setTime("12:00");
        datas = new ArrayList<>();


        for(int i = 0 ;i < 100; i++)
            datas.add(msg);
        messageAdapter = new MessageAdapter(getContext(), datas);
        msgListView.setAdapter(messageAdapter);
        msgListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("friendId", datas.get(i).getFriendId());
        startActivity(intent);
    }
}
