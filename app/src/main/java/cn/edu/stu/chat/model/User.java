package cn.edu.stu.chat.model;

/**
 * Created by Terence on 2016/8/22.
 */
public class User {
    private boolean loginState;
    public boolean isLoginState(){
        return loginState;
    }
    public void setLoginState(boolean state){
        loginState = state;
    }
}
