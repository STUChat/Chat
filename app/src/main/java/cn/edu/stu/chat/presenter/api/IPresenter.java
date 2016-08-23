package cn.edu.stu.chat.presenter.api;

import cn.edu.stu.chat.view.api.MvpView;

/**
 * Created by dell on 2016/8/23.
 */
public interface IPresenter {
    public void attach(MvpView view);
    public void deAttach();
}
