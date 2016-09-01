package cn.edu.stu.chat.model;

import java.io.Serializable;

/**
 * Created by dell on 2016/8/25.
 */
public class Friend implements Serializable{
    String headUrl;//用户头像url
    String name;//用户昵称
    String gender;//用户性别 0:保密 1.男 2.女
    String motto;//个性签名
    String email;//账户email
    String userID;//用户id

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "name:"+name+"\n"+
                "gender:"+gender+"\n"+
                "email:"+email+"\n"+
                "motto:"+motto+"\n"+
                "headUrl"+headUrl+"\n"+
                "UserID:"+userID;
    }
}
