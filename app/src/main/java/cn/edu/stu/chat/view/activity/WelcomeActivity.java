package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.activity_welcome);
        button = (Button)findViewById(R.id.welcome_button);
        app = (ChatApp) getApplicationContext();
        presenter = new WelcomePresenter();
        presenter.attach(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(Constant.WELCOME_JUMP_TIME);
                    presenter.login();
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
