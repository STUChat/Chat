package cn.edu.stu.chat.model;

import java.io.Serializable;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageModel implements Serializable{

    private static final long serialVersionUID = -7018750186297918551L;
    private Friend friend;
    private MessageDetailModel messageDetailModel;
    
    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public MessageDetailModel getMessageDetailModel() {
        return messageDetailModel;
    }

    public void setMessageDetailModel(MessageDetailModel messageDetailModel) {
        this.messageDetailModel = messageDetailModel;
    }
}
