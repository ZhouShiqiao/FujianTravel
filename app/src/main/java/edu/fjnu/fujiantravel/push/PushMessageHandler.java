package edu.fjnu.fujiantravel.push;

import android.content.Context;

import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.order.Order;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class PushMessageHandler {
    private final static PushMessageHandler handler = new PushMessageHandler();

    private PushMessageHandler() {

    }

    public static PushMessageHandler getinstance() {
        return handler;
    }

    public void handlemessage(Context context,String JsonStr){
        MyMessage msg = new MyMessage();
        msg = (MyMessage) Json.JsontoObject(JsonStr,msg.getClass());
        switch (msg.gethead()){
            case Order.PUSHORDERTOGUIDE:

                break;
        }
    }
}
