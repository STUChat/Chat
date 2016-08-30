package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.LoginPresenter;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;
import cn.edu.stu.chat.utils.ResidentNotificationHelper;
import cn.edu.stu.chat.utils.SharedPreferencesHelper;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.ILoginView;

/**
 * Created by dell on 2016/8/22.
 */
public class LoginActivity extends BaseActivity implements ILoginView{
    @BindView(R.id.login_nick_edit)
    EditText nickEdit;
    @BindView(R.id.login_pwd_edit)
    EditText pwdEdit;
    @BindView(R.id.login_eye)
    ImageView eyeView;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        presenter = new LoginPresenter();
        presenter.attach(this);
        presenter.init();
        ButterKnife.bind(this);
    }

    private void initView() {
        setToolbar(R.id.toolbar);
        String username = (String) SharedPreferencesHelper.getParam(this,"username","");
        String password = (String) SharedPreferencesHelper.getParam(this,"password","");
        if(!username.equals("") && !password.equals("")) {
            ((EditText)findViewById(R.id.login_nick_edit)).setText(username);
            ((EditText)findViewById(R.id.login_pwd_edit)).setText(password);
        }
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void saveUsernamePwd(String username,String password) {
        SharedPreferencesHelper.setParam(this,"username",username);
        SharedPreferencesHelper.setParam(this,"password",password);
    }

    @Override
    public void showErrorMessage(String text){
        findViewById(R.id.login_landing_btn).setClickable(true);
        ToastHelper.showErrorDialog(this,Constant.LoginTitle,text);
    }

    @Override
    public void openEye(){
        eyeView.setImageResource(R.mipmap.landing_open_eye_image);
        pwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        pwdEdit.postInvalidate();
    }

    @Override
    public void closeEye(){
        eyeView.setImageResource(R.mipmap.landing_close_eye_image);
        pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pwdEdit.postInvalidate();
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
    public boolean isNetworkAvailable() {
        return NetworkHelper.isNetworkAvailable(this);
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();
    }

    @OnClick(R.id.login_forgot_btn)
    void forgotPassword(){
        jumpToActivity(ForgotActivity.class);
    }
    @OnClick(R.id.login_register_btn)
    void toRegister(){
        jumpToActivity(RegisterActivity.class);
    }
    @OnClick(R.id.login_landing_btn)
    void login(){
        String nick = nickEdit.getText().toString();
        String pwd = pwdEdit.getText().toString();
        findViewById(R.id.login_landing_btn).setClickable(false);
        presenter.login(nick,pwd);
    }
    @OnClick(R.id.login_eye)
    void watchPassword(){
        presenter.clickEye();
    }

}
