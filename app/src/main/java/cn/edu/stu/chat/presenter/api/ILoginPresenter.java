package cn.edu.stu.chat.presenter.api;

/**
 * Created by dell on 2016/8/24.
 */
public interface ILoginPresenter extends IPresenter{
    public void init();
    public void login(String nick,String pwd);
    public void clickEye();
}
