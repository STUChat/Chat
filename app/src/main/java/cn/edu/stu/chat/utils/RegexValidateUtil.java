package cn.edu.stu.chat.utils;

/**
 * Created by dell on 2016/9/1.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式验证输入格式
 * @author liuxing
 *
 */
public class RegexValidateUtil {
    public static void main(String[] args) {
        System.out.println(checkEmail("14_8@qw.df"));
        System.out.println(checkMobileNumber("071-3534452"));
    }
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
}
