package cn.edu.stu.chat.presenter;

import android.app.Activity;

import java.util.Map;

import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.HttpApi;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.activity.MainActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Terence on 2016/8/24.
 * Get和Post方法返回的值有可能为null，代表请求不成功
 * 调用方法
 * HttpMethods.getInstance().baseUrl(url).subscribe(subscriber).get(path, map);
 * 或者
 * HttpMethods.getInstance().get(path, map);
 */
public class HttpMethods {
    private static String baseUrl = Constant.HOST;
    volatile private static HttpMethods instance = null;
    private Observable<ChatResponse> observable;
    private Subscriber subscriber;
    private HttpApi httpService;
    private ChatResponse response;
    public static HttpMethods getInstance() {
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null)
                    instance = new HttpMethods();
            }
        }
        return instance;
    }
    private HttpMethods(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
        //默认subscriber
        subscriber = new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                response = null;
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                response = chatResponse;
            }
        };
    }

    //Get和Post方法返回的值有可能为null，代表请求不成功
    public ChatResponse post(String path, Map<String, String> map) {
        response = null;
        if(map != null)
            observable = httpService.post(path, map);
        else
            observable = httpService.post(path);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return  response;
    }

    //Get和Post方法返回的值有可能为null，代表请求不成功
    public ChatResponse get(String path, Map<String,String> map) {
        response = null;
        if(map != null)
            observable = httpService.get(path, map);
        else
            observable = httpService.get(path);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return  response;
    }
    public HttpMethods baseUrl(String url){
        baseUrl = url;
        return instance;
    }
    public HttpMethods subscribe(Subscriber<ChatResponse> subscriber){
        this.subscriber = subscriber;
        return instance;
}
}
