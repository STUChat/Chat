package cn.edu.stu.chat.presenter;

import android.content.Intent;
import android.text.InputFilter;

import java.util.HashMap;
import java.util.Map;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IUpdateUserInfoPresenter;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.activity.UpdateUserInfoActivity;
import cn.edu.stu.chat.view.api.IUpdateUserInfoView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/29.
 */
public class UpdateUserInfoPresenter implements IUpdateUserInfoPresenter {
    private IUpdateUserInfoView updateUserInfoView;
    private User user;
    private String oldContent;
    private String title;
    private int id;//0 代表修改昵称，1代表修改个性签名

    @Override
    public void attach(MvpView view) {
        updateUserInfoView = (IUpdateUserInfoView)view;
    }

    @Override
    public void deAttach() {
        updateUserInfoView = null;
    }

    @Override
    public void init(Intent intent) {
        id = intent.getIntExtra("id",-1);
        user = updateUserInfoView.getUser();
        if(id==0){
            oldContent =user.getName();
            title = Constant.ChangeUserInfoNameTitle;
            updateUserInfoView.showInfo(0,oldContent);

        }else if(id==1){
            oldContent =user.getMotto();
            updateUserInfoView.showInfo(1,oldContent);
            title = Constant.ChangeUserInfoMottoTitle;
        }
        updateUserInfoView.setTitle(title);
    }

    @Override
    public void update(String newContent) {
        if(newContent==null || newContent.equals(""))
            updateUserInfoView.showFailDialog(title,"不能为空");
        else if(newContent.equals(oldContent))
            updateUserInfoView.showFailDialog(title,"不能与原来相同");
        else {
            if(id==0){
                user.setName(newContent);
            }else if(id==1){
                user.setMotto(newContent);
            }
            Map<String,String> map = new HashMap<>();
            map.put("token",user.getToken());
            map.put("name",user.getName());
            map.put("gender",user.getGender());
            map.put("motto",user.getMotto());
            HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
                @Override
                public void onCompleted() {

                }
                @Override
                public void onError(Throwable e) {
                    if(!updateUserInfoView.isNetworkAvialable())
                        updateUserInfoView.showFailDialog(title,"网络不可用");
                    else
                        updateUserInfoView.showFailDialog(title,e.getMessage());
                }
                @Override
                public void onNext(ChatResponse chatResponse) {
                    if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                        updateUserInfoView.setUser(user);
                        updateUserInfoView.showSucessDialog(title);
                        return;
                    }
                    if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                        updateUserInfoView.showFailDialog(title,chatResponse.getResponseMsg());
                    else
                        updateUserInfoView.showFailDialog(title,"未知错误");
                }
            }).post(UriConstant.UpdateUserInfo,map);
        }
    }
}
