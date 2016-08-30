package cn.edu.stu.chat.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.UserInfoPresenter;
import cn.edu.stu.chat.presenter.api.IUserInfoPresenter;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.IUserInfoView;

/**
 * Created by dell on 2016/8/25.
 */
public class UserInfoActivity extends BaseActivity implements IUserInfoView{
    private IUserInfoPresenter userInfoPresenter;
    @BindView(R.id.user_info_name)
    TextView nameTextview;
    @BindView(R.id.user_info_gender)
    TextView genderTextview;
    @BindView(R.id.user_info_motto)
    TextView mottoTextview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        userInfoPresenter = new UserInfoPresenter();
        userInfoPresenter.attach(this);
        userInfoPresenter.init();
    }

    private void initView() {
        setToolbar(R.id.toolbar);
        showToolbarLeftBtn(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void setUser(User user) {
        ((ChatApp)getApplication()).setUser(user);
    }

    @Override
    public User getUser() {
        return ((ChatApp)getApplication()).getUser();
    }

    @Override
    public void showUserInfo(User user) {
        nameTextview.setText(user.getName());
        if(user.getGender().equals("0")) genderTextview.setText("保密");
        if(user.getGender().equals("1")) genderTextview.setText("男");
        if(user.getGender().equals("2")) genderTextview.setText("女");
        mottoTextview.setText(user.getMotto());
    }

    @Override
    public boolean isNetworkAvialable() {
        return NetworkHelper.isNetworkAvailable(this);
    }

    @Override
    public void showFailDialog(String title, String message) {
        ToastHelper.showErrorDialog(this,title,message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresenter.deAttach();
    }

    @OnClick(R.id.user_info_name_layout)
    public void updateName() {
        Intent intent = new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class);
        intent.putExtra("id", 0);
        startActivity(intent);
    }
    @OnClick(R.id.user_info_gender_layout)
    public void updateGender(){
        showGenderDailog();
    }
    @OnClick(R.id.user_info_motto_layout)
    public void updateMotto(){
        Intent intent = new Intent(UserInfoActivity.this,UpdateUserInfoActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
    }

    private void showGenderDailog(){
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("性别")
                .setSingleChoiceItems(
                        new String[]{"保密","男","女"}, Integer.parseInt(getUser().getGender()),
                        new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             userInfoPresenter.upateGender(which);
                             dialog.dismiss();
                          }
                         }).create();
        dialog.show();
    }

    public void setTitle(String title){
        super.setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoPresenter.init();
    }
}
