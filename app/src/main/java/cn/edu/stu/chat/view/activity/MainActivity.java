package cn.edu.stu.chat.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.client.MessageService;
import cn.edu.stu.chat.utils.ResidentNotificationHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.fragment.ContactFragment;
import cn.edu.stu.chat.view.fragment.MessagingFragment;
import cn.edu.stu.chat.view.fragment.MineFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout messagingLayout, contactLayout, mineLayout;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment(savedInstanceState);
        initView();
        Intent intent = new Intent(this, MessageService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",((ChatApp)getApplication()).getUser());
        intent.putExtras(bundle);
        startService(intent);
        notificationChange(getIntent());
    }

    private void initFragment(Bundle savedInstanceState)
    {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if(savedInstanceState==null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if(fragment==null) {
                fragment = new MessagingFragment();
            }
            ft.replace(R.id.tab_fragment,fragment).commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        notificationChange(intent);
    }

    /**
     * 点击通知栏后清除通知
     * @param intent
     */
    private void notificationChange(Intent intent) {
        int noticeId = intent.getIntExtra(ResidentNotificationHelper.NOTICE_ID_KEY, -1);
        if(noticeId != -1){
            ResidentNotificationHelper.clearNotification(MainActivity.this, noticeId);
        }
    }

    private void initView() {
        messagingLayout = (LinearLayout) findViewById(R.id.tab_messaging);
        contactLayout = (LinearLayout) findViewById(R.id.tab_contact);
        mineLayout = (LinearLayout) findViewById(R.id.tab_mine);
        messagingLayout.setOnClickListener(this);
        contactLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        findViewById(R.id.image_messaging).setSelected(true);
        findViewById(R.id.text_messaging).setSelected(true);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        initFragmentState();
        switch (view.getId()){
            case R.id.tab_messaging:
                findViewById(R.id.image_messaging).setSelected(true);
                findViewById(R.id.text_messaging).setSelected(true);
                fragment = new MessagingFragment();
                break;
            case R.id.tab_contact:
                findViewById(R.id.image_contact).setSelected(true);
                findViewById(R.id.text_contact).setSelected(true);
                fragment = new ContactFragment();
                break;
            case R.id.tab_mine:
                findViewById(R.id.image_mine).setSelected(true);
                findViewById(R.id.text_mine).setSelected(true);
                fragment = new MineFragment();
                break;
        }
        ft.replace(R.id.tab_fragment, fragment).commit();
    }



    private void initFragmentState() {
        findViewById(R.id.image_messaging).setSelected(false);
        findViewById(R.id.text_messaging).setSelected(false);
        findViewById(R.id.image_contact).setSelected(false);
        findViewById(R.id.text_contact).setSelected(false);
        findViewById(R.id.image_mine).setSelected(false);
        findViewById(R.id.text_mine).setSelected(false);
    }
}
