package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.ILoginView;

/**
 * Created by dell on 2016/8/22.
 */
public class LoginActivity extends BaseActivity implements ILoginView,View.OnClickListener{
    private EditText nickEdit;
    private EditText pwdEdit;
    private ImageView eyeView;
    private ILoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        presenter = new ILoginPresenter();
        presenter.attach(this);
        presenter.init();
    }

    private void initView() {
        setToolbar(R.id.toolbar);
        nickEdit = (EditText)findViewById(R.id.login_nick_edit);
        pwdEdit = (EditText)findViewById(R.id.login_pwd_edit);
        eyeView = (ImageView)findViewById(R.id.login_eye);
        eyeView.setOnClickListener(this);
        findViewById(R.id.login_forgot_btn).setOnClickListener(this);
        findViewById(R.id.login_register_btn).setOnClickListener(this);
        findViewById(R.id.login_landing_btn).setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setMessage(String text) {

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
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_forgot_btn:
                jumpToActivity(ForgotActivity.class);
                break;
            case R.id.login_register_btn:
                jumpToActivity(RegisterActivity.class);
                break;
            case R.id.login_eye:
                presenter.clickEye();
                break;
            case R.id.login_landing_btn:
                String nick = nickEdit.getText().toString();
                String pwd = pwdEdit.getText().toString();
                presenter.login(nick,pwd);
                break;
        }
    }
}
