package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.WelcomePresenter;
import cn.edu.stu.chat.utils.SharedPreferencesHelper;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.IWelcomeView;

public class WelcomeActivity extends BaseActivity implements IWelcomeView {
    private Button button;
    private ChatApp app;
    private WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().
                setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        setContentView(R.layout.activity_welcome);
        button = (Button)findViewById(R.id.welcome_button);
        app = (ChatApp) getApplicationContext();
        presenter = new WelcomePresenter();
        presenter.attach(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.login();
            }
        },Constant.WELCOME_JUMP_TIME);

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
        finish();
    }

    @Override
    public void setUser(User user) {
        ((ChatApp)getApplication()).setUser(user);
    }

    @Override
    public boolean isLogin(){
        return super.isLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();
    }

}
