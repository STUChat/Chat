package cn.edu.stu.chat.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.utils.ToastHelper;
import cn.edu.stu.chat.view.api.BaseActivity;

/**
 * Created by dell on 2016/8/25.
 */
public class SearchFriendActivity extends BaseActivity {
    private EditText searchEdit;
    private ImageView ivDelete;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        initView();
    }

    private void initView() {
        searchEdit = (EditText)findViewById(R.id.search_input);
        ivDelete =(ImageView)findViewById(R.id.search_iv_delete);
        listView = (ListView)findViewById(R.id.search_listview);
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

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                ivDelete.setVisibility(View.VISIBLE);
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
        ToastHelper.showToast(SearchFriendActivity.this,text);
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) SearchFriendActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
