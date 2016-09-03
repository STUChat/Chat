package cn.edu.stu.chat.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Terence on 2016/8/25.
 */
public class MessageDetailModel implements Parcelable {
    private String userId;
    private int type;
    private String msg;

    public MessageDetailModel(String userId,int type,String msg){
        this.userId = userId;
        this.type = type;
        this.msg = msg;
    }

    public MessageDetailModel(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString(){
        return userId+"#"+type+"#"+msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(userId);
        parcel.writeInt(type);
        parcel.writeString(msg);
    }

    public static final Creator<MessageDetailModel> CREATOR = new Creator<MessageDetailModel>(){

        @Override
        public MessageDetailModel createFromParcel(Parcel parcel) {
            return new MessageDetailModel(parcel);
        }

        @Override
        public MessageDetailModel[] newArray(int i) {
            return new MessageDetailModel[i];
        }
    };

    private MessageDetailModel(Parcel parcel){
        userId = parcel.readString();
        type = parcel.readInt();
        msg = parcel.readString();
    }
}
