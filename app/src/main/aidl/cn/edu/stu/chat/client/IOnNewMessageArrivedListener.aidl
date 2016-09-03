// IOnNewMessageArrivedListener.aidl
package cn.edu.stu.chat.client;

// Declare any non-default types here with import statements
import java.lang.String;
interface IOnNewMessageArrivedListener {
   void onNewMessageArrived(String id);
}
