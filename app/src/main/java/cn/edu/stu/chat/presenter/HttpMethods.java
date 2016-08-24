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
 */
public class HttpMethods {
    private static String baseUrl = "http://172.29.252.1:8080";
    private static String baseUrl1 = "http://119.29.82.22:8880";
    volatile private static HttpMethods instance = null;
    private Observable observable;
    private Subscriber subscriber;
    private HttpApi httpService;
    private static Activity context;
    private ChatResponse response;
    public static HttpMethods getInstance(Activity activity) {
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null)
                    instance = new HttpMethods(activity);
            }
        }
        context = activity;
        return instance;
    }
    private HttpMethods(Activity activity){
        context = activity;
    }
    public ChatResponse post(String path, Map<String, String> map) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl1)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
        if(map != null)
            observable = httpService.post(path, map);
        else
            observable = httpService.post(path);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatResponse>() {
                    @Override
                    public void onCompleted() {
                        ToastHelper.showToast(context, "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastHelper.showToast(context,  e.toString());
                    }

                    @Override
                    public void onNext(ChatResponse chatResponse) {
                        ToastHelper.showToast(context, String.format("" +chatResponse.toString() ));
                    }
                });
        return  response;
    }

    public ChatResponse get(String path, Map<String,String> map) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl1)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
        if(map != null)
            observable = httpService.get(path, map);
        else
            observable = httpService.get(path);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatResponse>() {
                    @Override
                    public void onCompleted() {
                        ToastHelper.showToast(context, "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastHelper.showToast(context,  e.toString());
                    }

                    @Override
                    public void onNext(ChatResponse chatResponse) {
                        ToastHelper.showToast(context, String.format("" +chatResponse.toString() ));
                    }
                });
        return  response;
    }
}
