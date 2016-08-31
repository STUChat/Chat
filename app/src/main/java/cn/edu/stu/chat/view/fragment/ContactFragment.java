package cn.edu.stu.chat.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.User;
import cn.edu.stu.chat.presenter.ContactPresenter;
import cn.edu.stu.chat.presenter.api.IContactPresenter;
import cn.edu.stu.chat.view.activity.SearchFriendActivity;
import cn.edu.stu.chat.adapter.ContactAdapter;
import cn.edu.stu.chat.view.api.BaseFragment;
import cn.edu.stu.chat.view.api.IContactView;

/**
 * Created by cheng on 16-8-22.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener,IContactView{
    private IContactPresenter contactPresenter;
    private ListView listView;
    private ContactAdapter adapter;
    private View headerView;
    private RelativeLayout headLayout;

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
        headerView = inflater.inflate(R.layout.item_contact_header, listView, false);
        headLayout = (RelativeLayout) headerView.findViewById(R.id.contact_item_new_friend_layout);
        headLayout.setOnClickListener(this);
        listView.addHeaderView(headerView);
        adapter = new ContactAdapter(getContext(),null);
        listView.setAdapter(adapter);
        contactPresenter = new ContactPresenter();
        contactPresenter.attach(this);
        contactPresenter.init();
        return view;
    }


    @Override
    public void showDataChange(List list){
        adapter.setListData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkHelper.isNetworkAvailable(getContext());
    }

    @Override
    public User getUser() {
        return ((ChatApp)getActivity().getApplication()).getUser();
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getContext(), activityClass);
        startActivity(intent);
    }

    @Override
    public void jumpToActivity(Class<? extends Activity> activityClass,ArrayList<Friend> list) {
        Intent intent = new Intent(getContext(), activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("newFriend",list);
        intent.putExtras(bundle);
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
            case R.id.contact_item_new_friend_layout:
                contactPresenter.dealNewFriend();
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

    /**
     * 显示申请好友
     * @param num
     */
    @Override
    public void showNewFriend(int num){
        listView.setVisibility(View.VISIBLE);
        TextView numText = (TextView)headerView.findViewById(R.id.contact_item_new_friend);
        headerView.findViewById(R.id.contact_item_new_friend_num_layout).setVisibility(View.VISIBLE);
        numText.setText(num+"");
    }
}
