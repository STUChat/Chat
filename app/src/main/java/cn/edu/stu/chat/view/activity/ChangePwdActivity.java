package cn.edu.stu.chat.view.activity;

import android.os.Bundle;
import android.util.Log;
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
 * Created by dell on 2016/8/29.
 */
public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.change_pwd_old_pwd)
    EditText oldPwd;
    @BindView(R.id.change_pwd_new_pwd)
    EditText newPwd;
    @BindView(R.id.change_pwd_repeat_pwd)
    EditText repeatPwd;
    @BindView(R.id.change_pwd_confirm)
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        setToolbar(R.id.toolbar);
        setTitle(Constant.ChangePwdTitle);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.change_pwd_confirm)
    public void changePwd(){
        String old_pwd = oldPwd.getText().toString();
        String repeat_pwd = repeatPwd.getText().toString();
        String new_pwd = newPwd.getText().toString();
        if(old_pwd==null || repeat_pwd ==null || new_pwd==null ||old_pwd.equals("") || repeat_pwd.equals("") || new_pwd.equals("")){
            showErrorMessage("不能为空");
            return;
        }
        if(!repeat_pwd.equals(new_pwd)) {
            showErrorMessage("两次密码不相同");
            return;
        }
        Log.e("TAG",repeat_pwd+"  "+new_pwd);
        User user = ((ChatApp)getApplication()).getUser();
        Map<String,String> map = new HashMap<>();
        map.put("token",user.getToken());
        map.put("password",old_pwd);
        map.put("newPassword",new_pwd);
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(ChangePwdActivity.this))
                   showErrorMessage("网络不可用");
                else
                   showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    showSucessDialog("修改成功");
                    return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                   showErrorMessage(chatResponse.getResponseMsg());
                else
                   showErrorMessage("未知错误");
            }
        }).post(UriConstant.ChangePass,map);
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.ChangePwdTitle,message);
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
