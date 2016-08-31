package cn.edu.stu.chat.presenter;

import android.content.Intent;

import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IFriendInfoPresenter;
import cn.edu.stu.chat.view.activity.FriendInfoActivity;
import cn.edu.stu.chat.view.activity.MessageActivity;
import cn.edu.stu.chat.view.api.IFriendInfoView;
import cn.edu.stu.chat.view.api.MvpView;

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
    }

    @Override
    public void sendMsg() {
        friendInfoView.jumpToActivity(MessageActivity.class,true,friend);
    }

    @Override
    public void addFriend() {

    }
}
