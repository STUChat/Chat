package cn.edu.stu.chat.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IWelcomePresenter;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.api.IWelcomeView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by Terence on 2016/8/22.
 */
public class WelcomePresenter implements IWelcomePresenter {
    public static String TAG ="welcomePresenter";
    private IWelcomeView welcomeView;
    private String username;
    private String password;
    public WelcomePresenter(){

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
    public void login() {
        if(welcomeView.isLogin()){ //已经登陆
            welcomeView.jumpToActivity(MainActivity.class);
        }
        else if(isSavePassword()){  //保存了密码，未登陆
            Map<String,String> map = new HashMap<>();
            map.put("email",username);
            map.put("password",password);
            HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable e) {
                    welcomeView.jumpToActivity(LoginActivity.class);
                    Log.e(TAG, "onError: "+e.getMessage() );
                }
                @Override
                public void onNext(ChatResponse chatResponse) {
                    if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                        //登陆成功
                        User user = JsonHelper.getResponseValue(chatResponse, User.class);
                        if (user != null) {
                            user.setLoginTime(System.currentTimeMillis());
                            user.setToken(chatResponse.getResponseToken());
                            welcomeView.setUser(user);//保存用户
                            Log.e(TAG, user.toString());
                        }
                        if (welcomeView.isLogin()) {
                            welcomeView.jumpToActivity(MainActivity.class);
                            Log.e(TAG, user.toString());
                            return;
                        }
                    }
                    welcomeView.jumpToActivity(LoginActivity.class);
                }
            }).post(UriConstant.LOGIN,map);
        }
        else  //未保存密码
            welcomeView.jumpToActivity(LoginActivity.class);
    }

    @Override
    public void attach(MvpView view) {
        this.welcomeView = (IWelcomeView)view;
    }

    @Override
    public void deAttach() {
        this.welcomeView = null;
    }
}
