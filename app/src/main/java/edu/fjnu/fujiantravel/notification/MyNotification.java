package edu.fjnu.fujiantravel.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class MyNotification {
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private int notifyId = 100;

    public MyNotification() {

    }

    public MyNotification(Context context) {
        this.context = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        this.mBuilder = new NotificationCompat.Builder(context);
    }

    //设置通知栏标题
    public void setContentTitle(String str) {
        this.mBuilder.setContentTitle(str);
    }

    //设置通知栏显示内容
    public void setContentText(String str) {
        this.mBuilder.setContentText(str);
    }

    //设置通知栏点击意图
    public void setContentIntent(PendingIntent intent) {
        this.mBuilder.setContentIntent(intent);
    }

    //设置通知清楚响应意图
    public void setDeleteIntent(PendingIntent intent){
        this.mBuilder.setDeleteIntent(intent);
    }

    //设置通知集合数量
    public void setNumber(int number) {
        this.mBuilder.setNumber(number);
    }

    //通知首次出现通知栏，带上升动画效果
    public void setTicker(String str) {
        this.mBuilder.setTicker(str);
    }

    //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
    public void setWhen(long when) {
        this.mBuilder.setWhen(when);
    }

    //设置该通知优先级
    public void setPriority(int priority) {
        this.mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
    }

    //设置这个标志当用户单击面板就可以让通知将自动取消
    public void setAutoCancel(Boolean flag) {
        this.mBuilder.setAutoCancel(flag);
    }

    //ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,
    // 因此占用设备(如一个文件下载,同步操作,主动网络连接)
    public void setOngoing(boolean flag) {
        this.mBuilder.setOngoing(false);
    }

    //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
    public void setDefaults(int defaults) {
        this.mBuilder.setDefaults(defaults);
    }

    //设置通知声音
    public void setSound(Uri sound) {
        this.mBuilder.setSound(sound);
    }

    //设置通知小ICON
    public void setSmallIcon(int res) {
        this.mBuilder.setSmallIcon(res);
    }

    //设置通知栏进度条
    public void setProgress(int max, int progress, boolean indeterminate) {
        this.mBuilder.setProgress(max, progress, indeterminate);
    }
    public void notifybuild(){
        mNotificationManager.notify(notifyId, mBuilder.build());
    }
}
