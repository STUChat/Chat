package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.presenter.WelcomePresenter;
import cn.edu.stu.chat.utils.SharedPreferencesHelper;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.IWelcomeView;

public class WelcomeActivity extends Activity implements IWelcomeView {
    private Button button;
    private ChatApp app;
    private WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        button = (Button)findViewById(R.id.welcome_button);
        app = (ChatApp) getApplicationContext();
        presenter = new WelcomePresenter();
        presenter.attach(this);
        //要记得删除，这里是为了能进入主界面进行调试***********************************************************
//        SharedPreferencesHelper.setParam(this,"username","lawliex");
//        SharedPreferencesHelper.setParam(this,"password","lawliex");

        //要记得删除，这里是为了能进入主界面进行调试***********************************************************

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                   // Thread.sleep(Constant.WELCOME_JUMP_TIME);
                    if(presenter.login()){
                        jumpToActivity(MainActivity.class);
                    }else{
                        jumpToActivity(LoginActivity.class);
                    }


                }catch (Exception e){
                    ToastHelper.showToast(WelcomeActivity.this, "error");
                }finally {
                    WelcomeActivity.this.finish();
                }
            }
        }).start();
    }


    @Override
    public String getUsername() {
        String username = (String) SharedPreferencesHelper.getParam(this,"username","");
        return username;
    }

    @Override
    public String getPassword() {
        String password = (String) SharedPreferencesHelper.getParam(this,"password","");
        return password;
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();
    }
}
