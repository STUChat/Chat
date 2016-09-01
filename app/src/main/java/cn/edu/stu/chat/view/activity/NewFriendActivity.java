package cn.edu.stu.chat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.NewFriendAdapter;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.widget.RecycleViewDivider;
import cn.edu.stu.chat.view.widget.RecyclerOnBtnClickListener;
import cn.edu.stu.chat.view.widget.RecyclerOnItemClickListener;
import rx.Subscriber;

/**
 * Created by dell on 2016/9/1.
 */
public class NewFriendActivity extends BaseActivity{
    private RecyclerView recyclerview;
    private ArrayList<Friend> friends;
    private NewFriendAdapter adapter;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_new_friend);
        setToolbar(R.id.toolbar);
        initData();
        initView();
    }

    private void initView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview = (RecyclerView)findViewById(R.id.new_friend_recyclerview);
        recyclerview.setLayoutManager(mLayoutManager);
        //自定义下划线
        recyclerview.addItemDecoration(new RecycleViewDivider(this, RecycleViewDivider.VERTICAL_LIST));
        adapter = new NewFriendAdapter(this,recyclerview,friends);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onClick(View view, Friend friend) {
                Intent intent = new Intent(NewFriendActivity.this,FriendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friend",friend);
                intent.putExtras(bundle);
                intent.putExtra("classify",Constant.STRANGER);
                startActivity(intent);
            }
        });
        adapter.setmRecyclerOnBtnClickListener(new RecyclerOnBtnClickListener() {
            @Override
            public void onClick(View view, Friend friend, int type) {
                if(type == NewFriendAdapter.ACCEPT){
                    passOrNotReq(friend,true);
                }else {
                    passOrNotReq(friend,false);
                }
            }
        });
//        adapter.setOnMoreDataLoadListener(
//                new LoadMoreDataListener() {
//                    @Override
//                    public void loadMoreData() {
//                        updateData();
//                    }
//                });
    }

    private void passOrNotReq(final Friend friend, final boolean isAccept) {
        Map<String,String> map = new HashMap();
        map.put("token",((ChatApp)getApplication()).getUser().getToken());
        map.put("userID",friend.getUserID());
        if (isAccept) map.put("flag","2");
            else map.put("flag","1");
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(NewFriendActivity.this))
                    showErrorMessage("网络不可用");
                else
                    showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    if(isAccept)
                        ToastHelper.showSuccessDialog(NewFriendActivity.this,Constant.AddNewFriendTitle,"添加成功");
                    friends.remove(friend);
                    adapter.setList(friends);
                    adapter.notifyDataSetChanged();
                    return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    showErrorMessage(chatResponse.getResponseMsg());
                else
                   showErrorMessage("未知错误");
            }
        }).post(UriConstant.PassOrNotReq, map);
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.AddNewFriendTitle,message);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        friends = (ArrayList<Friend>)bundle.getSerializable("newFriend");
    }

    public void onDestroy(){
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("newFriend",friends);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.onDestroy();
    }
}
