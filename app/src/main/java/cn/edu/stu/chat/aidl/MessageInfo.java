package cn.edu.stu.chat.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cheng on 16-8-28.
 */
public class MessageInfo implements Parcelable {
    private String content;
    private String send;
    private String time;

    public MessageInfo(String content, String send, String time){
        this.content = content;
        this.send = send;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(content);
        parcel.writeString(send);
        parcel.writeString(time);
    }

    public static final Creator<MessageInfo> CREATOR = new Creator<MessageInfo>(){

        @Override
        public MessageInfo createFromParcel(Parcel parcel) {
            return new MessageInfo(parcel);
        }

        @Override
        public MessageInfo[] newArray(int i) {
            return new MessageInfo[i];
        }
    };

    private MessageInfo(Parcel parcel){
        content = parcel.readString();
        send = parcel.readString();
        time = parcel.readString();
    }

    public String toString(){
        return send+"  "+time+"  "+content;
    }

    public String getContent() {
        return content;
    }

    public String getSend() {
        return send;
    }

    public String getTime() {
        return time;
    }
}
