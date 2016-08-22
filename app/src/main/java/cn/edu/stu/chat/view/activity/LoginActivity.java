package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Constant;

/**
 * Created by dell on 2016/8/22.
 */
public class LoginActivity extends Activity {
    private View toolbar;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (View)findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(Constant.LoginTitle);
    }

}
