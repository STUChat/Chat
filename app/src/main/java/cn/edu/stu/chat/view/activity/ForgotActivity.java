package cn.edu.stu.chat.view.activity;

import android.os.Bundle;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.view.api.BaseActivity;

/**
 * Created by dell on 2016/8/22.
 */
public class ForgotActivity extends BaseActivity {

    private static String TAG = "ForgotActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
