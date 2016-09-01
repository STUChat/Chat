package cn.edu.stu.chat.model;

import java.io.Serializable;

/**
 * Created by Terence on 2016/8/22.
 */
public class User implements Serializable{
    String headUrl;//用户头像url
    String name;//用户昵称
    String gender;//用户性别 0:保密 1.男 2.女
    String motto;//个性签名
    String email;//账户email
    String token;//令牌
    long loginTime;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String toString(){
        return "name:"+name+"\n"+
                "gender:"+gender+"\n"+
                "email:"+email+"\n"+
                "token:"+token+"\n"+
                "motto:"+motto+"\n"+
                "headUrl"+headUrl;
    }
}
