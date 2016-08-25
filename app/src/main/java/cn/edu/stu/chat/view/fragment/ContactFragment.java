package cn.edu.stu.chat.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.presenter.ContactPresenter;
import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.view.activity.SearchFriendActivity;
import cn.edu.stu.chat.view.adapter.ContactAdapter;
import cn.edu.stu.chat.view.api.BaseFragment;
import cn.edu.stu.chat.view.api.IContactView;

/**
 * Created by cheng on 16-8-22.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener,IContactView{
    private IContactPresenter contactPresenter;
    private ListView listView;
    private ContactAdapter adapter;
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
        listView = (ListView)view.findViewById(R.id.contact_listview);
        contactPresenter = new ContactPresenter();
        contactPresenter.attach(this);
        contactPresenter.init();
        return view;
    }

    public void showList(List list){
        adapter = new ContactAdapter(getContext(),list);
        listView.setAdapter(adapter);
    }

    @Override
    public void showDataChange(List list){
        adapter.setListData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getContext(), activityClass);
        startActivity(intent);
    }

    @Override
    public void setTitle(String title){
        super.setTitle(title);
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
}
