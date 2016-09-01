package cn.edu.stu.chat.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.utils.RegexValidateUtil;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.observers.SafeSubscriber;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_bn)
    Button registerButton;
    @BindView(R.id.register_email_edit)
    EditText emailEdit;
    @BindView(R.id.register_name_edit)
    EditText nameEdit;
    @BindView(R.id.register_pwd_edit)
    EditText pwdEdit;
    @BindView(R.id.register_confirm_edit)
    EditText confirmEdit;

    @OnClick(R.id.register_bn)
    void checkUser(){
        if(emailEdit.getText().toString().equals("")||nameEdit.getText().toString().equals("")||
                pwdEdit.getText().toString().equals("")||confirmEdit.getText().toString().equals("")){
            showErrorMessage("注冊信息不能为空");
            return;
        }
        if(!RegexValidateUtil.checkEmail(emailEdit.getText().toString())){
            showErrorMessage("請輸入正確的郵箱");
            return;
        }
        if(!pwdEdit.getText().toString().equals(confirmEdit.getText().toString())){
            showErrorMessage("兩次密碼不相同");
            return;
        }
        register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar(R.id.toolbar);
        setTitle(Constant.RegisterTitle);
        ButterKnife.bind(this);
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.RegisterTitle,message);
    }

    private void register(){
        //注册成功后跳转到主界面
        HashMap<String,String> map = new HashMap<>();
        map.put("email",emailEdit.getText().toString());
        map.put("password",pwdEdit.getText().toString());
        map.put("name",nameEdit.getText().toString());
        map.put("gender","0");
        HttpMethods.getInstance().subscribe(new Subscriber<ChatResponse>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(RegisterActivity.this))
                    showErrorMessage("网络不可用");
                else
                    showErrorMessage(e.getMessage());
            }

            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    //申请成功
                    showSucessDialog("注冊成功");
                    return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    showErrorMessage(chatResponse.getResponseMsg());
                else
                    showErrorMessage("未知错误");
            }

        }).post("regist.aspx",map);
    }
    private void showSucessDialog(String title){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setConfirmText("衷心谢谢")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                }).show();
    }
}
