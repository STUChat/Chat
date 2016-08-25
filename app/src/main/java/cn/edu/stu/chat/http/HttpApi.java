package cn.edu.stu.chat.http;

import java.util.Map;

import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.User;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Terence on 2016/8/24.
 */
public interface HttpApi{
    @FormUrlEncoded
    @POST("{path}")
    Observable<ChatResponse> post(@Path("path") String path, @FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("{path}")
    Observable<ChatResponse> post(@Path("path") String path);

    @GET("{path}")
    Observable<ChatResponse> get(@Path("path") String path,@QueryMap Map<String,String> map);

    @GET("{path}")
    Observable<ChatResponse> get(@Path("path") String path);

}
