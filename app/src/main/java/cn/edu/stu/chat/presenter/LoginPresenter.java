package cn.edu.stu.chat.presenter;

import android.util.Log;

import java.util.HashMap;

import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;

import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.api.ILoginView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Observable;
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
                .get("Test2.aspx",map);
        if(response != null)
            Log.e("lawliex",response.getResponseCode()+"111");
        else
            Log.e("lawliex","null");


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
