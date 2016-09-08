package cn.edu.stu.chat.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.User;

/**
 * Created by dell on 2016/9/8.
 */
public class ContactDbUtil {
    private SQLiteDatabase db;
    private ContactDbHelper contactDbHelper;

    public ContactDbUtil(Context context) {
        contactDbHelper = new ContactDbHelper(context);
    }

    public void open() throws SQLException {
        db = contactDbHelper.getWritableDatabase();
    }

    public void close() {
        contactDbHelper.close();
    }

    // 插入数据操作
    public void insert(User user, Friend friend) {
        this.open();
        ContentValues cv = new ContentValues();
        cv.put(ContactDbHelper.Friend_Id, friend.getUserID());
        cv.put(ContactDbHelper.Friend_name, friend.getName());
        cv.put(ContactDbHelper.Friend_gender, friend.getGender());
        cv.put(ContactDbHelper.Friend_motto, friend.getMotto());
        cv.put(ContactDbHelper.Friend_email, friend.getEmail());
        cv.put(ContactDbHelper.Friend_headUrl, friend.getHeadUrl());
        cv.put(ContactDbHelper.Mine_userID, user.getToken());
        db.insert(ContactDbHelper.TABLE_NAME, null, cv);
        this.close();
    }

    // 删除数据操作
    public boolean delete(String friendId,String userToken) {
        this.open();
        String where = ContactDbHelper.Friend_Id + " = ? and " + ContactDbHelper.Mine_userID+" = ? " ;
        String[] whereValue = {friendId,userToken};
        if (db.delete(ContactDbHelper.TABLE_NAME, where, whereValue) > 0) {
            this.close();
            return true;
        }
        return false;
    }

    // 更新数据操作
    public boolean update(User user, Friend friend) {
        this.open();
        ContentValues cv = new ContentValues();

        cv.put(ContactDbHelper.Friend_name, friend.getName());
        cv.put(ContactDbHelper.Friend_gender, friend.getGender());
        cv.put(ContactDbHelper.Friend_motto, friend.getMotto());
        cv.put(ContactDbHelper.Friend_email, friend.getEmail());
        cv.put(ContactDbHelper.Friend_headUrl, friend.getHeadUrl());

        if (db.update(ContactDbHelper.TABLE_NAME, cv, ContactDbHelper.Friend_Id + "=? and "+ContactDbHelper.Mine_userID +" +? ",
                new String[]{friend.getUserID(),user.getToken()}) > 0) {
            this.close();
            return true;
        }
        db.close();
        return false;
    }

    // 获取所有数据
    public ArrayList<Friend> getAllData(String userToken) {
        this.open();
        ArrayList<Friend> items = new ArrayList<Friend>();
        Cursor cursor = db.rawQuery("select * from " + ContactDbHelper.TABLE_NAME+ " where "+ContactDbHelper.Mine_userID+" = '"+userToken+"'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String friend_Id = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_Id));
                String friend_name = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_name));
                String friend_gender = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_gender));
                String friend_motto = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_motto));
                String friend_email = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_email));
                String friend_headUrl = cursor.getString(cursor
                        .getColumnIndex(ContactDbHelper.Friend_headUrl));

                Friend item = new Friend(friend_headUrl,friend_name,friend_gender,friend_motto,friend_email,friend_Id);
                items.add(item);
                cursor.moveToNext();
            }
        }
        this.close();
        Collections.reverse(items);
        return items;
    }

//    public int lastId(){
//        return getAllData().get(0).getId();
//    }
}
