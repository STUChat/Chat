// IMessageManager.aidl
package cn.edu.stu.chat.aidl;

// Declare any non-default types here with import statements
import cn.edu.stu.chat.aidl.MessageInfo;
import cn.edu.stu.chat.aidl.IOnNewMessageArrivedListener;

interface IMessageManager {
    List<MessageInfo> getMessage();
    void addMessage(in MessageInfo message);
    void registerListener(IOnNewMessageArrivedListener listener);
    void unRegisterListener(IOnNewMessageArrivedListener listener);

}
