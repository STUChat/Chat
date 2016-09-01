package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.view.widget.LoadMoreDataListener;
import cn.edu.stu.chat.view.widget.RecyclerOnItemClickListener;

/**
 * Created by dell on 2016/8/30.
 */
public class SearchFriendAdapter extends RecyclerView.Adapter {
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    private static final int VIEW_NODATA = 2;
    private List<Friend> list;
    private Context context;
    private RecyclerView mRecyclerView;
    private LayoutInflater inflater;
    private boolean isLoading;
    private boolean hasData;
    //无数据加载
    private int totalItemCount;
    private int lastVisibleItemPosition;
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;

    public SearchFriendAdapter(Context context, RecyclerView recyclerView,List data){
        this.context = context;
        this.mRecyclerView = recyclerView;
        this.list = data;
        inflater = LayoutInflater.from(context);
        hasData = false;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //mRecyclerView添加滑动事件监听
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    Log.d("test", "totalItemCount =" + totalItemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                        //此时是刷新状态
                        if (mMoreDataListener != null && hasData ==true) {
                            list.add(null);
                            //加入null值此时adapter会判断item的type
                            SearchFriendAdapter.this.notifyDataSetChanged();
                            mMoreDataListener.loadMoreData();
                        }
                        isLoading = true;
                    }
                }
            });
        }
    }

    public void setLoaded(List newlist, Boolean hasData) {
        setHasData(hasData);
        if(isLoading ==true)
            list.remove(list.size() - 1);
        isLoading = false;
        list.addAll(newlist);
        if(hasData==false)
            list.add(null);
        SearchFriendAdapter.this.notifyDataSetChanged();
    }

    public void setHasData(Boolean hasData){
        this.hasData = hasData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_ITEM) {
            View view = inflater.inflate(R.layout.item_search_friend, parent, false);
            return new FriendViewHolder(view);
        }else if(viewType == VIEW_PROG){
            View view = inflater.inflate(R.layout.item_search_friend_footer, parent, false);
            return new ProgressViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_search_friend_footer_nodata, parent, false);
            return new NoDatazViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FriendViewHolder){
            ((FriendViewHolder)holder).name.setText(list.get(position).getName());
//            ((FriendViewHolder)holder).photo.setImageBitmap();
        }else if(holder instanceof ProgressViewHolder){
            Log.e("pd","hree");
            if (((ProgressViewHolder) holder).pb != null)
                ((ProgressViewHolder) holder).pb.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list==null ? 0:list.size();
    }

    public void setList(List list){
        this.list = list;
    }

    //根据不同的数据返回不同的viewType
    @Override
    public int getItemViewType(int position) {
       if(list.get(position)!=null)
           return VIEW_ITEM;
       if( hasData==true)
           return VIEW_PROG;
        return VIEW_NODATA;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView photo;
        public FriendViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.search_friend_item_name);
            photo = (ImageView) itemView.findViewById(R.id.search_friend_item_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnitemClickListener != null)
                        mOnitemClickListener.onClick(v,list.get(getAdapterPosition()));
                }
            });
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar pb;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            pb = (ProgressBar) itemView.findViewById(R.id.search_friend_item_pb);
        }
    }

    class NoDatazViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public  NoDatazViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.search_friend_item_nodata_textview);
        }
    }

    private LoadMoreDataListener mMoreDataListener;

    //加载更多监听方法
    public void setOnMoreDataLoadListener(LoadMoreDataListener onMoreDataLoadListener) {
        mMoreDataListener = onMoreDataLoadListener;
    }

    private RecyclerOnItemClickListener mOnitemClickListener;

    //点击事件监听方法
    public void setOnItemClickListener(RecyclerOnItemClickListener onItemClickListener) {
        mOnitemClickListener = onItemClickListener;
    }
}