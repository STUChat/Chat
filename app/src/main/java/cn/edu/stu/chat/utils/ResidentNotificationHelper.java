package cn.edu.stu.chat.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.view.activity.MainActivity;

/**
 * Created by dell on 2016/8/26.
 */
public class ResidentNotificationHelper {
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static final String ACTION_CLOSE_NOTICE = "cn.edu.stu.chat.action.closenotice";
    public static final int NOTICE_ID_TYPE_0 = R.string.app_name;

    @TargetApi(16)
    public static void sendResidentNoticeType0(Context context, String title, String content, @DrawableRes int res) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
        remoteViews.setTextViewText(R.id.time_tv, getTime());
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.logo);
        //remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice_view_type_0, pendingIntent);
//        int requestCode1 = (int) SystemClock.uptimeMillis();
//        Intent intent1 = new Intent(ACTION_CLOSE_NOTICE);
//        intent1.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
//        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.close_iv, pendingIntent1);
        builder.setSmallIcon(R.mipmap.search_icon);
        builder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
        //延迟0ms，然后振动300ms，在延迟500ms，接着在振动700ms。
        builder.setVibrate(new long[] {0,300});
        Notification notification = builder.build();
        if(android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
            notification.bigContentView = remoteViews;
        }
        notification.contentView = remoteViews;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_0, notification);
    }

    public static void sendDefaultNotice(Context context, String title, String content, @DrawableRes int res) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Notification notification = builder
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.logo)
                .setSmallIcon(R.mipmap.logo)
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_0, notification);
    }

    public static int getIconColor(){
        return Color.parseColor("#999999");

    }

    private static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);
        return format.format(new Date());
    }

    public static void clearNotification(Context context, int noticeId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(noticeId);
    }
}