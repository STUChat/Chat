package cn.edu.stu.chat.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.presenter.FriendInfoPresenter;
import cn.edu.stu.chat.presenter.api.IFriendInfoPresenter;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.IFriendInfoView;

/**
 * Created by cheng on 16-8-30.
 */
public class FriendInfoActivity extends BaseActivity implements IFriendInfoView{

    IFriendInfoPresenter presenter;
    @BindView(R.id.friend_info_photo)
    ImageView photoImageview;
    @BindView(R.id.frined_info_name)
    TextView nameTextview;
    @BindView(R.id.frined_info_gender_image)
    ImageView genderImage;
    @BindView(R.id.friend_info_motto)
    TextView mottoTextview;
    @BindView(R.id.friend_info_message_btn)
    Button messagebtn;
    @BindView(R.id.friend_info_add_friend_btn)
    Button addfriendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);
        setToolbar(R.id.toolbar);
        setTitle(Constant.FriendInfoTitle);
        presenter = new FriendInfoPresenter();
        presenter.attach(this);
        Intent intent = getIntent();
        presenter.init(intent);
        showToolbarLeftBtn(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void showInfo(Friend friend) {
        nameTextview.setText(friend.getName());
        if(friend.getGender().equals("1")) {
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setImageResource(R.mipmap.gender_male);
        }else if(friend.getGender().equals("2")) {
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setImageResource(R.mipmap.gender_female);
        }else {
            genderImage.setVisibility(View.INVISIBLE);
        }
        mottoTextview.setText(friend.getMotto());
    }

    @Override
    public void showBtn(int classify) {
        if(classify == Constant.STRANGER){
            messagebtn.setVisibility(View.GONE);
            addfriendbtn.setVisibility(View.VISIBLE);
        }else{
            messagebtn.setVisibility(View.VISIBLE);
            addfriendbtn.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.friend_info_message_btn)
    public void sendMsg(){
        presenter.sendMsg();
    }

    @OnClick(R.id.friend_info_add_friend_btn)
    public void addFriend(){
        presenter.addFriend();
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this, Constant.FriendInfoTitle,message);
    }

    public void showSuccessMessage(String message){
        ToastHelper.showDialog(this,Constant.FriendInfoTitle,message);
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkHelper.isNetworkAvailable(this);
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass, Boolean isFinish) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        if(isFinish)
            finish();
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass, Boolean isFinish,Friend friend) {
        Intent intent = new Intent(this, activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("friend",friend);
        intent.putExtras(bundle);
        startActivity(intent);
        if(isFinish)
            finish();
    }
}