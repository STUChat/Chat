package cn.edu.stu.chat.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.FriendInfo;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<FriendInfo> list;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, List list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setListData(List list){
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FriendInfo getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_contact,null);
            holder.image = (ImageView) convertView.findViewById(R.id.contact_item_photo);
            holder.name = (TextView) convertView.findViewById(R.id.contact_item_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(list.get(i).getName());

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        TextView name;
    }
}
