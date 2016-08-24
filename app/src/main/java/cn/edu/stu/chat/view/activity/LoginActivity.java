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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.presenter.api.ILoginPresenter;
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
        presenter = new ILoginPresenter();
        presenter.attach(this);
        presenter.init();
        ButterKnife.bind(this);
    }

    private void initView() {
        setToolbar(R.id.toolbar);
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
        presenter.login(nick,pwd);
    }
    @OnClick(R.id.login_eye)
    void watchPassword(){
        presenter.clickEye();
    }

}
