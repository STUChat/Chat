package cn.edu.stu.chat.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

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
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * Created by cheng on 16-8-29.
 */
public class FeedBackActivity extends BaseActivity {
    private static String TAG = "FeedBackActivity";
    @BindView(R.id.feed_back_edit)
    EditText editText;
    @BindView(R.id.feed_back_confirm)
    Button confrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setToolbar(R.id.toolbar);
        setTitle(Constant.FeedBackTitle);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.feed_back_confirm)
    public void feedBack(){
        if(editText.getText()==null || editText.getText().toString().equals("")){
            ToastHelper.showErrorDialog(this,Constant.FeedBackTitle,"内容不能为空");
            return;
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("token",((ChatApp)getApplication()).getUser().getToken());
        map.put("content",editText.getText().toString());
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(FeedBackActivity.this))
                    showErrorMessage("网络不可用");
                else
                    showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                    showSucessDialog("我们已经收到您的反馈!");
                    return;
                }
                if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                    showErrorMessage(chatResponse.getResponseMsg());
                else
                    showErrorMessage("未知错误");
            }
        }).post(UriConstant.FeedBack,map);
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.ChangePwdTitle,message);
    }

    private void showSucessDialog(String title){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setConfirmText("衷心谢谢")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).show();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
