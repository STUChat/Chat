package cn.edu.stu.chat.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.SearchFriendAdapter;
import cn.edu.stu.chat.http.HttpMethods;
import cn.edu.stu.chat.http.NetworkHelper;
import cn.edu.stu.chat.model.ChatResponse;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.UriConstant;
import cn.edu.stu.chat.utils.JsonHelper;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;
import cn.edu.stu.chat.view.widget.LoadMoreDataListener;
import cn.edu.stu.chat.view.widget.RecycleViewDivider;
import cn.edu.stu.chat.view.widget.RecyclerOnItemClickListener;
import rx.Subscriber;

/**
 * Created by dell on 2016/8/25.
 */
public class SearchFriendActivity extends BaseActivity {
    private EditText searchEdit;
    private ImageView ivDelete;
    private RecyclerView recyclerview;
    private SearchFriendAdapter adapter;
    private List<Friend> friends;
    private String searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        initView();
    }
    private void initView() {
        searchEdit = (EditText)findViewById(R.id.search_input);
        ivDelete =(ImageView)findViewById(R.id.search_iv_delete);
        initRecycleView();
        searchEdit.addTextChangedListener(new EditChangedListener());
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startSearching(searchEdit.getText().toString());
                }
                return true;
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
                ivDelete.setVisibility(View.GONE);
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview = (RecyclerView) findViewById(R.id.search_recyclerview);
        recyclerview.setLayoutManager(mLayoutManager);
        //自定义下划线
        recyclerview.addItemDecoration(new RecycleViewDivider(this, RecycleViewDivider.VERTICAL_LIST));
        friends = new ArrayList<>();
        adapter = new SearchFriendAdapter(this,recyclerview,friends);
        recyclerview.setAdapter(adapter);
        adapter.setOnMoreDataLoadListener(
                new LoadMoreDataListener() {
                    @Override
                    public void loadMoreData() {
                        updateData();
                    }
                });
        adapter.setOnItemClickListener(new RecyclerOnItemClickListener(){
            @Override
            public void onClick(View view,Friend friend) {
                Intent intent = new Intent(SearchFriendActivity.this,FriendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friend",friend);
                intent.putExtras(bundle);
                intent.putExtra("classify",Constant.STRANGER);
                startActivity(intent);
            }
        });
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                ivDelete.setVisibility(View.VISIBLE);
//                startSearching(searchEdit.getText().toString());
            } else {
                ivDelete.setVisibility(View.GONE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    /**
     * 进行搜索操作
     * @param text
     */
    private void startSearching(String text){
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) SearchFriendActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        initData(text);
        updateData();
    }

    private void initData(String text) {
        searchText = text;
        recyclerview.setVisibility(View.VISIBLE);
        friends.clear();
        initRecycleView();
    }

    private void updateData(){
        if(searchText==null||searchText.equals("")){
            showErrorMessage("不能为空");
            return;
        }
        Map<String,String> map = new HashMap();
        map.put("token",((ChatApp)getApplication()).getUser().getToken());
        map.put("select",searchText);
        HttpMethods.getInstance().baseUrl(UriConstant.HOST).subscribe(new Subscriber<ChatResponse>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                if(!NetworkHelper.isNetworkAvailable(SearchFriendActivity.this))
                    showErrorMessage("网络不可用");
                else
                    showErrorMessage(e.getMessage());
            }
            @Override
            public void onNext(ChatResponse chatResponse) {
                if (chatResponse != null && chatResponse.getResponseCode().equals("10")) {//后面有数据
                    List<Friend> list = JsonHelper.getResponseList(chatResponse,Friend.class);
                    if(list!=null && !list.isEmpty())
                        adapter.setLoaded(list,true);
                    return;
                }
                if (chatResponse != null && chatResponse.getResponseCode().equals("1")) {//后面无数据
                    final List<Friend> list = JsonHelper.getResponseList(chatResponse,Friend.class);
                    if(list!=null&&!list.isEmpty()){
                        adapter.setLoaded(list,false);
                    }
                    return;
                }
                if(chatResponse.getResponseMsg()!=null && !chatResponse.getResponseMsg().equals(""))
                    showErrorMessage(chatResponse.getResponseMsg());
                else
                    showErrorMessage("未知错误");
            }
        }).post(UriConstant.FindUser,map);
    }

    private  void showErrorMessage(String message){
        ToastHelper.showErrorDialog(this,Constant.SearchFriendTitle,message);
    }
}