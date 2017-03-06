package edu.fjnu.fujiantravel.push;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class PushThread extends Thread {
    private String address;
    private int port;

    public PushThread(String address, int port){
        this.address=address;
        this.port=port;
    }
}
