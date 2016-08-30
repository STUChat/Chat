// IOnNewMessageArrivedListener.aidl
package cn.edu.stu.chat.aidl;

// Declare any non-default types here with import statements
import cn.edu.stu.chat.aidl.MessageInfo;

interface IOnNewMessageArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void onNewMessageArrived(in MessageInfo message);
}
