package cn.edu.stu.chat.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UserInfo;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;

import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.api.ILoginView;
import cn.edu.stu.chat.view.api.MvpView;

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
     * 登陆---目前测试--直接跳转主页
     * @param nick
     * @param pwd
     */
    public void login(String nick,String pwd){
        HashMap<String, String> map = new HashMap<>();
        map.put("nick",nick);
        map.put("pwd",pwd);
        ChatResponse response = HttpMethods.getInstance()
                .baseUrl(Constant.HOST)
                .get(Constant.LOGIN2,null);
        if(response != null) {
            Log.e("lawliex", response.getResponseCode());
            List<UserInfo> list = JsonHelper.getResponseList(response,UserInfo.class);
            for (UserInfo user:list){
                Log.e(TAG, "login: "+user.toString() );
            }
        }else
            Log.e("lawliex","null");
        loginView.jumpToActivity(MainActivity.class);
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
