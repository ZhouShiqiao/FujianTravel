package edu.fjnu.fujiantravel.order;

import android.content.Context;
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

    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private String address;
    private int port;

    private Context context;

    public OrderThread() {

    }

    public OrderThread(int type, Order order, String address, int port, Context context) {
        this.type = type;
        this.order = order;
        this.address = address;
        this.port = port;
        this.context = context;
    }

    public OrderThread(int type, OrderUpdate orderupdate, String address, int port, Context context) {
        this.type = type;
        this.orderupdate = orderupdate;
        this.address = address;
        this.port = port;
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
            }
            reJsonStr = in.readUTF();
            remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
            int head = remsg.gethead();
            switch (head) {
                case Order.CREATRORDER_SUCCESS:
                    msg.sethead(Client.CLINT_FINISH);
                    msg.setdetail(null);
                    out.writeUTF(Json.ObjecttoJson(msg));
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                  //  Toast.makeText(context, "订单创建成功", Toast.LENGTH_SHORT).show();
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
}
