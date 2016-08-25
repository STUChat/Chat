package cn.edu.stu.chat.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.view.activity.UserInfoActivity;
import cn.edu.stu.chat.view.api.BaseFragment;

/**
 * Created by cheng on 16-8-22.
 */
public class MineFragment extends BaseFragment {

    private LinearLayout userLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        userLayout =(LinearLayout)view.findViewById(R.id.mine_user);
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserInfo();
            }
        });
    }

    public void checkUserInfo(){
        getActivity().startActivity(new Intent(getContext(), UserInfoActivity.class));
    }
}
