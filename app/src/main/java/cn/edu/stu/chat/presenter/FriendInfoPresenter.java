package cn.edu.stu.chat.presenter;

import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IFriendInfoPresenter;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.activity.FriendInfoActivity;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.activity.MessageActivity;
import cn.edu.stu.chat.view.api.IFriendInfoView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by cheng on 16-8-30.
 */
public class FriendInfoPresenter implements IFriendInfoPresenter {
    IFriendInfoView friendInfoView;
    private int classify;//代表是朋友还是陌生人
    private Friend friend;
    @Override
    public void attach(MvpView view) {
        friendInfoView = (IFriendInfoView)view;
    }

    @Override
    public void deAttach() {
        friendInfoView = null;
    }

    @Override
    public void init(Intent intent) {
        classify = intent.getIntExtra("classify", Constant.STRANGER);//默认是陌生人
        friend = (Friend) intent.getExtras().get("friend");
        if(friend!=null)
            friendInfoView.showInfo(friend);
        friendInfoView.showBtn(classify);

        Log.e("TAG", "onClick: "+friend.toString());
    }

    @Override
    public void sendMsg() {
        friendInfoView.jumpToActivity(MessageActivity.class,true,friend);
    }

    @Override
    public void addFriend() {
        Map<String,String> map = new HashMap<>();
        map.put("token",friendInfoView.getUser().getToken());
        map.put("UserID",friend.getUserID());

        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!friendInfoView.isNetworkAvailable())
                    friendInfoView.showErrorMessage("网络不可用");
                else
                    friendInfoView.showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    //申请成功
                        friendInfoView.showSuccessMessage("申请成功");
                        return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    friendInfoView.showErrorMessage(chatResponse.getResponseMsg());
                else
                    friendInfoView.showErrorMessage("未知错误");
            }
        }).post(UriConstant.AddFriend, map);
    }
}
