package cn.edu.stu.chat.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2016/9/8.
 */
public class ContactDbHelper extends SQLiteOpenHelper {
    // 数据库名
    public final static String DATABASE_NAME = "Chat.db";
    // 数据库版本
    public static int DATABASE_VERSION = 1;
    // 表名
    public final static String TABLE_NAME = "message_contact";
    // 表中的字段
    public final static String Friend_Id = "friend_user_id";
    public final static String Friend_name = "friend_name";
    public final static String Friend_gender = "friend_gender";
    public final static String Friend_motto = "friend_motto";
    public final static String Friend_email = "friend_email";
    public final static String Friend_headUrl = "friend_head_url";
    public final static String Mine_userID = "mine_user_id";

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + Friend_Id + " text primary key , "
                + Friend_name + " text, " + Friend_gender + " text," + Friend_motto
                + " text," + Friend_email  +" text, "+ Friend_headUrl + " text," + Mine_userID + " text);";
        db.execSQL(sql);
    }

    /**
     * 数据库升级时调用 删除数据库中原有的user表，并重新创建新user表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}