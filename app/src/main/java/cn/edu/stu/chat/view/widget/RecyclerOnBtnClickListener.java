package cn.edu.stu.chat.view.widget;

import android.view.View;

import cn.edu.stu.chat.model.Friend;

/**
 * Created by dell on 2016/8/30.
 * recycle item点击事件
 */
public interface RecyclerOnBtnClickListener {
    void onClick(View view, Friend friend,int type);
}