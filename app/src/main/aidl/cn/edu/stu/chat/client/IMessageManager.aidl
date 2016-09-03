// IMessageManager.aidl
package cn.edu.stu.chat.client;

// Declare any non-default types here with import statements
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.client.IOnNewMessageArrivedListener;
interface IMessageManager {
    List<MessageDetailModel> getMessage(String id);
    void sendMessage(in MessageDetailModel message);
    void registerListener(IOnNewMessageArrivedListener listener);
    void unRegisterListener(IOnNewMessageArrivedListener listener);
}
