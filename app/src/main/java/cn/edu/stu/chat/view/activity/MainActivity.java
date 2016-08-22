package cn.edu.stu.chat.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.stu.chat.R;

public class MainActivity extends AppCompatActivity {
    private Button toRegister;
    private Button toLogin;
    private Button toForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toRegister = (Button)findViewById(R.id.to_register);
        toLogin =  (Button)findViewById(R.id.to_login);
        toForgot =  (Button)findViewById(R.id.to_forgot);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        toForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });
    }
}
