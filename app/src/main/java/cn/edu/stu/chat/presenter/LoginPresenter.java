package cn.edu.stu.chat.presenter;

import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;

import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.api.ILoginView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/23.
 */
public class LoginPresenter implements ILoginPresenter {
    private static String TAG = "Login";
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
     * 登陆
     * @param username
     * @param password
     */
    public void login(final String username,final String password){
        if(username==null || password==null ||username.equals("")||password.equals("")) {
            loginView.showErrorMessage("昵称或密码不能为空");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("email",username);
        map.put("password",password);

        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!loginView.isNetworkAvailable())
                    loginView.showErrorMessage("网络不可用");
                else
                    loginView.showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    //登陆成功
                    User user = JsonHelper.getResponseValue(chatResponse, User.class);
                    if (user != null) {
                        user.setLoginTime(System.currentTimeMillis());
                        user.setToken(chatResponse.getResponseToken());
                        loginView.setUser(user);//保存用户
                    }
                    if (loginView.isLogin()) {
                        loginView.saveUsernamePwd(username, password);
                        loginView.jumpToActivity(MainActivity.class,true);
                        Log.e(TAG, user.toString() );
                        return;
                    }
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    loginView.showErrorMessage(chatResponse.getResponseMsg());
                else
                    loginView.showErrorMessage("未知错误");
            }
        }).post(UriConstant.LOGIN, map);
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
