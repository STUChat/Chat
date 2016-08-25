package cn.edu.stu.chat.model;

import java.io.Serializable;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageDetailModel implements Serializable {
    private static final long serialVersionUID = 3866532882035322531L;
    private String userId;
    private String name;
    private String msg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
