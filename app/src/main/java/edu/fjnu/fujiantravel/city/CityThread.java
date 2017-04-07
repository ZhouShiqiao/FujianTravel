package edu.fjnu.fujiantravel.city;

import android.content.Context;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import edu.fjnu.fujiantravel.database.DBManager;
import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.order.Order;
import edu.fjnu.fujiantravel.user.Guide;
import edu.fjnu.fujiantravel.user.Tourist;
import edu.fjnu.fujiantravel.user.User;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class CityThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private CityList citys = new CityList();
    private int type;

    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private String address;
    private int port;

    private Handler handler;
    private Context context;

    public CityThread() {

    }

    public CityThread(int type, String address, int port, Context context) {
        this.type = type;
        this.address = address;
        this.port = port;
        this.context = context;
    }

    public void run() {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            switch (type) {
                case City.QUERYCITYLIST:
                    querycitylist();
                    break;
            }
            reJsonStr = in.readUTF();
            remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
            int head = remsg.gethead();
            switch (head) {
                case City.QUERYCITYLIST_SUCCESS:
                    querysuccess();
                    clientover();
                    break;
                case City.QUERYCITYLIST_ERROR:
                    clientover();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void querycitylist() throws IOException {
        this.msg.sethead(City.QUERYCITYLIST);
        this.JsonStr = Json.ObjecttoJson(msg);
        out.writeUTF(JsonStr);
        out.flush();
    }

    private void querysuccess() {
        citys = (CityList) Json.JsontoObject(remsg.getdetail(),citys.getClass());
        DBManager.getInstance().initcity(citys.getcitys());
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
}
