package cn.edu.stu.chat.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.view.activity.ChanagePwdActivity;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.UserInfoActivity;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.BaseFragment;
import cn.edu.stu.chat.view.widget.CircleImageView;

/**
 * Created by cheng on 16-8-22.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout userLayout;
    private LinearLayout logoutLayout;
    private CircleImageView photoImage;
    private TextView nameTextView;
    private ImageView genderImage;
    private User user;
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
        initData();
        return view;
    }

    private void initView(View view) {
        userLayout =(LinearLayout)view.findViewById(R.id.mine_user);
        logoutLayout = (LinearLayout)view.findViewById(R.id.mine_logout);
        nameTextView = (TextView)view.findViewById(R.id.mine_name);
        photoImage = (CircleImageView)view.findViewById(R.id.mine_photo);
        genderImage = (ImageView)view.findViewById(R.id.mine_gender_image);
        userLayout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);
    }

    private void initData(){
        user = ((ChatApp)(getActivity().getApplication())).getUser();
        nameTextView.setText(user.getName());
        if(user.getGender().equals("1")) {//男
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setImageResource(R.mipmap.gender_male);
        }else if(user.getGender().equals("2")) {//女
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setImageResource(R.mipmap.gender_female);
        }else{
            genderImage.setVisibility(View.GONE);
        }
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
            case R.id.mine_change_pwd://修改密码
                changePwd();
                break;
        }
    }

    private void changePwd() {
        getActivity().startActivity(new Intent(getContext(), ChanagePwdActivity.class));
    }

    public void checkUserInfo(){
        getActivity().startActivity(new Intent(getContext(), UserInfoActivity.class));
    }

    public void userLogout(){
        ((BaseActivity)getActivity()).logout();
        jumpToActivity(LoginActivity.class);
        getActivity().finish();
    }

    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
