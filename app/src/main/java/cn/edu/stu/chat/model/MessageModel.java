package cn.edu.stu.chat.model;

import java.io.Serializable;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageModel implements Serializable{

    private static final long serialVersionUID = -7018750186297918551L;
    private String friendId;
    private String time;
    private String nickName;
    private String photoUrl;
    private String msg;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
