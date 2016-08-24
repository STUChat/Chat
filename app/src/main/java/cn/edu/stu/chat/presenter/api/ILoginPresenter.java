package cn.edu.stu.chat.presenter.api;

import android.app.Activity;

import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.api.ILoginView;
import cn.edu.stu.chat.view.api.MvpView;

/**
 * Created by dell on 2016/8/23.
 */
public class ILoginPresenter implements IPresenter {
    private ILoginView loginView;
    private boolean isCloingEye;

    @Override
    public void attach(MvpView view) {
        this.loginView = (ILoginView)view;
    }

    @Override
    public void deAttach() {
        this.loginView = null;
    }

    public void init(){
        loginView.setTitle(Constant.LoginTitle);
        isCloingEye = true;

    }

    /**
     * 登陆---目前测试--直接跳转主页
     * @param nick
     * @param pwd
     */
    public void login(String nick,String pwd){
        loginView.jumpToActivity(MainActivity.class);
        ((Activity)loginView).finish();
    }

    public void clickEye(){
        if(isCloingEye==true){
            loginView.openEye();
            isCloingEye=false;
        }else{
            loginView.closeEye();
            isCloingEye=true;
        }
    }
}
