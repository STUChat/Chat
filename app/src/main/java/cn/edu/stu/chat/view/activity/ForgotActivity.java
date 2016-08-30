package cn.edu.stu.chat.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/22.
 */
public class ForgotActivity extends BaseActivity {
    private static String TAG = "ForgotActivity";

    @BindView(R.id.forgot_pwd_email)
    EditText emailEdit;
    @BindView(R.id.forgot_confirm_btn)
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        setToolbar(R.id.toolbar);
        setTitle(Constant.ForgotPwdTitle);
        showToolbarLeftBtn(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.forgot_confirm_btn)
    public void sendEmail(){
        if(emailEdit.getText()==null || emailEdit.getText().toString().equals("")) {
            showErrorMessage("不能为空");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("email",emailEdit.getText().toString());
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(ForgotActivity.this))
                    showErrorMessage("网络不可用");
                else
                    showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    showSucessDialog("新密码已发送您的邮箱，请登陆后修改密码");
                    return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    showErrorMessage(chatResponse.getResponseMsg());
                else
                    showErrorMessage("未知错误");
            }
        }).post(UriConstant.ForgotPwd,map);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.ForgotPwdTitle,message);
    }

    private void showSucessDialog(String title){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                }).show();
    }
}
