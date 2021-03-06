package cn.edu.stu.chat.view.api;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;

public class BaseActivity extends AppCompatActivity {

    protected View toolbar;
    /**
     * 设置toolbar
     * @param res_id
     */
    protected void setToolbar(int res_id){
        toolbar = (View)findViewById(res_id);
    }

    /**
     * 显示左侧按钮
     */
    protected void showToolbarLeftBtn(View.OnClickListener listener){
        if(toolbar!=null){
            findViewById(R.id.left_toolbtn).setVisibility(View.VISIBLE);
            findViewById(R.id.left_toolbtn).setOnClickListener(listener);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }

    /**
     * 显示右侧按钮
     */
    protected void showToolbarRightBtn(View.OnClickListener listener){
        if(toolbar!=null){
            findViewById(R.id.right_toolbtn).setVisibility(View.VISIBLE);
            findViewById(R.id.right_toolbtn).setOnClickListener(listener);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }

    /**
     * 改变标题
     * @param title
     */
    protected void setTitle(String title){
        if(toolbar!=null){
            ((TextView)findViewById(R.id.toolbar_title)).setText(title);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }

    /**
     * 隐藏标题
     */
    protected void hideTitle(){
        if(toolbar!=null){
            findViewById(R.id.toolbar_title).setVisibility(View.INVISIBLE);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }

    /**
     * 显示popup菜单,响应事件需要重写响应方法
     * @param view
     * @param menuId
     */
    protected void showPopupMenu(View view,int menuId) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(menuId, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MenuItemClick(item);
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    /**
     * popup菜单的响应事件
     * @param item
     */
    protected void MenuItemClick(MenuItem item) {
    }

    /**
     * 判断用户是否登陆
     * 判读Application的user是否为空
     * @return
     */
    public boolean isLogin(){
        ChatApp app = (ChatApp)getApplication();
            if(app.getUser()!=null&& app.getUser().getToken()!=null&&!app.getUser().getToken().equals("")) {
                return true;
            }
            return false;
    }

    /**
     * 注销
     * @return
     */
    public void logout(){
        ChatApp app = (ChatApp)getApplication();
        app.setUser(null);
    }
}
