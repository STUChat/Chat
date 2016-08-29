package cn.edu.stu.chat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.UpdateUserInfoPresenter;
import cn.edu.stu.chat.presenter.api.IUpdateUserInfoPresenter;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.api.IUpdateUserInfoView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by dell on 2016/8/29.
 */
public class UpdateUserInfoActivity extends BaseActivity implements IUpdateUserInfoView{
    private IUpdateUserInfoPresenter presenter;
    private EditText editText;
    private Button confirm;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_change_user_info);
        Intent intent = getIntent();
        initView();
        presenter = new UpdateUserInfoPresenter();
        presenter.attach(this);
        presenter.init(intent);
    }

    private void initView() {
        editText = (EditText)findViewById(R.id.change_user_info_edit);
        confirm = (Button)findViewById(R.id.change_user_info_confirm);
        setToolbar(R.id.main_toolbar);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.update(editText.getText().toString());
            }
        });
    }

    @Override
    public void showSucessDialog(String title){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                }).show();
    }

    @Override
    public void showInfo(int i,String content) {
        editText.setText(content);
        if(i==0) {//昵称
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        }
    }

    @Override
    public void showFailDialog(String title, String message) {
        ToastHelper.showErrorDialog(UpdateUserInfoActivity.this,title,message);
    }

    @Override
    public boolean isNetworkAvialable() {
        return NetworkHelper.isNetworkAvailable(this);
    }

    @Override
    protected void onDestroy() {
        presenter.deAttach();
        super.onDestroy();
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
    public void setTitle(String title){
        super.setTitle(title);
    }
}
