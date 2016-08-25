package cn.edu.stu.chat.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.FriendInfo;
import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.view.api.IContactView;
import cn.edu.stu.chat.view.api.MvpView;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactPresenter implements IContactPresenter {
    IContactView contactView;
    List<FriendInfo> list;
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
        list = new ArrayList<>();

        for(int i=0;i<50;i++) {
            FriendInfo friend = new FriendInfo();
            friend.setName("冰冰");
            list.add(friend);
        }
        //初始化联系人
        contactView.showList(list);
    }
}
