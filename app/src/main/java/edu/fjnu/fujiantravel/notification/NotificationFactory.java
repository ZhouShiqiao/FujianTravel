package edu.fjnu.fujiantravel.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.OrderSimpleInfoActivity;
import edu.fjnu.fujiantravel.activity.SimpleUserInfoActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.order.Order;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class NotificationFactory {
    public final static NotificationFactory factory = new NotificationFactory();

    private NotificationFactory() {
    }

    public static NotificationFactory getinstance() {
        return factory;
    }

    public void getNotification(String Jsonstr, Context context) {
        MyMessage msg = new MyMessage();
        msg = (MyMessage) Json.JsontoObject(Jsonstr, msg.getClass());
        switch (msg.gethead()) {
            case Order.PUSHORDERTOGUIDE:
                Order order = new Order();
                order = (Order) Json.JsontoObject(msg.getdetail(), order.getClass());
                if (!order.gettouristid().equals(TouristActivity.getUser().getid())) {
                    this.buildPushOrderToGuideNotification(order.getorderid(), context);
                }
                break;
            case Order.PUSHORDERTOTOURIST:
                this.buildPushOrderToTouristNotification(msg.getdetail(), context);
                break;
        }
    }

    private void buildPushOrderToGuideNotification(String str, Context context) {
        MyNotification notification = new MyNotification(context);
        notification.setContentTitle(context.getString(R.string.order_notify_title));
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setAutoCancel(true);
        notification.setTicker(context.getString(R.string.order_notify_ticker));
        notification.setContentText(context.getString(R.string.new_order_notify_guide));
        notification.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent().setClass(context, OrderSimpleInfoActivity.class);
        intent.putExtra("id", str);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification.setContentIntent(pendingIntent);

        notification.notifybuild();
    }

    private void buildPushOrderToTouristNotification(String str, Context context) {
        MyNotification notification = new MyNotification(context);
        notification.setContentTitle(context.getString(R.string.order_notify_title));
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setAutoCancel(true);
        notification.setTicker(context.getString(R.string.order_receive_ticker));
        notification.setContentText(context.getString(R.string.order_receive_notify_tourist));
        notification.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent().setClass(context, SimpleUserInfoActivity.class);
        intent.putExtra("id", str);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification.setContentIntent(pendingIntent);

        notification.notifybuild();
    }
}
