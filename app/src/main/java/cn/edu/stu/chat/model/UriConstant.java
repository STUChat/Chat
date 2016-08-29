package cn.edu.stu.chat.model;

/**
 * Created by dell on 2016/8/29.
 */
public class UriConstant {
    /**
     *服务器地址
     */
    public final static String HOST = "http://119.29.82.22:8880";
    /**
     * 登录接口
     * 参数:
     */
    public final static String LOGIN = "login.aspx";
    /**
     * 获取个人信息
     * 参数:token
     */
    public final static String GetUserInfo = "GetUserInfo.aspx";

    /**
     * 修改个人信息
     * 参数:token,name,gender,motto
     */
    public final static String UpdateUserInfo = "UpdateUserInfo.aspx";

    /**
     * 修改密码
     * 参数:token,password,newPassword
     */
    public final static String ChangePass = "ChangePass.aspx";

    /**
     * 反馈
     * 参数:
     */
    public final static String FeedBack = "";
}
