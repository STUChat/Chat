package cn.edu.stu.chat.model;

/**
 * Created by cheng on 16-8-24.
 * 测试使用
 */

public class UserInfo {
    private String id;
    private String age;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String toString(){
        return ("id:"+id+"age:"+age+"sex:"+sex+"\n");
    }
}
