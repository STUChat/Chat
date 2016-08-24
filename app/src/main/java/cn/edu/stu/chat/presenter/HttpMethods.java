package cn.edu.stu.chat.presenter;

import java.util.Map;

import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.api.HttpApi;
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
    private static String baseUrl = Constant.HOST;
    volatile private static HttpMethods instance;
    private Observable observable;
    private Subscriber subscriber;
    private HttpApi httpService;
    private ChatResponse response;
    public HttpMethods getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null)
                    instance = new HttpMethods();
            }
        }
        return instance;
    }

    public ChatResponse post(String path, Map<String, String> map) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
        httpService.post(path, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ChatResponse>() {
                    @Override
                    public void call(ChatResponse chatResponse) {
                        response = chatResponse;
                    }
                });
        return  response;
    }

    public ChatResponse get(String path, Map<String, String> map) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
        httpService.get(path, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ChatResponse>() {
                    @Override
                    public void call(ChatResponse chatResponse) {
                        response = chatResponse;
                    }
                });
        return  response;
    }
}
