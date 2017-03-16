package edu.fjnu.fujiantravel.order;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class OrderThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private int type;
    private Order order;
    private OrderUpdate orderupdate;
    private String orderid;

    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private String address;
    private int port;

    private Context context;
    private Handler handler;

    public OrderThread() {

    }

    public OrderThread(int type, Order order, String address, int port, Context context,Handler handler) {
        this.type = type;
        this.order = order;
        this.address = address;
        this.port = port;
        this.context = context;
        this.handler= handler;
    }

    public OrderThread(int type, OrderUpdate orderupdate, String address, int port, Context context) {
        this.type = type;
        this.orderupdate = orderupdate;
        this.address = address;
        this.port = port;
        this.context = context;
    }

    public OrderThread(int type, String orderid, String address, int port, Context context, Handler handler) {
        this.type = type;
        this.orderid = orderid;
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
                case Order.CREATEORDER:
                    createorder();
                    break;
                case Order.QUERYORDER_SINGLE:
                    querysingleorder();
                    break;
            }
            reJsonStr = in.readUTF();
            remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
            int head = remsg.gethead();
            switch (head) {
                case Order.CREATRORDER_SUCCESS:
                    createordersuccess();
                    break;
                case Order.QUERYORDER_SUCCESS:
                    queryordersuccess();
                    break;
                case Order.CREATRORDER_ERROR:
                    createordererror();
                    break;
                case Order.QUERYORDER_ERROR:
                    queryordererror();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createorder() throws IOException {
        this.msg.sethead(Order.CREATEORDER);
        this.msg.setdetail(Json.ObjecttoJson(this.order));
        this.JsonStr = Json.ObjecttoJson(msg);
        out.writeUTF(JsonStr);
        out.flush();
    }

    private void querysingleorder() throws IOException {
        this.msg.sethead(Order.QUERYORDER_SINGLE);
        this.msg.setdetail(orderid);
        this.JsonStr = Json.ObjecttoJson(msg);
        out.writeUTF(JsonStr);
        out.flush();
    }

    private void createordersuccess() throws IOException {
        Message message = new Message();
        message.what= Order.CREATRORDER_SUCCESS;
        handler.sendMessage(message);
        clientover();
    }
    private void createordererror()throws IOException{
        Message message = new Message();
        message.what=Order.CREATRORDER_ERROR;
        handler.sendMessage(message);
        clientover();
    }

    private void queryordersuccess() throws IOException {
        clientover();
        Message message = new Message();
        message.what = remsg.gethead();
        message.obj = remsg.getdetail();
        handler.sendMessage(message);
    }
    private void queryordererror()throws IOException{
        Message message = new Message();
        message.what = Order.QUERYORDER_ERROR;
        handler.sendMessage(message);
        clientover();
    }

    private void clientover() throws IOException {
        msg.sethead(Client.CLINT_FINISH);
        msg.setdetail(null);
        out.writeUTF(Json.ObjecttoJson(msg));
        out.flush();
        socket.close();
        out.close();
        in.close();
    }
}