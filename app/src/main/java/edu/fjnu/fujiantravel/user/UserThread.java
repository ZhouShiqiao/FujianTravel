package edu.fjnu.fujiantravel.user;

import edu.fjnu.fujiantravel.message.MyMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class UserThread extends Thread {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private User user;
    private int type;

    private MyMessage msg = new MyMessage();
    private MyMessage remsg = new MyMessage();
    private String JsonStr;
    private String reJsonStr;

    private String address;
    private int port;

    public UserThread() {

    }

    public UserThread(int type, User user, String address, int port) {
        this.user = user;
        this.type = type;
        this.address = address;
        this.port = port;
    }

    public void run() {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            switch (type) {
                case User.USERREGISTER:
                    break;
                case User.USERLOG:
                    break;
                case User.EDITUSERINFO:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
