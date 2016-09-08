package cn.edu.stu.chat.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.adapter.MessageAdapter;
import cn.edu.stu.chat.client.IMessageManager;
import cn.edu.stu.chat.client.IOnNewMessageArrivedListener;
import cn.edu.stu.chat.client.MessageService;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.model.MessageModel;
import cn.edu.stu.chat.utils.ContactDbUtil;
import cn.edu.stu.chat.view.activity.MainActivity;
import cn.edu.stu.chat.view.activity.MessageActivity;
import cn.edu.stu.chat.view.api.BaseFragment;

/**
 * Created by cheng on 16-8-22.
 */
public class MessagingFragment extends BaseFragment implements AdapterView.OnItemClickListener{
   // @BindView(R.id.message_listview)
    private ListView msgListView;
    private List<MessageModel> datas;
    private MessageAdapter messageAdapter;
    private IMessageManager mManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), MessageService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",((ChatApp)getActivity().getApplication()).getUser());
        intent.putExtras(bundle);
        getActivity().bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IMessageManager messageManager = IMessageManager.Stub.asInterface(iBinder);
            try{
                mManager = messageManager;
                messageManager.registerListener(newMessageArrivedListener);
//                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//                String date = sDateFormat.format(new java.util.Date());
//                mManager.sendMessage(new MessageDetailModel("id2",1,"hello, i am service",date));
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    private IOnNewMessageArrivedListener newMessageArrivedListener = new IOnNewMessageArrivedListener.Stub(){

        @Override
        public void onNewMessageArrived(String id) throws RemoteException {
            List messageList = mManager.getMessage(id);
            if(messageList!=null && !messageList.isEmpty()){
//                datas.addAll(messageList);
//                handler.sendEmptyMessage(NEW_MESSAGE);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_messaging, null);
        msgListView = (ListView) view.findViewById(R.id.message_listview);
        MessageModel msg = new MessageModel();
        datas = new ArrayList<>();
        messageAdapter = new MessageAdapter(getContext(), datas);
        msgListView.setAdapter(messageAdapter);
        msgListView.setOnItemClickListener(this);
        new getMessageContact(getContext(),((ChatApp)getActivity().getApplication()).getUser().getToken(),datas,messageAdapter).execute();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("friend",datas.get(i).getFriend());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        if(mManager!=null && mManager.asBinder().isBinderAlive()){
            try{
                mManager.unRegisterListener(newMessageArrivedListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        getActivity().unbindService(mConnection);
        super.onDestroy();
        Log.e("TAG", "unbindservice");
    }

    private static class getMessageContact extends AsyncTask<Object,Integer,ArrayList<Friend>>{
        private Context context;
        private String token;
        private List datas;
        private MessageAdapter adapter;
        public getMessageContact(Context context,String token,List datas,MessageAdapter adapter){
            this.context = context;
            this.token = token;
            this.datas = datas;
            this.adapter = adapter;
        }

        @Override
        protected ArrayList<Friend> doInBackground(Object... objects) {
            return new ContactDbUtil(context).getAllData(token);
        }

        @Override
        protected void onPostExecute(ArrayList<Friend> friends) {
            for(Friend friend:friends){
                Log.e("TAG", "onPostExecute: "+friend.toString() );
                MessageModel model = new MessageModel();
                model.setFriend(friend);
                datas.add(model);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
