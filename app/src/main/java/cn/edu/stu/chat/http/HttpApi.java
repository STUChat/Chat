package cn.edu.stu.chat.http;



import java.util.Map;

import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    /**
     * 上传多张图片
     * @param path
     * @param params
     * @param content
     * @return
     */
    @Multipart
    @POST("{path}")
    Observable<ChatResponse> uploadImages(@Path("path") String path, @PartMap Map<String, RequestBody> params,@Part("content")RequestBody content);

    /**
     * 上传一张图片
     * @param path
     * @param photo
     * @return
     */
    @Multipart
    @POST("{path}")
    Observable<ChatResponse> uploadImage(@Path("path") String path,@Part MultipartBody.Part photo);
}
