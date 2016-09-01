package cn.edu.stu.chat.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.UserInfoPresenter;
import cn.edu.stu.chat.presenter.api.IUserInfoPresenter;
import cn.edu.stu.chat.utils.GlideLoader;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.IUserInfoView;
import cn.edu.stu.chat.view.widget.CircleImageView;

/**
 * Created by dell on 2016/8/25.
 */
public class UserInfoActivity extends BaseActivity implements IUserInfoView{
    private static final int REQUEST_CODE = 1000;
    private IUserInfoPresenter userInfoPresenter;
    private ArrayList<String> path = new ArrayList<>();

    @BindView(R.id.user_info_name)
    TextView nameTextview;
    @BindView(R.id.user_info_gender)
    TextView genderTextview;
    @BindView(R.id.user_info_motto)
    TextView mottoTextview;
    @BindView(R.id.user_info_photo)
    ImageView photo;

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
    @OnClick(R.id.user_info_photo_layout)
    public void updatePhoto(){
        ImageConfig imageConfig
                = new ImageConfig.Builder(
                // GlideLoader 可用自己用的缓存库
                new GlideLoader())
                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                // 标题的背景颜色 （默认黑色）
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                // 提交按钮字体的颜色  （默认白色）
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                // 标题颜色 （默认白色）
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）  (单选 为 singleSelect)
                .singleSelect()
                .crop()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(1)
                // 已选择的图片路径
                .pathList(path)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/ImageSelector/Pictures")
                // 开启拍照功能 （默认开启）
                .showCamera()
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(UserInfoActivity.this, imageConfig);   // 开启图片选择器
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (String path : pathList) {
                Log.e("ImagePathList", path);
            }

            if(path!=null && !path.isEmpty()) {
                Glide.with(this).load(Uri.fromFile(new File(path.get(0))))
                        .into(photo);
            }
            path.clear();
        }
    }
}
