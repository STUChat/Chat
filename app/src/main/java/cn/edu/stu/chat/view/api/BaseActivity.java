package cn.edu.stu.chat.view.api;


import android.app.Activity;
import android.os.Bundle;

import cn.edu.stu.chat.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
