package edu.fjnu.fujiantravel.order;

/**
 * Created by Administrator on 2017/3/1 0001.
        */

        import java.util.List;

public class Order {
    // create order
    public final static int CREATEORDER = 20;
    public final static int CREATRORDER_SUCCESS = 200;
    public final static int CREATRORDER_ERROR = 201;

    // update order
    public final static int UPDATEORDER = 21;
    public final static int UPDATEORDRE_SUCCESS = 210;
    public final static int UPDATEORDER_ERROR = 211;

    // query order
    public final static int QUERYORDER = 22;
    public final static int QUERYORDER_TOURIST = 228;
    public final static int QUERYORDER_GUIDE = 229;
    public final static int QUERYORDER_SUCCESS = 220;
    public final static int QUERYORDER_ERROR = 221;
    public final static int QUERYORDER_SINGLE = 227;

    // order begin
    public final static int BEGINORDER = 23;
    public final static int BEGINORDER_SUCCESS = 230;
    public final static int BEGINORDER_ERROR = 231;

    // order push
    public final static int PUSHORDER = 24;
    public final static int PUSHORDERSUCCESS = 240;
    public final static int PUSHORDERTOTOURIST = 241;
    public final static int PUSHORDERTOGUIDE = 242;

    // order cancel
    public final static int CANCELORDER = 25;
    public final static int CANCELORDERSUCCESS = 250;
    public final static int CANCELORDERERROR = 251;

    // order receive
    public final static int RECEIVEORDER = 26;
    public final static int RECEIVEORDERSUCCESS = 260;
    public final static int RECEIVEORDERERROR = 261;

    // order ensure
    public final static int ENSUREORDER = 27;
    public final static int ENSUREORDER_SUCCESS = 270;
    public final static int ENSUREORDER_ERROR = 271;


    private String orderid;
    private String touristid;
    private String guideid;
    private int type;
    private int state;
    private double price;
    private double reward;
    private String createtime;
    private String paytime;
    private String finishtime;
    private String remark;
    private int peoplenumbers;
    private int playtime;
    private List<OrderDetail> detail;
    private String begintime;
    private String senic;

    public Order() {

    }
    public void setsenic(String senic) {
        this.senic = senic;
    }

    public String getsenic() {
        return this.senic;
    }

    public void setbegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getbegintime() {
        return this.begintime;
    }

    public void setdetail(List<OrderDetail> detail) {
        this.detail = detail;
    }

    public List<OrderDetail> getdetail() {
        return this.detail;
    }

    public void setpeoplenumbers(int numbers) {
        this.peoplenumbers = numbers;
    }

    public int getPeoplenumbers() {
        return this.peoplenumbers;
    }

    public void setplaytime(int time) {
        this.playtime = time;
    }

    public int getplaytime() {
        return this.playtime;
    }


    public String getorderid() {
        return this.orderid;
    }

    public String gettouristid() {
        return this.touristid;
    }

    public String getguideid() {
        return this.guideid;
    }

    public int gettype() {
        return this.type;
    }

    public int getstate() {
        return this.state;
    }

    public double getprice() {
        return this.price;
    }

    public double getreward() {
        return this.reward;
    }

    public String getcreatetime() {
        return this.createtime;
    }

    public String getpaytime() {
        return this.paytime;
    }

    public String getfinishtime() {
        return this.finishtime;
    }

    public String getremark() {
        return this.remark;
    }

    public void setorderid(String id) {
        this.orderid = id;
    }

    public void setguideid(String id) {
        this.guideid = id;
    }

    public void settouristid(String id) {
        this.touristid = id;
    }

    public void settype(int type) {
        this.type = type;
    }

    public void setstate(int state) {
        this.state = state;
    }

    public void setprice(double price) {
        this.price = price;
    }

    public void setreward(double reward) {
        this.reward = reward;
    }

    public void setcreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setpaytime(String paytime) {
        this.paytime = paytime;
    }

    public void setfinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }
}