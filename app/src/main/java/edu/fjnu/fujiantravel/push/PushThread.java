package edu.fjnu.fujiantravel.push;

import android.content.Context;

import com.baidu.android.pushservice.PushManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.order.Order;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class PushThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private int type;
    private PushMessage pushmessage;
    private String address;
    private int port;

    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private Context context;

    public PushThread(int type, PushMessage msg, String address, int port,Context context) {
        this.type = type;
        this.pushmessage = msg;
        this.address = address;
        this.port = port;
        this.context=context;
    }

    public void run() {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            switch (this.type) {
                case PushMessage.PUSHBIND:
                    pushbind();
                    out.writeUTF(JsonStr);
                    out.flush();
                    break;
            }
            reJsonStr = in.readUTF();
            remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
            int head = remsg.gethead();
            switch (head) {
                case PushMessage.PUSHBIND_SUCCESS:
                    bindsuccess();
                    break;
                case PushMessage.PUSHBIND_ERROR:
                    socket.close();
                    out.close();
                    in.close();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pushbind() {
        msg.sethead(type);
        msg.setdetail(Json.ObjecttoJson(pushmessage));
        JsonStr = Json.ObjecttoJson(msg);
    }

    private void bindsuccess() throws IOException{
        remsg.sethead(Client.CLINT_FINISH);
        remsg.setdetail(null);
        reJsonStr = Json.ObjecttoJson(remsg);
        out.writeUTF(reJsonStr);
        out.flush();
        socket.close();
        out.close();
        in.close();
        /*List<String> tags = new ArrayList<>();
        tags.add("tourist");
        tags.add("guide");
        PushManager.setTags(context.getApplicationContext(), tags);*/

    }
}
