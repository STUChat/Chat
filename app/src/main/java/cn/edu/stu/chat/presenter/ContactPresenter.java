package cn.edu.stu.chat.presenter;

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
import cn.edu.stu.chat.view.api.IContactView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactPresenter implements IContactPresenter {
    IContactView contactView;
    List<Friend> friendList;

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
        getDataFromInternel();
        //初始化联系人
    }


    public void getDataFromInternel() {
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
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    final List<Friend> list = JsonHelper.getResponseList(chatResponse,Friend.class);
                    for(Friend friend:list){
                        Log.e("TAG",friend.toString());
                    }
                    if(list!=null&&!list.isEmpty()){
                        friendList.clear();
                        friendList.addAll(list);
                        contactView.showList(friendList);
                    }
                }
            }
        }).post(UriConstant.FindFriendList,map);
    }
}
