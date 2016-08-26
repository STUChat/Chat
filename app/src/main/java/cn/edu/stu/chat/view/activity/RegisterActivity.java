package cn.edu.stu.chat.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import rx.Subscriber;
import rx.observers.SafeSubscriber;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_bn)
    Button registerButton;

    @OnClick(R.id.register_bn)
    void register(){
        //注册成功后跳转到主界面
        /**
         *
         */
        HashMap<String,String> map = new HashMap<>();
        map.put("name","lawliex");
        map.put("password","xx123");
        map.put("rpassword","xx123");
        map.put("gender","0");
        map.put("headUrl","lawliex");
        map.put("email","570103680@qq.com");

        ChatResponse response= HttpMethods.getInstance().subscribe(new Subscriber<ChatResponse>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastHelper.showToast(RegisterActivity.this,"error");
            }

            @Override
            public void onNext(ChatResponse chatResponse) {
                ToastHelper.showToast(RegisterActivity.this,chatResponse.getResponseMsg());
            }
        }).post("regist.aspx",map);
//        if(response != null){
//
//        }else{
//
//        }
//        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//        startActivity(intent);
//        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar(R.id.toolbar);
        setTitle(Constant.RegisterTitle);
        ButterKnife.bind(this);
    }
}
