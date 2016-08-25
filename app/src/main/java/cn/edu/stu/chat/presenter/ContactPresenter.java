package cn.edu.stu.chat.presenter;

import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.view.api.IContactView;
import cn.edu.stu.chat.view.api.MvpView;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactPresenter implements IContactPresenter {
    IContactView contactView;

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
        //初始化联系人
    }
}
