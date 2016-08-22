package cn.edu.stu.chat.presenter;

import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IWelcomePresenter;
import cn.edu.stu.chat.view.api.IWelcomeView;

/**
 * Created by Terence on 2016/8/22.
 */
public class WelcomePresenter implements IWelcomePresenter {
    private User user;
    private IWelcomeView welcomeView;
    private String username;
    private String password;
    public WelcomePresenter(IWelcomeView welcomeView){
        this.welcomeView = welcomeView;
    }
    @Override
    public boolean isSavePassword() {
        username = welcomeView.getUsername();
        password = welcomeView.getPassword();
        if(username.length() == 0 || password.length() == 0)
            return false;
        return true;
    }

    @Override
    public boolean login() {
        /*
         *返回是否登录成功
         */
        if(isSavePassword()){
            /*

            some network operation
            do login...


             */
            //模拟登陆操作*****************************************
            user = new User();
            user.setLoginState(true);
            //模拟登陆操作*****************************************

            if(user.isLoginState())
                return true;
        }
        return false;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
