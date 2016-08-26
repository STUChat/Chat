package cn.edu.stu.chat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.stu.chat.model.ChatResponse;

/**
 * Created by cheng on 16-8-24.
 */
public class JsonHelper {

    /**
     * 将ChatResponse的ResponseData 转为bean
     * @param response
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getResponseValue(ChatResponse response, Class<T> cls) {
        if(response.getResponseData()!=null)
            return getValue(response.getResponseData(),cls);
        return null;
    }

    /**
     * 将ChatResponse的ResponseData 转为list<bean>>
     * @param response
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T>  getResponseList(ChatResponse response, Class<T> cls) {
        if(response.getResponseData()!=null)
            return getList(response.getResponseData().toString(),cls);
        return null;
    }

    public static <T> List<T> getList(String jsonString,  Class<T> cls) {
        List<T> listT = null;
        try {
            //创建泛型对象
            T t =  cls.newInstance();
            //利用类加载加载泛型的具体类型
            Class<T> classT = (Class<T>) Class.forName(t.getClass().getName());
            List<Object> listObj = new ArrayList<Object>();
            //将数组节点中json字符串转换为object对象到Object的list集合
            listObj = new GsonBuilder().create().fromJson(jsonString, new TypeToken<List<Object>>(){}.getType());
            //转换未成功
            if(listObj == null || listObj.isEmpty()) return null;
            listT = new ArrayList<T>();
            //将Object的list中的的每一个元素中的json字符串转换为泛型代表的类型加入泛型代表的list集合返回
            for (Object obj : listObj) {
                T perT = new GsonBuilder().create().fromJson(obj.toString(), classT);
                listT.add(perT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listT;
    }

    public static List<Map<String, Object>> getMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static <T> T getValue(Object str, Class<T> cls) {
        T t = null;
        String s = null;
        if(str!=null) {
            try {
                Gson gson = new Gson();
                if(s instanceof String)
                    s = (String)str;
                else
                    s = gson.toJson(str);
                t = gson.fromJson(s, cls);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return t;
    }

}
