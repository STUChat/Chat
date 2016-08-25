package cn.edu.stu.chat.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.presenter.ContactPresenter;
import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.view.activity.LoginActivity;
import cn.edu.stu.chat.view.activity.SearchFriendActivity;
import cn.edu.stu.chat.view.api.BaseFragment;
import cn.edu.stu.chat.view.api.IContactView;

/**
 * Created by cheng on 16-8-22.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener,IContactView{
    IContactPresenter contactPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_contact, null);
        setToolbar(view,R.id.toolbar);
        showToolbarRightBtn(this);
        contactPresenter = new ContactPresenter();
        contactPresenter.attach(this);
        contactPresenter.init();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right_toolbtn:
                showPopupMenu(getView().findViewById(R.id.right_toolbtn),R.menu.fragment_contact_menu);
                break;
        }
    }

    @Override
    protected void MenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact_add_friend:
                jumpToActivity(SearchFriendActivity.class);
                break;
        }
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getContext(), activityClass);
        startActivity(intent);
    }
}
