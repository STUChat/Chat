package cn.edu.stu.chat.presenter;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.IUserInfoPresenter;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.view.api.IUserInfoView;
import cn.edu.stu.chat.view.api.MvpView;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/29.
 */
public class UserInfoPresenter implements IUserInfoPresenter {
    private IUserInfoView userInfoView;
    private User user;

    public UserInfoPresenter(){
    }

    @Override
    public void attach(MvpView view) {
        userInfoView = (IUserInfoView) view;
    }

    @Override
    public void deAttach() {
        userInfoView = null;
    }

    @Override
    public void init() {
        user = userInfoView.getUser();
        userInfoView.showUserInfo(user);
        userInfoView.setTitle(Constant.UserInfoTitle);
    }

    @Override
    public void upateGender(final int gender) {
            Map<String,String> map = new HashMap<>();
            map.put("token",user.getToken());
            map.put("name",user.getName());
            map.put("gender",gender+"");
            map.put("motto",user.getMotto());
            HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
                @Override
                public void onCompleted() {

                }
                @Override
                public void onError(Throwable e) {
                    if(!userInfoView.isNetworkAvialable())
                        userInfoView.showFailDialog(Constant.UserInfoTitle,"网络不可用");
                    else
                        userInfoView.showFailDialog(Constant.UserInfoTitle,e.getMessage());
                }
                @Override
                public void onNext(ChatResponse chatResponse) {
                    if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {
                        user.setGender(gender+"");
                        userInfoView.showUserInfo(user);
                        return;
                    }
                    if(chatResponse.getResponseMsg()!=null&&!chatResponse.getResponseMsg().equals(""))
                        userInfoView.showFailDialog(Constant.UserInfoTitle,chatResponse.getResponseMsg());
                    else
                        userInfoView.showFailDialog(Constant.UserInfoTitle,"未知错误");
                }
            }).post(UriConstant.UpdateUserInfo,map);
    }

}
