package cn.edu.stu.chat.view.api;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.stu.chat.R;

/**
 * Created by dell on 2016/8/25.
 */
public class BaseFragment extends Fragment {
    protected View toolbar;
    private  View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    /**
     * * 设置toolbar
     * @param res_id
     */
    protected void setToolbar(View view,int res_id){
        this.view = view;
        toolbar = view.findViewById(res_id);
    }

    /**
     * 显示左侧按钮
     */
    protected void showToolbarLeftBtn(View.OnClickListener listener){
        if(toolbar!=null){
            view.findViewById(R.id.left_toolbtn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.left_toolbtn).setOnClickListener(listener);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }

    /**
     * 显示右侧按钮
     */
    protected void showToolbarRightBtn(View.OnClickListener listener){
        if(toolbar!=null){
            view.findViewById(R.id.right_toolbtn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.right_toolbtn).setOnClickListener(listener);
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
            ((TextView)view.findViewById(R.id.toolbar_title)).setText(title);
        }else{
            throw new NullPointerException("toolbar为空，先使用setToolabr");
        }
    }


    /**
     * 隐藏标题
     */
    protected void hideTitle(){
        if(toolbar!=null){
            view.findViewById(R.id.toolbar_title).setVisibility(View.INVISIBLE);
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
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
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
}
