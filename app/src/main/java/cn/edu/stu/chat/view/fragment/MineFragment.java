package cn.edu.stu.chat.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.UserInfoActivity;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.BaseFragment;

/**
 * Created by cheng on 16-8-22.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout userLayout;
    private LinearLayout logoutLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        userLayout =(LinearLayout)view.findViewById(R.id.mine_user);
        logoutLayout = (LinearLayout)view.findViewById(R.id.mine_logout);
        userLayout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_user://查看用户信息
                checkUserInfo();
                break;
            case R.id.mine_logout://注销
                userLogout();
                break;
        }
    }

    public void checkUserInfo(){
        getActivity().startActivity(new Intent(getContext(), UserInfoActivity.class));
    }

    public void userLogout(){
        ((BaseActivity)getActivity()).logout();
        jumpToActivity(LoginActivity.class);
    }

    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }
}
