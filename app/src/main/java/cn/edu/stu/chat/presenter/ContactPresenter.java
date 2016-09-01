package cn.edu.stu.chat.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.NewFriendActivity;
import cn.edu.stu.chat.view.activity.SearchFriendActivity;
import cn.edu.stu.chat.view.api.IContactView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactPresenter implements IContactPresenter {
    IContactView contactView;
    List<Friend> friendList;
    List<Friend> newfriendList;
    @Override
    public void attach(MvpView view) {
        contactView = (IContactView) view;
    }

    @Override
    public void deAttach() {
        contactView = null;
    }

    @Override
    public void init() {
        contactView.setTitle(Constant.ContactTitle);
        friendList = new ArrayList<>();
        newfriendList = new ArrayList<>();
        getFriendDataFromInternet();
        getNewFriendFromInternet();
        //初始化联系人
    }

    @Override
    public void dealNewFriend() {
        if(newfriendList!=null && !newfriendList.isEmpty())
            contactView.jumpToActivity(NewFriendActivity.class,(ArrayList<Friend>)newfriendList);
        else
            contactView.jumpToActivity(SearchFriendActivity.class);
    }

    public void getFriendDataFromInternet() {
        Map<String,String> map = new HashMap();
        map.put("token",contactView.getUser().getToken());
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                if(!contactView.isNetworkAvailable())
                    contactView.showErrorMessage();
                Log.e("Tag1",e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    final List<Friend> list = JsonHelper.getResponseList(chatResponse,Friend.class);
                    if(list!=null&&!list.isEmpty()){
                        friendList.clear();
                        friendList.addAll(list);
                        contactView.showDataChange(friendList);
                    }
                }
            }
        }).post(UriConstant.FindFriendList,map);
    }

    public void getNewFriendFromInternet(){
        Map<String,String> map = new HashMap();
        map.put("token",contactView.getUser().getToken());
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                if(!contactView.isNetworkAvailable())
                    contactView.showErrorMessage();
                Log.e("Tag2",e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    final List<Friend> list = JsonHelper.getResponseList(chatResponse,Friend.class);
                    for(Friend friend:list){
                        Log.e("TAG",friend.toString());
                    }
                    if(list!=null&&!list.isEmpty()){
                        newfriendList.clear();
                        newfriendList.addAll(list);
                        contactView.showNewFriend(newfriendList.size());
                    }
                }
            }
        }).post(UriConstant.GetFriendReq,map);
    }

    public void dealResult(int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK) {//还有没处理的好友{
            newfriendList =(ArrayList<Friend>)data.getExtras().getSerializable("newFriend");
            contactView.showNewFriend(newfriendList.size());
        }else{
            newfriendList.clear();
            contactView.showNewFriend(0);
        }
    }
}
