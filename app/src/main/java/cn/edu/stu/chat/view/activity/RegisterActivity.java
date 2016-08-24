package cn.edu.stu.chat.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.utils.ToastHelper;

public class RegisterActivity extends Activity{
    private View toolbar;
    private TextView title;

    @BindView(R.id.register_bn)
    Button registerButton;

    @OnClick(R.id.register_bn)
    void register(){
        //注册成功后跳转到主界面
        /**
         *
         */
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (View)findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(Constant.RegisterTitle);
        ButterKnife.bind(this);
    }
}
