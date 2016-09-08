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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.view.activity.ChangePwdActivity;
import cn.edu.stu.chat.view.activity.FeedBackActivity;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.UserInfoActivity;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.BaseFragment;

/**
 * Created by cheng on 16-8-22.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_photo)
    ImageView photoImage;
    @BindView(R.id.mine_name)
    TextView nameTextView;
    @BindView(R.id.mine_gender_image)
    ImageView genderImage;

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
        ButterKnife.bind(this, view);
        initData();
        return view;
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
        Glide.with(this)
                .load(user.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.default_photo)
                .into(photoImage);
    }

    @OnClick(R.id.mine_change_pwd)
    public void changePwd() {
        getActivity().startActivity(new Intent(getContext(), ChangePwdActivity.class));
    }

    @OnClick(R.id.mine_user)
    public void checkUserInfo(){
        getActivity().startActivity(new Intent(getContext(), UserInfoActivity.class));
    }

    @OnClick(R.id.mine_logout)
    public void logout(){
        ((BaseActivity)getActivity()).logout();
        jumpToActivity(LoginActivity.class);
        getActivity().finish();
    }

    @OnClick(R.id.mine_feedback)
    public void feedBack(){
        jumpToActivity(FeedBackActivity.class);
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
