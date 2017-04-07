package edu.fjnu.fujiantravel.scenic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.order.Order;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class ScenicThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private int type;


    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private String address;
    private int port;

    private Context context;
    private Handler handler;

    public ScenicThread() {

    }

    public ScenicThread(int type, String address, int port, Context context, Handler handler) {
        this.type = type;
        this.address = address;
        this.port = port;
        this.context = context;
        this.handler = handler;
    }

    public void run() {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            switch (this.type) {
                case Scenic.QUERYSCENIC:
                    query(type);
                    break;
                case Scenic.QUERYSCENUCLIST:
                    query(type);
                    break;
            }
            reJsonStr = in.readUTF();
            remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
            int head = remsg.gethead();
            switch (head) {
                case Scenic.QUERYLIST_ERROR:
                    clientover();
                    break;
                case Scenic.QUERYLIST_SUCCESS:
                    queryslistsucccess();
                    clientover();
                    break;
                case Scenic.QUERYSCENIC_SUCCESS:
                    clientover();
                    break;
                case Scenic.QUERYSCENIC_ERROR:
                    clientover();
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void queryscenicsuccess(){

    }
    private void queryslistsucccess(){
        Message message = new Message();
        message.what = Scenic.QUERYLIST_SUCCESS;
        message.obj=remsg.getdetail();
        handler.sendMessage(message);
    }

    private void clientover() throws IOException {
        MyMessage finishmsg = new MyMessage();
        finishmsg.sethead(Client.CLINT_FINISH);
        out.writeUTF(Json.ObjecttoJson(finishmsg));
        out.flush();
        socket.close();
        out.close();
        in.close();
    }

    private void query(int head) throws IOException {
        this.msg.sethead(head);
        this.JsonStr = Json.ObjecttoJson(msg);
        out.writeUTF(JsonStr);
        out.flush();
    }
}
